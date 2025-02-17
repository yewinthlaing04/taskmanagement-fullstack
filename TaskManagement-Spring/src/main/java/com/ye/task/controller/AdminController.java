package com.ye.task.controller;

import com.ye.task.dto.TaskDto;
import com.ye.task.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id  ){
        adminService.deleteTask(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id ) {
        return ResponseEntity.ok(adminService.getTaskById(id));
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<?> updateTask( @PathVariable Long id , @RequestBody TaskDto taskDto ) {
            TaskDto updatedTask = adminService.updateTask(id , taskDto );
            if ( updatedTask == null ) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.ok(updatedTask);
    }

    @GetMapping("/tasks/search/{title}")
    public ResponseEntity<List<TaskDto>> searchTask(@PathVariable String title ) {
        return ResponseEntity.ok(adminService.searchTaskByTitle(title));
    }
}
