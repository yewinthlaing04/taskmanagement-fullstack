package com.ye.task.service.employee;

import com.ye.task.dto.TaskDto;
import com.ye.task.entity.Task;
import com.ye.task.entity.User;
import com.ye.task.repo.TaskRepository;
import com.ye.task.utils.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public List<TaskDto> getTasksByUserId() {

        User user = jwtUtil.getLoggedInUser();
        if ( user != null ){
            return taskRepository.findAllByUserId(user.getId()).stream()
                    .sorted(Comparator.comparing(Task::getDueDate).reversed())
                    .map(Task::getTaskDto)
                    .collect(Collectors.toList());
        }
       throw new EntityNotFoundException("User not found ");
    }
}
