package org.openhab.binding.zware.config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.openhab.binding.zware.internal.ZWareBindingConstants;
import org.openhab.binding.zware.utils.OkHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 此类用作处理获取网关当前处于何种状态，并返回具体信息；
 * 
 * @author: Honey
 * @date: 2018年5月29日
 * @tags:
 * @email: xy410257@163.com
 */
public class ZwareGetNetworkOperation {

    private final static Logger logger = LoggerFactory.getLogger(ZwareGetNetworkOperation.class);

    private String urlParams = ZWareBindingConstants.Host + ZWareBindingConstants.URL_GET_CURRENT_NETWORK_OPERATION;

    public String ZwareGetNetworkOperation() throws DocumentException {
        Document netOpDocument = DocumentHelper.parseText(OkHttpUtils.postRequest(urlParams, null));
        Element zwaveElement = netOpDocument.getRootElement();
        Element zwnetElement = zwaveElement.element("zwnet");
        Element operation = zwnetElement.element("operation");
        int net_cmd_type = Integer.parseInt(operation.attribute("op").getText());
        int net_cmd_status = Integer.parseInt(operation.attribute("op_sts").getText());
        if ("ADD_NODE".equals(NetCMDType.getName(net_cmd_type))) {
            switch (net_cmd_status) {
                case 1:

                    return "Protocol part of adding node done";
                case 2:

                    return "Getting node detailed information";

                case 3:
                    return "Smart Start add node Zwave protocol started";

                default:
                    break;
            }
        }
        if ("RM_NODE".equals(NetCMDType.getName(net_cmd_type))) {
            switch (net_cmd_status) {
                case 1:

                    return "Ready to remove a node";
                case 2:

                    return "Found a node";

                case 3:
                    return "Removing the node";

                default:
                    break;
            }
        }
        if ("RP_NODE".equals(NetCMDType.getName(net_cmd_type))) {
            switch (net_cmd_status) {
                case 1:

                    return "Ready to replace a node ";
                case 2:

                    return "Protocol part of replacing node done  ";

                case 3:

                    return "Adding node securely";
                case 4:

                    return "Getting node detailed information";

                default:
                    break;
            }
        }
        if ("INITIATE".equals(NetCMDType.getName(net_cmd_type))) {
            switch (net_cmd_status) {
                case 1:

                    return "Initiating started, ready to be added/removed to/fromnetwork ";
                case 2:

                    return "Protocol part of initiating done ";

                case 3:

                    return "Trying to be included securely";
                case 4:

                    return "Getting node detailed information";

                default:
                    break;
            }
        }
        if ("UPDATE".equals(NetCMDType.getName(net_cmd_type))) {
            switch (net_cmd_status) {
                case 1:

                    return "Network topology update started ";
                case 2:

                    return "Node neighbor update started ";

                case 3:

                    return "Node information update started";

                default:
                    break;
            }
        }
        switch (net_cmd_status) {
            case 0:
                return "OP_STS_NONE";
            case -1:
                return "OP_STS_ERROR";
            case -4:
                return "OP_STS_NO_NET";
            case -5:
                return "OP_STS_ABORTED";

            default:
                return "";
        }
        /**
         * 保留字段
         * String op_total_nodes = operation.attribute("op_total_nodes").getText();
         * String op_cmplt_nodes = operation.attribute("op_cmplt_nodes").getText();
         * String prev_op = operation.attribute("prev_op").getText();
         * String inif_count = operation.attribute("inif_count").getText();
         *
         */

    }

    public enum NetCMDStatus {
        OP_STS_NONE(0, "OP_STS_NONE"),
        OP_STS_ERROR(-1, "OP_STS_ERROR"),
        OP_STS_NO_NET(-4, "OP_STS_NO_NET"),
        OP_STS_ABORTED(-5, "OP_STS_ABORTED");

        private int index;
        private String name;

        private NetCMDStatus(int index, String name) {
            this.index = index;
            this.name = name;
        }

        // 普通方法；
        public static String getName(int index) {
            for (NetCMDStatus c : NetCMDStatus.values()) {
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

    public enum NetCMDType {
        NONE(0, "NONE"),
        INITIALIZE(1, "INITIALIZE"),
        ADD_NODE(2, "ADD_NODE"),
        RM_NODE(3, "RM_NODE"),
        RP_NODE(4, "RP_NODE"),
        RM_FAILED_ID(5, "RM_FAILED_ID"),
        INITIATE(6, "INITIATE"),
        UPDATE(7, "UPDATE"),
        RESET(8, "RESET"),
        MIGRATE_SUC(9, "MIGRATE_SUC"),
        MIGRATE(10, "MIGRATE"),
        LOAD_NW_INFO(12, "LOAD_NW_INFO"),
        NODE_UPDATE(13, "NODE_UPDATE"),
        SEND_NIF(14, "SEND_NIF"),
        ADD_NODE_ON_BEHALF(15, "ADD_NODE_ON_BEHALF"),
        RP_NODE_ON_BEHALF(16, "RP_NODE_ON_BEHALF"),
        HEALTH_CHK(20, "HEALTH_CHK");

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
