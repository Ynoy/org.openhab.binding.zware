package org.openhab.binding.zware.config;

import java.util.ArrayList;
import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.openhab.binding.zware.internal.ZWareBindingConstants;
import org.openhab.binding.zware.utils.OkHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZwareNodeList {
    private final static Logger logger = LoggerFactory.getLogger(ZwareNodeList.class);

    private static String getNodeList = ZWareBindingConstants.Host + ZWareBindingConstants.URL_GET_LIST_NODE;

    public static ArrayList<ArrayList<String>> getNodeList() throws DocumentException {
        ArrayList list = new ArrayList();
        String respGetNodeList = OkHttpUtils.postRequest(getNodeList, null);
        logger.error("ZwareNodeList-->respGetNodeList:" + respGetNodeList);
        Document document = DocumentHelper.parseText(respGetNodeList);
        Element zwave = document.getRootElement();// 获取根节点
        Element zwnet = zwave.element("zwnet");

        for (Iterator zwnode = zwnet.elementIterator("zwnode"); zwnode.hasNext();) {
            ArrayList list2 = new ArrayList();
            Element zwnode_i = (Element) zwnode.next();
            Attribute id = zwnode_i.attribute("id");
            Attribute dev_categorys = zwnode_i.attribute("category");
            String nodeId = id.getText();
            int dev_category = Integer.parseInt(dev_categorys.getText());
            String category = DeviceCategory.getName(dev_category);
            logger.error("NodeId:" + nodeId);
            logger.error("dev_category:" + category);
            list2.add(nodeId);
            list2.add(category);
            list.add(list2);
        }

        return list;

    }

    /**
     * 针对不同设备的category值进行不同的详细描述，并返回具体信息。
     *
     * @author: Honey
     * @date: 2018年5月29日
     * @tags:
     * @email: xy410257@163.com
     */
    public enum DeviceCategory {
        SENSOR_ALARM(1, "Sensor alarm"),
        ON_OFF_SWITCH(2, "On/off switch"),
        POWER_STRIP(3, "POWER_STRIP"),
        SIREN(4, "Siren"),
        VALVE(5, "VALVE"),
        SIMPLE_DISPLAY(6, "Simple display"),
        DOORLOCK_KEYPAD(7, "Door lock with keypad"),
        SUB_ENERGY_METER(8, "SUB_ENERGY_METER"),
        ADV_WHL_HOME_ENER_METER(9, "Advanced whole home energy meter"),
        SIM_WHL_HOME_ENER_METER(10, "Simple whole home energy meter"),
        SENSOR(11, "Sensor"),
        LIGHT_DIMMER(12, "Light dimmer switch"),
        WIN_COVERING_NO_POS(13, "Window covering no position/endpoint"),
        WIN_COVERING_EP(14, "Window covering end point aware"),
        WIN_COVERING_POS_EP(15, "Window covering position/end point aware"),
        FAN_SWITCH(16, "Fan switch"),
        RMT_CTL_MULTIPURPOSE(17, "Remote control multipurpose"),
        DEV_RMT_CTL_AV(18, "Remote control – AV"),
        DEV_RMT_CTL_SIMPLE(19, "Remote control simple"),
        DEV_UNRECOG_GATEWAY(20, "Gateway (unrecognized by client)"),
        DEV_CENTRAL_CTLR(21, "Central controller"),
        DEV_SET_TOP_BOX(22, "Set top box"),
        DEV_TV(23, "TV"),
        DEV_SUB_SYS_CTLR(24, "Sub system controller"),
        DEV_GATEWAY(25, "Gateway"),
        DEV_THERMOSTAT_HVAC(26, "Thermostat – HVAC"),
        DEV_THERMOSTAT_SETBACK(27, "Thermostat – setback"),
        DEV_WALL_CTLR(28, "Wall controller");

        private int index;
        private String name;

        // 构造方法；
        private DeviceCategory(int index, String name) {
            this.name = name;
            this.index = index;
        }

        // 普通方法；
        public static String getName(int index) {
            for (DeviceCategory c : DeviceCategory.values()) {
                if (c.getIndex() == index) {
                    return c.name;
                }
            }
            return null;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public enum NodeAliveStatus {
        ALIVE(3, "ONLINE"),
        DOWN(4, "OFFLINE"),
        SLEEP(5, "SLEEPING");

        private int index;
        private String name;

        private NodeAliveStatus(int index, String name) {
            this.name = name;
            this.index = index;
        }

        // 普通方法；
        public static String getName(int index) {
            for (NodeAliveStatus c : NodeAliveStatus.values()) {
                if (c.getIndex() == index) {
                    return c.name;
                }
            }
            return null;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
