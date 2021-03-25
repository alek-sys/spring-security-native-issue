package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
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

	final AutowireCapableBeanFactory autowireCapableBeanFactory;

	public TestController(AutowireCapableBeanFactory autowireCapableBeanFactory) {
		this.autowireCapableBeanFactory = autowireCapableBeanFactory;
	}

	@GetMapping("/")
	String index() {
		AnotherBean anotherBean = new AnotherBean();
		autowireCapableBeanFactory.autowireBean(anotherBean);
		return anotherBean.getSomeBean() != null ? "Autowired" : "Not autowired";
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
