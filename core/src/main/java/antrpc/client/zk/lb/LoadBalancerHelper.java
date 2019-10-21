package antrpc.client.zk.lb;

import antrpc.client.Host;
import antrpc.commons.config.IConfiguration;
import antrpc.commons.constants.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoadBalancerHelper {

    private ILoadBalancer loadBalancer;

    public LoadBalancerHelper(IConfiguration configuration) {
        try {
            loadBalancer = (ILoadBalancer) configuration.getLoadBalancerName().newInstance();
        } catch (Exception e) {
            String message =
                    "Failed to instantiate ILoadBalancer, check the "
                            + Constants.RPC_LOAD_BALANCER_PROP_NAME
                            + " configuration. The value of the current configuration is "
                            + configuration.getLoadBalancerName().getName();
            if (log.isWarnEnabled()) {
                log.error(message, e);
            }
            throw new RuntimeException(message, e);
        }
    }

    public <T extends Host> ILoadBalancer<T> getLoadBalancer(Class<T> elementType) {
        return loadBalancer;
    }
}