package com.cimb.tokolapak.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.tokolapak.dao.DepartmentRepo;
import com.cimb.tokolapak.dao.EmployeeAddressRepo;
import com.cimb.tokolapak.dao.EmployeeRepo;
import com.cimb.tokolapak.dao.ProjectRepo;
import com.cimb.tokolapak.entity.Department;
import com.cimb.tokolapak.entity.Employee;
import com.cimb.tokolapak.entity.EmployeeAddress;
import com.cimb.tokolapak.entity.Project;
import com.cimb.tokolapak.service.EmployeeService;
import com.cimb.tokolapak.util.EmailUtil;

@RestController
@RequestMapping("/employee")
// cara nyambungin ke FE harus ditambahin cross origin
// buat nentuin URL mana yang bisa mengakses API
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeAddressRepo employeeAddressRepo;
	
	@Autowired
	private DepartmentRepo departmentRepo;
	
	@Autowired
	private ProjectRepo projectRepo;
	
	@Autowired
	private EmailUtil emailUtil;
	
//	@PostMapping
//	public Employee addEmployee(@RequestBody Employee employee) {
//		return employeeRepo.save(employee);
//	}
	
	@GetMapping
	public Iterable<Employee> getEmployees(){
		return employeeRepo.findAll();
	}
	
	@DeleteMapping("/address/{id}")
	public void deleteEmployeeAddressById(@PathVariable int id) {
		Optional<EmployeeAddress> employeeAddress = employeeAddressRepo.findById(id);
		employeeService.deleteEmployeeAddress(employeeAddress.get());
}
	
	// Weekend Task
	
//	@PutMapping("/{id}")
//	public Employee updateEmployeeAddress(@RequestBody Employee employee) { 
//		 return employeeService.updateEmployeeAddress(employee);
//	}
	
	@PutMapping("/{employeeId}/address")
	public Employee addAddressToEmployee(@RequestBody EmployeeAddress employeeAddress, @PathVariable int employeeId) {
		Employee findEmployee = employeeRepo.findById(employeeId).get();

		if (findEmployee == null)
			throw new RuntimeException("Employee not found");

		findEmployee.setEmployeeAddress(employeeAddress);

		return employeeRepo.save(findEmployee); 
	}
	
	@PostMapping("/department/{departmentId}")
	public Employee addEmployee(@RequestBody Employee employee, @PathVariable int departmentId) {
		Department findDepartment = departmentRepo.findById(departmentId).get();

		if (findDepartment == null)
			throw new RuntimeException("Department not found");

		employee.setDepartment(findDepartment);
		emailUtil.sendEmail(employee.getEmail(), "Registrasi Email Karyawan", "<h1>Selamat</h1>");
		
		return employeeRepo.save(employee);
	}
	
	@PutMapping("/{employeeId}/department/{departmentId}")
	public Employee addEmployeeToDepartment(@PathVariable int departmentId, @PathVariable int employeeId) {
		// Employee didapatkan dari ID yang kita kirim ({employeeId})
		Employee findEmployee = employeeRepo.findById(employeeId).get(); 

		// Jika tidak ada employee dengan ID tsbt, throw error
		if (findEmployee == null)
			throw new RuntimeException("Employee not found");

		Department findDepartment = departmentRepo.findById(departmentId).get();

		if (findDepartment == null)
			throw new RuntimeException("Department not found");

		findEmployee.setDepartment(findDepartment);

		return employeeRepo.save(findEmployee);

		// 1. Carikan department yang memiliki ID = {departmentId}
		// 2. Lalu gunakan method map, yang akan mendapatkan sebuah parameter "department"
		// 3. "department" akan berisi Department yang memiliki ID = {departmentId} (point nomor 1)
		// 4. Lalu, set field department si employee dengan department yang kita dapatkan dari parameter map ("department")
		// 5. Save employee nya
	}

	@PostMapping("/{employeeId}/projects/{projectId}")
	public Employee addProjectToEmployee(@PathVariable int employeeId, @PathVariable int projectId) {
		Employee findEmployee = employeeRepo.findById(employeeId).get();
		
		Project findProject = projectRepo.findById(projectId).get();

		findEmployee.getProjects().add(findProject);
		
		return employeeRepo.save(findEmployee);
	}
}
