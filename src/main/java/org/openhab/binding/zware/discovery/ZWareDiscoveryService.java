package org.openhab.binding.zware.discovery;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.config.discovery.DiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryServiceCallback;
import org.eclipse.smarthome.config.discovery.ExtendedDiscoveryService;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.openhab.binding.zware.handler.ZWareBridgeHandler;
import org.openhab.binding.zware.internal.ZWareBindingConstants;
import org.openhab.binding.zware.utils.HttpUtils;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;;

@Component(service = DiscoveryService.class, immediate = true, configurationPid = "discovery.zware")
public class ZWareDiscoveryService extends AbstractDiscoveryService implements ExtendedDiscoveryService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final int SEARCH_TIME = 60 * 4;

    private HttpUtils httpUtils;

    private DiscoveryServiceCallback discoveryServiceCallback;

    private static final String ZWNET_GET_OPERATION = ZWareBindingConstants.hosts
            + ZWareBindingConstants.URL_GET_CURRENT_NETWORK_OPERATION;

    private static final String ZWNET_GET_NODESLIST = ZWareBindingConstants.hosts
            + ZWareBindingConstants.URL_GET_LIST_NODE;

    private ZWareBridgeHandler controllerHandler;
    private ZWayDeviceScan mZWayDeviceScanningRunnable;

    public ZWareDiscoveryService() {
        // TODO Auto-generated constructor stub
        super(ZWareBindingConstants.SUPPORTED_DEVICE_THING_TYPES_UIDS, SEARCH_TIME);
    }

    public void activate() {
        logger.debug("Activating ZWave discovery service for {}", controllerHandler.getThing().getUID());
    }

    @Override
    public void deactivate() {
        logger.debug("Deactivating ZWave discovery service for {}", controllerHandler.getThing().getUID());
    }

    private void scan(String type) {
        // TODO Auto-generated method stub
        String url = ZWareBindingConstants.hosts + type;
        Map<String, String> map = new HashMap<>();

        if (type.equals(ZWareBindingConstants.URL_INCLUDE_EXCLUDE)) {
            map.put("cmd", "2");
            map.put("&", "");
            String resp = HttpUtils.httpPost(url, null);
            if (resp != null) {
                thingDiscovered(null);
            }
            logger.error(HttpUtils.httpPost(ZWNET_GET_NODESLIST, null));
            ThingUID thingUID = new ThingUID(ZWareBindingConstants.ZWAVE_THING_UID, "node1");

            // Attention: if is already present as thing in the ThingRegistry
            // the configuration for thing will be updated!
            DiscoveryResult discoveryResult = DiscoveryResultBuilder.create(thingUID)
                    .withProperty(ZWareBindingConstants.User, "zware").withLabel("Zware Device").build();
            thingDiscovered(discoveryResult);
        }

        String resp = HttpUtils.httpPost(url, null);
        thingDiscovered(null);

    }

    @Override
    protected void startScan() {
        scan(ZWareBindingConstants.URL_INCLUDE_EXCLUDE);
    }

    @Override
    protected synchronized void stopScan() {
        scan(ZWareBindingConstants.URL_ABORT_NETWORK);
        super.stopScan();
    }

    @Override
    public synchronized void abortScan() {
        scan(ZWareBindingConstants.URL_ABORT_NETWORK);
        super.abortScan();
    }

    public class ZWayDeviceScan extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                String resp = HttpUtils.httpPost(ZWNET_GET_OPERATION, null);
                logger.error(resp);
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    // TODO: handle exception
                    logger.error(e.getStackTrace().toString());
                }

            }
        }
    }

    @Override
    public void setDiscoveryServiceCallback(DiscoveryServiceCallback discoveryServiceCallback) {
        // TODO Auto-generated method stub
        this.discoveryServiceCallback = discoveryServiceCallback;
    }
}
