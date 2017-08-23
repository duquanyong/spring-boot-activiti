package com.dqy.spring.boot.activiti;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import com.dqy.spring.boot.activiti.entity.Person;
import com.dqy.spring.boot.activiti.service.MyService;

@Configuration
@ComponentScan(basePackages = {
		"com.dqy.spring.boot.activiti" }, excludeFilters = @Filter(type = FilterType.REGEX, pattern = {
				"com.dqy.activiti.sdk.log.*" }))
@EnableAutoConfiguration
public class SpringBootActiviti1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootActiviti1Application.class, args);
	}

	@Bean
	public CommandLineRunner init(final RepositoryService repositoryService, final RuntimeService runtimeService,
			final TaskService taskService) {
		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {
				System.out.println(
						"Number of process definitions : " + repositoryService.createProcessDefinitionQuery().count());
				System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
				Person person = new Person("duquanyong", "du", "quanyong", new Date());
				person.setId(1l);
				Map<String, Object> params = new HashMap<String,Object>();
				params.put("person", person);
				runtimeService.startProcessInstanceByKey("oneTaskProcess",params);
				System.out.println("Number of tasks after process start: " + taskService.createTaskQuery().count());
			}
		};
	}

	@Bean
	public CommandLineRunner init(final MyService myService) {
		return new CommandLineRunner() {
			public void run(String... strings) throws Exception {
				myService.createDemoUsers();
			}
		};
	}

}
