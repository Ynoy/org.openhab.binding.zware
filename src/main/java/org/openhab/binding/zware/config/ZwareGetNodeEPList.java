package org.openhab.binding.zware.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.openhab.binding.zware.internal.ZWareBindingConstants;
import org.openhab.binding.zware.utils.OkHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZwareGetNodeEPList {

    private final static Logger logger = LoggerFactory.getLogger(ZwareGetNodeEPList.class);

    /**
     * 通过ZwareGetNodeEPList获取到所有网关下所有设备放入list，
     * 每一个具体的node为一个list，其中包含该node的所有属性。
     *
     * 每一个list（node）包含固定的三个属性：desc、node_id、ep_id；
     * 分别对应list的index：0、1、2
     *
     * @return
     * @throws DocumentException
     */
    private static ArrayList<ArrayList<String>> ZwareGetNodeEPList() throws DocumentException {
        String urlParams = ZWareBindingConstants.Host + ZWareBindingConstants.URL_GET_LIST_NODE_ENDPOINT_PAIRS;

        // 该请求为post无参请求；
        String resp = OkHttpUtils.postRequest(urlParams, null);
        logger.error(resp);
        ArrayList<ArrayList<String>> nodeEpLists = new ArrayList<>();
        Document respDocument = DocumentHelper.parseText(resp);
        Element zwave = respDocument.getRootElement();
        for (Iterator node_ep = zwave.elementIterator("node_ep"); node_ep.hasNext();) {
            ArrayList<String> nodeEpList = new ArrayList<>();
            String desc = ((Element) node_ep).attribute("desc").getText();
            nodeEpList.add(desc);
            logger.error(desc);
            String node_id = ((Element) node_ep).attribute("node_id").getText();
            nodeEpList.add(node_id);
            logger.error(node_id);
            String ep_id = ((Element) node_ep).attribute("ep_id").getText();
            nodeEpList.add(ep_id);
            logger.error(ep_id);
            nodeEpLists.add(nodeEpList);
        }
        return nodeEpLists;
    }

    /**
     * 调用ZwareGetNodeEPList返回的list<node>中的参数值去请求该节点支持的cc。
     *
     * 返回的list在ZwareGetNodeEPList返回的list<node>中添加了一项command集
     *
     * @return
     * @throws DocumentException
     */
    public static ArrayList<ArrayList<String>> ZwareGetInterfaceList() throws DocumentException {

        String urlParams = ZWareBindingConstants.Host + ZWareBindingConstants.URL_LIST_ENDPOOINTS_INTERFACES;
        Map<String, String> mapParams = new HashMap<>();

        ArrayList<ArrayList<String>> nodeEpLists = ZwareGetNodeEPList();

        for (ArrayList<String> nodeEpList : nodeEpLists) {
            mapParams.put("epd", nodeEpList.get(0));
            String reap = OkHttpUtils.postRequest(urlParams, mapParams);
            nodeEpList.add(reap);
            nodeEpLists.add(nodeEpList);
        }

        return nodeEpLists;
    }
}
