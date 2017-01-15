//package com.erdaoya.springcloud.gateway.oauth;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//
///**
// *
// * @author erdaoya
// */
//public class ResourceConfig extends ResourceServerConfigurerAdapter{
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers("/**")
//                .denyAll();
//    }
//
//}
