package com.sergio.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

// We are able to use the @EnableZuulServer annotation. This annotation
// does not load any Zuul capabilities (like enable reverse proxy filters or
// native communication with Eureka). This is used when you want to build a
// custom routing implementation.
// @EnableZuulProxy automatically communicates with Eureka for service discovery
// and will use Ribbon as client-side load balancing
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class ZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}
}
