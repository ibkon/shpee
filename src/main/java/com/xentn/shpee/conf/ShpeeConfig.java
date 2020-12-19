package com.xentn.shpee.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "shpee")
@Data
public class ShpeeConfig {
    private String uploadPath;
}
