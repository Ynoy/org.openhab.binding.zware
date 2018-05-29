package org.openhab.binding.zware.commandclass;

import java.util.ArrayList;

public class ZwareCommandClass {

    public ArrayList<E>
    /**
     * 该命令枚举是为了创建channel而准备，让一般CC对应上固定的channelTypeID；
     * 但也不排除个别特殊功能的CC，例如Alarm这种包含多种传感器信息的命令集，像这种命令集
     * 需要单独写一个AlarmCommandClass类做具体的处理。
     *
     * @author: Honey
     * @date: 2018年5月29日
     * @tags:
     * @email: xy410257@163.com
     */
    public enum CommandClass {
        COMMAND_CLASS_BASIC(32, "onoff_basic"),

        COMMAND_CLASS_ZIP(35, "string_zip"),

        COMMAND_CLASS_ASSOCIATION_GRP_INFO(89, "string_associationinfo"),
        COMMAND_CLASS_DEVICE_RESET_LOCALLY(90, "string_reset"),

        COMMAND_CLASS_IP_ASSOCIATION(92, "string_ipassociation"),

        COMMAND_CLASS_ZWAVEPLUS_INFO(94, "string_zwaveplus"),

        COMMAND_CLASS_ALARM(113, "switch_alarm"),
        COMMAND_CLASS_MANUFACTURER_SPECIFIC(114, "string_manufacturer"),
        COMMAND_CLASS_POWERLEVEL(115, "string_powerlevel"),

        COMMAND_CLASS_BATTERY(128, "number_battery"),
        COMMAND_CLASS_WAKE_UP(132, "string_wakeup"),
        COMMAND_CLASS_ASSOCIATION(133, "string_association"),
        COMMAND_CLASS_VERSION(134, "string_info");

        private int index;
        private String typeID;

        private CommandClass(int index, String typeID) {
            this.index = index;
            this.typeID = typeID;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getTypeID() {
            return typeID;
        }

        public void setTypeID(String typeID) {
            this.typeID = typeID;
        }

    }
}
