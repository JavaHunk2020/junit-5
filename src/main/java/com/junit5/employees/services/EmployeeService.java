package com.junit5.employees.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.junit5.employees.dao.EmployeeRepository;
import com.junit5.employees.handlers.RecordNotFoundException;
import com.junit5.employees.model.Employee;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	/*
	 * @Autowired private RestTemplate restTemplate;
	 * 
	 * 
	 * public RestTemplate getRestTemplate() { return restTemplate; }
	 * 
	 * public void setRestTemplate(RestTemplate restTemplate) { this.restTemplate =
	 * restTemplate; }
	 */

	public Employee save(Employee employee) {
		if (employee.getId() == null) {
			employee = employeeRepository.save(employee);
			return employee;
		} else {
			Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

			if (employeeOptional.isPresent()) {
				Employee newEntity = employeeOptional.get();
				newEntity.setFirstName(employee.getFirstName());
				newEntity.setLastName(employee.getLastName());

				newEntity = employeeRepository.save(newEntity);

				return newEntity;
			} else {
				throw new RecordNotFoundException("No employee record exist for given id");
			}
		}
	}

	public List<Employee> findAll() {
		List<Employee> result = (List<Employee>) employeeRepository.findAll();

		if (result.size() > 0) {
			return result;
		} else {
			return new ArrayList<Employee>();
		}
	}

	public void deleteById(Integer id) {
		Optional<Employee> employee = employeeRepository.findById(id);

		if (employee.isPresent()) {
			employeeRepository.deleteById(id);
		} else {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
	}

	void deleteAll() {
		employeeRepository.deleteAll();
	}
	
	/*
	 * public String findMagic(String loanid) throws JsonProcessingException {
	 * Employee employee=new Employee(); employee.setFirstName("Nagen");
	 * employee.setId(12); employee.setLastName("Singh"); String jsonResponse=new
	 * ObjectMapper().writeValueAsString(employee); // ResponseEntity<String>
	 * responseEntity=new ResponseEntity<>(jsonResponse,HttpStatus.OK);
	 * RequestEntity<String>
	 * request=RequestEntity.post(URI.create("wadas")).body(jsonResponse);
	 * ResponseEntity<String> responseEntity=restTemplate.exchange(request,
	 * String.class); String str=responseEntity.getBody(); return str; }
	 * 
	 */	
}
