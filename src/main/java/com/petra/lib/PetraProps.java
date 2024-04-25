package com.petra.lib;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "petra")
public class PetraProps {

    @Getter
    @Setter
    private List<ServiceModel> services;


    @Value("${thread.count}")
    @Getter
    private Integer threadsCount;

    public Map<String, Object> getPropsMap(String... propPath) {
        return Collections.emptyMap();
    }

    public Object getProp(String... propPath) {
        return null;
    }
}
