package com.example.practice.apiversioning.global;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "api.version")
public class ApiVersionProperties {

    private Type type = Type.URI;
    private UriLocation uriLocation = UriLocation.BEGIN;
    private String header = "X-API-VERSION";
    private String param = "api_version";

    private String uriPrefix;

    enum Type {
        URI,
        HEADER,
        PARAM
    }

    enum UriLocation {
        BEGIN,
        END
    }
}
