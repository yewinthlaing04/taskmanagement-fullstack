package com.ye.task.service.admin;

import com.ye.task.dto.TaskDto;
import com.ye.task.dto.UserDto;
import com.ye.task.entity.Task;
import com.ye.task.entity.User;
import com.ye.task.enums.TaskStatus;
import com.ye.task.enums.UserRoles;
import com.ye.task.exception.TaskNotFoundException;
import com.ye.task.repo.TaskRepository;
import com.ye.task.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream()
                .filter( user -> user.getUserrole() == UserRoles.EMPLOYEE)
                .map(User::getUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {

        // find user with id

        // if user exist
        // create task dto with repo

        Optional<User> optionalUser = userRepository.findById(taskDto.getEmployeeId());

        if ( optionalUser.isPresent() ) {
            Task task = new Task();

            task.setTitle(taskDto.getTitle());
            task.setDescription(taskDto.getDescription());
            task.setPriority(taskDto.getPriority());
            task.setTaskStatus(TaskStatus.INPROGRESS);
            task.setUser(optionalUser.get());
            task.setDueDate(taskDto.getDueDate());
            return taskRepository.save(task).getTaskDto();
        }

        return null;
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream()
                .sorted(Comparator.comparing(Task::getDueDate).reversed())
                .map(Task::getTaskDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTask(Long id) {

        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            taskRepository.deleteById(id);
        } else {
            throw new TaskNotFoundException("Task not found with id " + id);
        }
    }

    @Override
    public TaskDto getTaskById(Long id) {

        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.map(Task::getTaskDto).orElse(null);
    }

    @Override
    public TaskDto updateTask(Long id, TaskDto taskDto) {

        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()){
            Task existingTask = optionalTask.get();
            existingTask.setTitle(taskDto.getTitle());
            existingTask.setDescription(taskDto.getDescription());
            existingTask.setDueDate(taskDto.getDueDate());
            existingTask.setPriority(taskDto.getPriority());
            existingTask.setTaskStatus(mapStringToTaskStatus(String.valueOf(taskDto.getTaskStatus())));
            return taskRepository.save(existingTask).getTaskDto();
        }
        return null;
    }

    private TaskStatus mapStringToTaskStatus(String status){
        return switch(status){
            case "PENDING" -> TaskStatus.PENDING;
            case "INPROGRESS" -> TaskStatus.INPROGRESS;
            case "COMPLETED" -> TaskStatus.COMPLETED;
            case "DEFERRED" -> TaskStatus.DEFERRED;
            default -> TaskStatus.CANCELLED;
        };
    }
}
