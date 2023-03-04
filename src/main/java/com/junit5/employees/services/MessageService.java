package com.junit5.employees.services;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


//2006 ->> spring 1.3
//request,singleton,prototype, session,global session, ->> 4.3 ->> application ,socket
@Service
@Scope("singleton")
public class MessageService {
		
	public String generateMessage() {
	    return "hmmmm";	
	}
		

}
