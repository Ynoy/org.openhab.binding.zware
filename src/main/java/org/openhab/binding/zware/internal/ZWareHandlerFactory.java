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

import java.util.Collections;
import java.util.Set;

import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandlerFactory;
import org.openhab.binding.zware.handler.ZWareBridgeHandler;
import org.openhab.binding.zware.handler.ZWareDeviceHandler;
import org.osgi.service.component.annotations.Component;

/**
 * The {@link ZWareHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author CheneyHao - Initial contribution
 */

@Component(configurationPid = "binding.zware", service = ThingHandlerFactory.class)
public class ZWareHandlerFactory extends BaseThingHandlerFactory {

    private static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Collections
            .singleton(ZWareBindingConstants.ZWAVE_THING_UID);

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {

        if (thingTypeUID.equals(ZWareBindingConstants.ZWAVE_THING_UID)) {
            return true;
        }

        return ZWareBindingConstants.BINDING_ID.equals(thingTypeUID.getBindingId());
    }

    @Override
    protected ThingHandler createHandler(Thing thing) {

        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        // Handle controllers here
        if (thingTypeUID.equals(ZWareBindingConstants.THING_TYPE_BRIDGE)) {
            return new ZWareBridgeHandler((Bridge) thing);
        }

        // Everything else gets handled in a single handler
        return new ZWareDeviceHandler(thing);
    }
}
