package com.fy.flower.sample;

import com.ly.train.flower.web.spring.context.FlowerComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.context.annotation.Bean;

/**
 * @author fyu
 */
@SpringBootApplication
@FlowerComponentScan("com.fy.flower.sample")
public class SampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }
    @Bean
    public JettyServletWebServerFactory servletContainer(){
        return new JettyServletWebServerFactory();
    }
}
