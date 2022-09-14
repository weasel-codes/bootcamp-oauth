package com.wibmo.bootcamp.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class JWTConfiguration extends WebMvcConfigurerAdapter {

  @Autowired 
  JWTAuthInterceptor intercept;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(intercept).addPathPatterns("/**").excludePathPatterns("/auth/**");
  }
}
