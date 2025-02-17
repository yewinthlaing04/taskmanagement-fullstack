package com.ye.task.service.employee;

import com.ye.task.dto.TaskDto;

import java.util.List;

public interface EmployeeService {

    List<TaskDto> getTasksByUserId();
}
