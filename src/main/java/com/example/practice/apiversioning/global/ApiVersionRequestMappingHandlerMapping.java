package com.example.practice.apiversioning.global;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Objects;

import static com.example.practice.apiversioning.global.ApiVersionProperties.Type.*;
import static com.example.practice.apiversioning.global.ApiVersionProperties.UriLocation.*;

@Slf4j
@RequiredArgsConstructor
public class ApiVersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private final ApiVersionProperties apiVersionProperties;

    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        return createRequestCondition(handlerType);
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        return createRequestCondition(method);
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = this.createRequestMappingInfo(method);
        if(Objects.nonNull(info)) {
            RequestMappingInfo typeInfo = this.createRequestMappingInfo(handlerType);
            if(Objects.nonNull(typeInfo)) {
                info = typeInfo.combine(info);
            }

            if(apiVersionProperties.getType().equals(URI)) {
                ApiVersion apiVersion = AnnotationUtils.getAnnotation(method, ApiVersion.class);

                if(Objects.isNull(apiVersion)) {
                    apiVersion = AnnotationUtils.getAnnotation(handlerType, ApiVersion.class);
                }
                if(Objects.nonNull(apiVersion)) {
                    String version = apiVersion.value().trim();
                    Utils.checkVersionNumber(version, method);

                    String prefix = "/v" + version;
                    if(apiVersionProperties.getUriLocation().equals(END)) {
                        info = info.combine(RequestMappingInfo.paths(prefix).options(getBuilderConfiguration()).build());
                    } else {
                        if(StringUtils.hasText(apiVersionProperties.getUriPrefix())) {
                            prefix = apiVersionProperties.getUriPrefix().trim() + prefix;
                        }
                        info = RequestMappingInfo.paths(prefix).options(getBuilderConfiguration()).build().combine(info);
                    }
                }
            }
        }
        return info;
    }

    private RequestCondition<ApiVersionRequestCondition> createRequestCondition(AnnotatedElement target) {
        if(apiVersionProperties.getType().equals(URI)) return null;

        ApiVersion apiVersion = AnnotationUtils.findAnnotation(target, ApiVersion.class);
        if(Objects.isNull(apiVersion)) return null;

        String version = apiVersion.value().trim();
        Utils.checkVersionNumber(version, target);

        return new ApiVersionRequestCondition(version, apiVersionProperties);
    }

    private RequestMappingInfo createRequestMappingInfo(AnnotatedElement element) {
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping.class);
        RequestCondition<?> condition = (element instanceof Class ? getCustomTypeCondition((Class<?>) element) : getCustomMethodCondition((Method) element));

        return Objects.nonNull(requestMapping) ? createRequestMappingInfo(requestMapping, condition) : null;
    }
}
