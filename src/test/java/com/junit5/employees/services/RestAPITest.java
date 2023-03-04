package com.junit5.employees.services;

import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.junit5.employees.model.Employee;

@ExtendWith(MockitoExtension.class)
public class RestAPITest {
	
	@Mock
  private RestTemplate restTemplate;
  
	@InjectMocks
  private EmployeeService employeeService;
  
	/*
	 * @BeforeEach public void init() { restTemplate= mock(RestTemplate.class);
	 * employeeService=new EmployeeService();
	 * employeeService.setRestTemplate(restTemplate); }
	 */
  public String findMagic(int loanid) throws JsonProcessingException {
		Employee employee=new Employee();
		employee.setFirstName("Nagen");
		employee.setId(12);
		employee.setLastName("Singh");
		String jsonResponse=new ObjectMapper().writeValueAsString(employee);
	//	ResponseEntity<String> responseEntity=new ResponseEntity<>(jsonResponse,HttpStatus.OK);
		RequestEntity<String> request=RequestEntity.post(URI.create("wadas")).body(jsonResponse);
		ResponseEntity<String> responseEntity=restTemplate.exchange(request, String.class);
		String str=responseEntity.getBody();
		return str;
	}
	/*
	 * @Test public void test() throws IOException {
	 * 
	 * Employee employee=new Employee();
	 * 
	 * File file = ResourceUtils.getFile("classpath:data/data.xml"); String response
	 * = Files.readAllLines(file.toPath()).stream().collect(Collectors.joining());
	 * 
	 * Mockito.doReturn(new ResponseEntity<String>(response,
	 * HttpStatus.OK)).when(restTemplate).exchange( ArgumentMatchers.any(),
	 * ArgumentMatchers.<Class<String>>any());
	 * 
	 * String result=employeeService.findMagic("L102929");
	 * System.out.println(result); }
	 */
	

}
