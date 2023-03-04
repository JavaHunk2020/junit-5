package com.junit5.employees.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.junit5.employees.dao.EmployeeRepository;
import com.junit5.employees.handlers.RecordNotFoundException;
import com.junit5.employees.model.Employee;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
	
	@Mock  //creating fack instance of employeeRepository
	private EmployeeRepository employeeRepository;
	
	@InjectMocks // means making instance of EmployeeService 
	private EmployeeService employeeService;
	
	/*public Employee save(Employee employee) {
		if (employee.getId() == null) {
			employee = employeeRepository.save(employee);
			return employee;
		}*/
	
	@Test
	public void testSaveEmployee() {
		
		Employee employee=new Employee();
		employee.setFirstName("Nagendra");
		employee.setLastName("Kumar");
		
		Employee oemployee=new Employee();
		oemployee.setFirstName("Nagendra");
		oemployee.setLastName("Kumar");
		oemployee.setId(100);
		
		when(employeeRepository.save(employee)).thenReturn(oemployee);
		
		Employee serviceEmployee=employeeService.save(employee);
		assertNotNull(serviceEmployee);
		assertEquals("Nagendra", serviceEmployee.getFirstName());
		assertEquals("Kumar", serviceEmployee.getLastName());
		assertEquals(100, serviceEmployee.getId());
		verify(employeeRepository, times(1)).save(employee);
	}
	
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
	
	
	@Test
	public void testUpdateEmployeeWhenExist() {

		Employee employee = new Employee();
		employee.setFirstName("Nagendra");
		employee.setLastName("Kumar");
		employee.setId(100);
		
		Employee cemployee = new Employee();
		cemployee.setFirstName("Rohit");
		cemployee.setLastName("Singh");
		cemployee.setId(100);
		//mocking it
		when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
		when(employeeRepository.save(any())).thenReturn(cemployee);
		
		
		Employee serviceEmployee=employeeService.save(cemployee);
		assertNotNull(serviceEmployee);
		assertEquals("Rohit", serviceEmployee.getFirstName());
		assertEquals("Singh", serviceEmployee.getLastName());
		assertEquals(100, serviceEmployee.getId());
		verify(employeeRepository, times(1)).findById(employee.getId());
		verify(employeeRepository, times(1)).save(employee);
	}
	
	
	@Test
	public void testUpdateEmployeeWhenNotExist() {

		Employee employee = new Employee();
		employee.setFirstName("Nagendra");
		employee.setLastName("Kumar");
		employee.setId(100);
		when(employeeRepository.findById(employee.getId())).thenReturn(Optional.empty());

		RecordNotFoundException thrown = Assertions.assertThrows(RecordNotFoundException.class, () -> {
			employeeService.save(employee);
		});

		Assertions.assertEquals("No employee record exist for given id", thrown.getMessage());
		verify(employeeRepository, times(1)).findById(employee.getId());
	}
	
}
