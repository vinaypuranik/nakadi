package de.zalando.aruha.nakadi.util;

import de.zalando.aruha.nakadi.repository.zookeeper.ZooKeeperHolder;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class FeatureToggleService {

    private static final Logger LOG = LoggerFactory.getLogger(FeatureToggleService.class);

    @Value("${nakadi.featureToggle.enableAll}")
    private boolean forceEnableAll;

    private final ZooKeeperHolder zkHolder;
    private final TimeBasedCache<String, Boolean> cachedValues = new TimeBasedCache<>(5000);

    public FeatureToggleService(final ZooKeeperHolder zkHolder) {
        this.zkHolder = zkHolder;
    }

    public boolean isFeatureEnabled(final String feature) {
        if (forceEnableAll) {
            return true;
        }
        return cachedValues.getOrCalculate(feature, this::isFeatureEnabledInZk);
    }

    private Boolean isFeatureEnabledInZk(final String feature) {
        try {
            final Stat stat = zkHolder.get().checkExists().forPath("/nakadi/feature_toggle/" + feature);
            return stat != null;
        } catch (final Exception e) {
            LOG.warn("Error occurred when checking if feature '" + feature + "' is toggled", e);
            return false;
        }

    }
}
