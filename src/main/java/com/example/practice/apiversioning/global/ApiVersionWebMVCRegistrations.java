package com.example.practice.apiversioning.global;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@AllArgsConstructor
//@RequiredArgsConstructor
public class ApiVersionWebMVCRegistrations implements WebMvcRegistrations {

    private ApiVersionProperties apiVersionProperties;

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new ApiVersionRequestMappingHandlerMapping(apiVersionProperties);
    }
}
