package com.ye.task.service.admin;

import com.ye.task.dto.TaskDto;
import com.ye.task.dto.UserDto;

import java.util.List;

public interface AdminService {

    List<UserDto> getUsers();

    TaskDto createTask(TaskDto taskDto);

    List<TaskDto> getAllTasks();

    void deleteTask(Long id);

    TaskDto getTaskById(Long id );

    TaskDto updateTask(Long id , TaskDto taskDto);
}
