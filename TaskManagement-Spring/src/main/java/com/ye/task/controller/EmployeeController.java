package com.ye.task.controller;

import com.ye.task.dto.TaskDto;
import com.ye.task.service.employee.EmployeeService;
import com.ye.task.service.employee.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> getTasksByUserId(){
        return ResponseEntity.ok(employeeService.getTasksByUserId());
    }
}
