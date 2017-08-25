package com.dqy.spring.boot.activiti.controller;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.dqy.spring.boot.activiti.service.MyService;
import com.fasterxml.jackson.core.JsonParser;
import net.minidev.json.parser.JSONParser;

@RestController
public class MyRestController {
	@Autowired
	private MyService myService;

	@RequestMapping(value = "/process", method = RequestMethod.POST)
	public String startProcessInstance(@RequestBody StartProcessRepresentation startProcessRepresentation) {
		myService.startProcess(startProcessRepresentation.getAssignee());
		return "success";
	}
	@RequestMapping(value = "/test", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public String test() {
		return "success";
	}
	@RequestMapping(value = "/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
		List<Task> tasks = myService.getTasks(assignee);
		List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
		for (Task task : tasks) {
			dtos.add(new TaskRepresentation(task.getId(), task.getName()));
		}
		return dtos;
	}

	static class TaskRepresentation {

		private String id;
		private String name;

		public TaskRepresentation(String id, String name) {
			this.id = id;
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	static class StartProcessRepresentation {
		private String assignee;

		public String getAssignee() {
			return assignee;
		}

		public void setAssignee(String assignee) {
			this.assignee = assignee;
		}
	}
	
	public static void main(String args[]) {
		StartProcessRepresentation s = new StartProcessRepresentation();
		s.setAssignee("Joram");
		System.out.println(JSON.toJSONString(s));
	}
	
}
