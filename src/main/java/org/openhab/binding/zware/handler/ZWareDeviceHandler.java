package org.openhab.binding.zware.handler;

import java.util.HashMap;
import java.util.List;

import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.thing.binding.builder.ChannelBuilder;
import org.eclipse.smarthome.core.thing.binding.builder.ThingBuilder;
import org.eclipse.smarthome.core.thing.type.ChannelTypeUID;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.zware.internal.ZWareBindingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZWareDeviceHandler extends BaseThingHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private int nodeId;

    public ZWareDeviceHandler(Thing thing) {
        super(thing);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        // TODO Auto-generated method stub

    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
    }

    @Override
    public void channelLinked(ChannelUID channelUID) {
        logger.debug("Z-Way device channel linked: {}", channelUID);

        ZWareBridgeHandler zwayBridgeHandler = getZwareBridgeHandler();
        if (zwayBridgeHandler == null || !zwayBridgeHandler.getThing().getStatus().equals(ThingStatus.ONLINE)) {
            logger.debug("Z-Way bridge handler not found or not ONLINE.");
            return;
        }

        // Method called when channel linked and not when server started!!!

        super.channelLinked(channelUID); // performs a refresh command
    }

    @Override
    public void channelUnlinked(ChannelUID channelUID) {
        logger.debug("Z-Way device channel unlinked: {}", channelUID);

        ZWareBridgeHandler zwayBridgeHandler = getZwareBridgeHandler();
        if (zwayBridgeHandler == null || !zwayBridgeHandler.getThing().getStatus().equals(ThingStatus.ONLINE)) {
            logger.debug("Z-Way bridge handler not found or not ONLINE.");
            return;
        }

        super.channelUnlinked(channelUID);
    }

    protected synchronized ZWareBridgeHandler getZwareBridgeHandler() {
        Bridge bridge = getBridge();
        if (bridge == null) {
            return null;
        }
        ThingHandler handler = bridge.getHandler();
        if (handler instanceof ZWareBridgeHandler) {
            return (ZWareBridgeHandler) handler;
        } else {
            return null;
        }
    }

    private synchronized void addChannel(String id, String acceptedItemType, String label,
            HashMap<String, String> properties) {
        boolean channelExists = false;

        // Check if a channel for this virtual device exist. Attention: same channel type could multiple assigned to a
        // thing. That's why not check the existence of channel type.
        List<Channel> channels = getThing().getChannels();
        for (Channel channel : channels) {
            if (channel.getProperties().get("deviceId") != null
                    && channel.getProperties().get("deviceId").equals(properties.get("deviceId"))) {
                channelExists = true;
            }
        }

        if (!channelExists) {
            ThingBuilder thingBuilder = editThing();
            ChannelTypeUID channelTypeUID = new ChannelTypeUID(ZWareBindingConstants.BINDING_ID, id);
            Channel channel = ChannelBuilder
                    .create(new ChannelUID(getThing().getUID(), id + "-" + properties.get("deviceId")),
                            acceptedItemType)
                    .withType(channelTypeUID).withLabel(label).withProperties(properties).build();
            thingBuilder.withChannel(channel);
            thingBuilder.withLabel(thing.getLabel());
            updateThing(thingBuilder.build());
        }
    }

}
