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
package org.openhab.binding.zware.internal;

import java.util.Set;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

import com.google.common.collect.ImmutableSet;

/**
 * The {@link ZWareBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author CheneyHao - Initial contribution
 */
public class ZWareBindingConstants {

    public static final String BINDING_ID = "zware";

    public final static String THING_TYPE_DEVICE = BINDING_ID + ":device";
    public final static ThingTypeUID ZWAVE_THING_UID = new ThingTypeUID(THING_TYPE_DEVICE);

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_BRIDGE = new ThingTypeUID(BINDING_ID, "bridge");

    public static final Set<ThingTypeUID> SUPPORTED_DEVICE_THING_TYPES_UIDS = ImmutableSet.of(ZWAVE_THING_UID);

    // List of ignored devices for Discovery
    public static final Set<String> DISCOVERY_IGNORED_DEVICES = ImmutableSet.of("BatteryPolling");

    // Config
    public static final String HOSTS = "hostsAddress";
    public static final String Host = "http://192.168.10.239:808";

    public static final String UserUsrname = "usrname";
    public static final String UserPasswd = "passwd";

    // List of all Channel ids
    public static final String User = "ZwareUser";

    /*
     * NETWORK API
     */
    public static final String URL_LOGIN = "/register/login";
    // URL list
    public static final String URL_GET_INFORMATION = "/cgi/zcgi/networks//zwnet_get_desc";
    public static final String URL_GET_LIST_NODE = "/cgi/zcgi/networks//zwnet_get_node_list";
    public static final String URL_GET_LIST_NODE_ENDPOINT_PAIRS = "/cgi/zcgi/networks//get_node_ep_list";
    public static final String URL_INCLUDE_EXCLUDE = "/cgi/zcgi/networks//zwnet_add";
    public static final String URL_UPDATE_NETWORK = "/cgi/zcgi/networks//zwnet_update";
    public static final String URL_RESET_NETWORK = "/cgi/zcgi/networks//zwnet_reset";
    public static final String URL_ABORT_NETWORK = "/cgi/zcgi/networks//zwnet_abort";
    public static final String URL_GET_CURRENT_NETWORK_OPERATION = "/cgi/zcgi/networks//zwnet_get_operation";
    public static final String URL_INITIATE = "/cgi/zcgi/networks//zwnet_initiate";
    public static final String URL_GET_PROVISIONING_LIST_LIST = "/cgi/zcgi/networks//zwnet_provisioning_list_list_get";
    public static final String URL_GET_PROVISIONING_DEVICE_LIST = "/cgi/zcgi/networks//zwnet_provisioning_list_info";

    /*
     * NODE/ENDPOINT API
     */
    public static final String URL_LIST_ENDPOOINTS = "/cgi/zcgi/networks//zwnode_get_ep_list";
    public static final String URL_LIST_ENDPOOINTS_INTERFACES = "/cgi/zcgi/networks//zwep_get_if_list";
    public static final String URL_RMMOVEorREPLACE_FAILED_NODE = "/cgi/zcgi/networks//zwnet_fail";
    public static final String URL_UPDATE_NODE = "/cgi/zcgi/networks//zwnode_update";
    public static final String URL_LIST_NODES = "/cgi/zcgi/networks//zwnet_get_node_list";
    // public static final String URL_LIST_ENDPOOINTS = "/cgi/zcgi/networks//zwnode_get_ep_list";
}
