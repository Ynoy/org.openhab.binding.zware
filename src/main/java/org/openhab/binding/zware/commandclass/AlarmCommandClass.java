package org.openhab.binding.zware.commandclass;

import java.util.Map;

import org.openhab.binding.zware.internal.ZWareBindingConstants;

public class AlarmCommandClass {

    private final String ALARMINTERFACEAPI = ZWareBindingConstants.Host + "/cgi/zcgi/networks//zwif_alrm";

    private final int CMD_ALARM_SETUP = 1;
    private final int CMD_ALARM_GET = 2;
    private final int CMD_ALARM_REPORT = 3;
    private final int CMD_ALARM_SET = 4;
    private final int CMD_ALARM_TYPE_SUP_GET = 5;
    private final int CMD_ALARM_TYPE_SUP_REPORT = 6;
    private final int CMD_ALARM_EVENT_SUP_GET = 7;
    private final int CMD_ALARM_EVENT_SUP_REPORT = 8;

    public Map<String, String> AlarmStatus(String descriptor) {

        return null;
    }
}
