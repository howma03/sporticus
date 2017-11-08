package com.sporticus.web.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.io.IOException;

@EnableWebMvc
@Configuration
public class ConfigurationWeb extends WebMvcConfigurerAdapter {
    // All web configuration will go here

    @Autowired
    private ResourceProperties resourceProperties;
    private String apiPath = "/api";
    private String apiPublicPath = "/papi";

    @Bean
    public ViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/jsp/");
        bean.setSuffix(".jsp");
        return bean;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/resources/**")) {
            registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        }
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
        }
        if (!registry.hasMappingForPattern("/app/**")) {
            registry.addResourceHandler("/app/**").addResourceLocations("/resources/app/");
        }
        registry.addResourceHandler("/**")
                .addResourceLocations(resourceProperties.getStaticLocations())
                .setCachePeriod(resourceProperties.getCachePeriod()).resourceChain(true)
                .addResolver(new SinglePageAppResourceResolver());
    }

    protected String getApiPath() {
        return apiPath;
    }

    protected String getApiPublicPath() {
        return apiPublicPath;
    }

    private class SinglePageAppResourceResolver extends PathResourceResolver {
        @Override
        protected Resource getResource(String resourcePath, Resource location) throws IOException {
            Resource resource = location.createRelative(resourcePath);
            if (resource.exists() && resource.isReadable()) {
                return resource;
            }
            if (getApiPath() != null && ("/" + resourcePath).startsWith(getApiPath())) {
                return null;
            }
            if(getApiPublicPath() != null && ("/" + resourcePath).startsWith(getApiPublicPath())) {
                return null;
            }

            LoggerFactory.getLogger(getClass()).info("Routing /" + resourcePath + " to /index.html");
            resource = location.createRelative("/resources/app/" + resourcePath);
            LoggerFactory.getLogger(getClass()).info("Routing to " + resource);
            if (resource.exists() && resource.isReadable()) {
                return resource;
            }
            return null;
        }
    }
}