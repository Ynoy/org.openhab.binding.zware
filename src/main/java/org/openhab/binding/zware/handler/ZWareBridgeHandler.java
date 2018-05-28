/**
 * Copyright (c) 2014,2018 by the respective copyright holders.
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.zware.handler;

import static org.openhab.binding.zware.internal.ZWareBindingConstants.User;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseBridgeHandler;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.zware.config.ZwareNodeList;
import org.openhab.binding.zware.internal.ZWareBindingConstants;
import org.openhab.binding.zware.internal.ZWareConfiguration;
import org.openhab.binding.zware.utils.OkHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link ZWareBridgeHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author CheneyHao - Initial contribution
 */

public class ZWareBridgeHandler extends BaseBridgeHandler {

    private final Logger logger = LoggerFactory.getLogger(ZWareBridgeHandler.class);

    private static final int SEARCH_TIME = 60 * 4;

    public static final ThingTypeUID SUPPORTED_THING_TYPE = ZWareBindingConstants.THING_TYPE_BRIDGE;

    public static ZWareConfiguration config;

    ZWayDeviceScan zWayDeviceScan;

    public ZWareBridgeHandler(Bridge bridge) {
        super(bridge);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (channelUID.getId().equals(User)) {
            // TODO: handle command

            // Note: if communication with thing fails for some reason,
            // indicate that by setting the status with detail information
            // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
            // "Could not control device at IP address x.x.x.x");
        }
    }

    @Override
    public void initialize() {
        config = getConfigAs(ZWareConfiguration.class);
        String text = "3";
        String url = ZWareBindingConstants.Host + ZWareBindingConstants.URL_LOGIN;
        logger.error(url);

        logger.error(config.getUsrname() + "-------" + config.getPasswrd());
        Map<String, String> map = new HashMap<>();
        map.put(ZWareBindingConstants.UserUsrname, config.getUsrname());
        map.put(ZWareBindingConstants.UserPasswd, config.getPasswrd());
        String resp = OkHttpUtils.postRequest(url, map);
        logger.error(resp);

        String getNetworkOperation = ZWareBindingConstants.Host
                + ZWareBindingConstants.URL_GET_CURRENT_NETWORK_OPERATION;

        String respNetworkOperation = OkHttpUtils.postRequest(getNetworkOperation, null);
        Document zwave = null;
        try {
            zwave = DocumentHelper.parseText(respNetworkOperation);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            ZwareNodeList.getNodeList();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        updateStatus(ThingStatus.ONLINE);

        // zWayDeviceScan.run();
        // TODO: Initialize the thing. If done set status to ONLINE to indicate proper working.
        // Long running initialization should be done asynchronously in background.

        // Note: When initialization can NOT be done set the status with more details for further
        // analysis. See also class ThingStatusDetail for all available status details.
        // Add a description to give user information to understand why thing does not work
        // as expected. E.g.
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
        // "Can not access device as username and/or password are invalid");
    }

    public class ZWayDeviceScan extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {

                try {
                    ZwareNodeList.getNodeList();
                } catch (DocumentException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    // TODO: handle exception
                    logger.error(e.getStackTrace().toString());
                }

            }
        }
    }
}