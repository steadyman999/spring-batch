package com.steadyman.tuto.springbatch.repository;

import com.steadyman.tuto.springbatch.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
