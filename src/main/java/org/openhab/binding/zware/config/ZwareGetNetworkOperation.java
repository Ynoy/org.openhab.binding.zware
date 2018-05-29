package org.openhab.binding.zware.config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.openhab.binding.zware.internal.ZWareBindingConstants;
import org.openhab.binding.zware.utils.OkHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZwareGetNetworkOperation {

    private final static Logger logger = LoggerFactory.getLogger(ZwareGetNetworkOperation.class);

    private String urlParams = ZWareBindingConstants.Host + ZWareBindingConstants.URL_GET_CURRENT_NETWORK_OPERATION;

    public void ZwareGetNetworkOperation() throws DocumentException {
        Document netOpDocument = DocumentHelper.parseText(OkHttpUtils.postRequest(urlParams, null));

    }

    public enum NetCMDStatus {
        OP_STS_NONE(0, "No status to report"),
        OP_STS_ERROR(-1, "Error"),
        OP_STS_NO_NET(-4, "Network uninitialized"),
        OP_STS_ABORTED(-5, "Network operation aborted");

        private NetCMDStatus(int index, String name) {

        }
    }

    public enum NetCMDType {
        NONE(0, "No operation is executing"),
        INITIALIZE(1, "Initialization operation"),
        ADD_NODE(2, "Add node operation"),
        RM_NODE(3, "Remove node operatio"),
        RP_NODE(4, "Replace failed node operation"),
        RM_FAILED_ID(5, "Remove failed node operation"),
        INITIATE(6, "Initiation operation by controller"),
        UPDATE(7, "Update network topology from the SUC/SIS"),
        RESET(8, "Restore to factory default setting"),
        MIGRATE_SUC(9, "Create primary controller by a SUC"),
        MIGRATE(10, "Migrate primary controller operation"),
        LOAD_NW_INFO(12, "Load network information"),
        NODE_UPDATE(13, "Update node info"),
        SEND_NIF(14, "Send node information frame"),
        ADD_NODE_ON_BEHALF(15, "Add node on-half operation"),
        RP_NODE_ON_BEHALF(16, "Replace failed node on-half operation"),
        HEALTH_CHK(20, "Network health check");

        private int index;
        private String name;

        private NetCMDType(int index, String name) {
            this.index = index;
            this.name = name;
        }

        // 普通方法；
        public static String getName(int index) {
            for (NetCMDType c : NetCMDType.values()) {
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
