package com.example.practice.apiversioning.global;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

public record ApiVersionRequestCondition(String apiVersion, ApiVersionProperties apiVersionProperties) implements RequestCondition<ApiVersionRequestCondition> {

    public ApiVersionRequestCondition(@NonNull String apiVersion, @NonNull ApiVersionProperties apiVersionProperties) {
        this.apiVersion = apiVersion.trim();
        this.apiVersionProperties = apiVersionProperties;
    }

    @Override
    public ApiVersionRequestCondition combine(ApiVersionRequestCondition other) {
        return new ApiVersionRequestCondition(other.apiVersion(), other.apiVersionProperties());
    }

    @Override
    public int compareTo(ApiVersionRequestCondition other, HttpServletRequest request) {
        return other.apiVersion().compareTo(apiVersion());
    }

    @Override
    public ApiVersionRequestCondition getMatchingCondition(HttpServletRequest request) {
        ApiVersionProperties.Type type = apiVersionProperties.getType();
        String version = switch (type) {
            case HEADER -> request.getHeader(apiVersionProperties.getHeader());
            case PARAM -> request.getParameter(apiVersionProperties.getParam());
            default -> null;
        };

        boolean match = version != null && !version.isEmpty() && version.trim().equals(apiVersion);
        if (match) return this;

        return null;
    }

    @Override
    public String toString() {
        return "@ApiVersion(" + apiVersion + ")";
    }
}
