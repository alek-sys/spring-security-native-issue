package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.configuration.ObjectPostProcessorConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

class SomeBean {

}

class AnotherBean {

	private SomeBean someBean;

	@Autowired(required = false)
	public void setSomeBean(SomeBean someBean) {
		this.someBean = someBean;
	}

	public SomeBean getSomeBean() {
		return someBean;
	}
}

@RestController
class TestController {

	final List<ObjectPostProcessor<Object>> objectPostProcessors;

	public TestController(List<ObjectPostProcessor<Object>> objectPostProcessors) {
		this.objectPostProcessors = objectPostProcessors;
	}

	@GetMapping("/")
	String index() {
		AnotherBean anotherBean = new AnotherBean();
		for (ObjectPostProcessor<Object> objectPostProcessor : objectPostProcessors) {
			anotherBean = objectPostProcessor.postProcess(anotherBean);
		}
		String message = anotherBean.getSomeBean() != null ? "Autowired" : "Not autowired";
		return message + ", " + objectPostProcessors.size() + " object post processor(s) found";
	}
}

@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().permitAll();
	}
}

@SpringBootApplication
public class DemoApplication {

	@Bean
	SomeBean bean() {
		return new SomeBean();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
