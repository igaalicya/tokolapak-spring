package com.cimb.tokolapak.service;

import com.cimb.tokolapak.entity.Employee;
import com.cimb.tokolapak.entity.EmployeeAddress;

public interface EmployeeService  {
	public void deleteEmployeeAddress(EmployeeAddress employeeAddress);
	public Employee updateEmployeeAddress(Employee employee);
}
