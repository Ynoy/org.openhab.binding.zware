package org.openhab.binding.zware.discovery;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.config.discovery.DiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryServiceCallback;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.openhab.binding.zware.handler.ZWareBridgeHandler;
import org.openhab.binding.zware.internal.ZWareBindingConstants;
import org.openhab.binding.zware.utils.HttpUtils;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hao.httputil.OkHttpUtils;;

@Component(service = DiscoveryService.class, immediate = true, configurationPid = "discovery.zware")
public class ZWareDiscoveryService extends AbstractDiscoveryService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final int SEARCH_TIME = 60 * 4;

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

    private void scan() {
        // TODO Auto-generated method stub
        String url = ZWareBindingConstants.hosts + ZWareBindingConstants.URL_INCLUDE_EXCLUDE;
        Map<String, String> map = new HashMap<>();
        HttpUtils httpUtils = new HttpUtils();
        map.put("cmd", "2");
        map.put("&", "");
        String resp = HttpUtils.httpPost(url, null);

        logger.error(HttpUtils.httpPost(ZWNET_GET_NODESLIST, null));
        ThingUID thingUID = new ThingUID(ZWareBindingConstants.ZWAVE_THING_UID, "node1");

        // Attention: if is already present as thing in the ThingRegistry
        // the configuration for thing will be updated!
        DiscoveryResult discoveryResult = DiscoveryResultBuilder.create(thingUID)
                .withProperty(ZWareBindingConstants.User, "zware").withLabel("Zware Device").build();
        thingDiscovered(discoveryResult);

    }

    @Override
    protected void startScan() {
        scan();
    }

    @Override
    protected synchronized void stopScan() {
        stop();
        super.stopScan();
        removeOlderResults(getTimestampOfLastScan());

    }

    private void stop() {
        // TODO Auto-generated method stub
    }

    @Override
    public synchronized void abortScan() {
        stop();
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

}
