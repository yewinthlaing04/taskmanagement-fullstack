package com.ye.task.controller;

import com.ye.task.dto.TaskDto;
import com.ye.task.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
//@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok((adminService.getUsers()));
    }

    @RequestMapping(value = "/users", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptions(){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create-task")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto){
        TaskDto newTask = adminService.createTask(taskDto);
        if ( newTask == null ) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTasks(){
        return ResponseEntity.ok(adminService.getAllTasks());
    }

}
