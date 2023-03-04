package com.junit5;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junit5.employees.services.MessageService;

@RestController
public class MessageController {
	
	@Resource
	private MessageService messageService;  

	@GetMapping("/hello")
	public String hello() {
		return messageService.generateMessage();
	}

}