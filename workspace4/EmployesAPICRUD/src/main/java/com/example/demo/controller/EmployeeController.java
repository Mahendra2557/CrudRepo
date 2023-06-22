package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;



@RestController
//@RequestMapping("/api")
public class EmployeeController {
	@Autowired
	EmployeeRepository employeerepository;
	
	@PostMapping("/employee")
	public String createNewEmployee(@RequestBody Employee employee) {
		employeerepository.save(employee);
		return "product is saved";
	
}
	
	@GetMapping("/employee")
	public ResponseEntity<List<Employee>> getAllEmployee() {
		List<Employee> empList=new ArrayList<>();
		employeerepository.findAll().forEach(empList::add);
		return new ResponseEntity<List<Employee>>(empList,HttpStatus.OK);		
}
	
	@GetMapping("/employee/id")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable int empid) {
		//List<Employee> empList=new ArrayList<>();
		Optional<Employee> emp= employeerepository.findById(empid);
		//return new ResponseEntity<List<Employee>>(empList,HttpStatus.OK);
		if(emp.isPresent()) {
			return new ResponseEntity<Employee>(emp.get(),HttpStatus.FOUND);
		}else {
			return new ResponseEntity<Employee>(emp.get(),HttpStatus.NOT_FOUND);
		}
}
	@PutMapping("/employee/{empid}")
	public String updateEmployeeById(@PathVariable int empid,@RequestBody Employee employee)
	{
		Optional<Employee> emp=employeerepository.findById(empid);
		if(emp.isPresent()) {
			Employee existEmp= emp.get();
			existEmp.setName(employee.getName());
			existEmp.setSalary(employee.getSalary());
			employeerepository.save(existEmp);
			return "employee details "+empid+"updated" ;
		}else {
			return "doesnt exist";
		}
		
	}
	@DeleteMapping("/employee/{empid}")
	public String deleteEmployeeById(@PathVariable int empid)
	{
		employeerepository.deleteById(empid);
		return "employee deleted successfully";
	}
	@DeleteMapping("/employees")
	public String deleteEmployee()
	{
		employeerepository.deleteAll();
		return "all employee deleted successfully";
	}
	
	
	
	
}
