package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    private TaskService taskService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping({"/addTask"})
    public Task addTask(@RequestBody Task task) {
        return taskService.addTask(task);
    }

    @PostMapping({"/addTasks"})
    public List<Task> addTasks(@RequestBody List<Task> tasks) {
        return taskService.addMultipleTasks(tasks);
    }

    @GetMapping({"/tasks"})
    public List<Task> findAllTasks(@RequestParam(required = false) String taskStatus) {
        if ("ToDo".equals(taskStatus)) {
            return taskService.getToDoTasks();
        } else if ("Doing".equals(taskStatus)) {
            return taskService.getDoingTasks();
        } else if ("Done".equals(taskStatus)) {
            return taskService.getDoneTasks();
        } else {
            return taskService.getAllTasks();
        }
    }

    @GetMapping({"/task/{id}"})
    public Task findTaskByID(@PathVariable int id) {
        return taskService.getTaskById(id);
    }

    @Transactional
    @DeleteMapping("/deleteTaskById/{id}")
    public String deleteTaskById(@PathVariable int id) {
        return taskService.deleteTaskById(id);
    }

    @PutMapping("/updateTask")
    public Task updateTask(@RequestBody Task task) {
        return taskService.updateTaskStatus(task);
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler() {
        return "Application has encountered a problem. \nPlease check that the details provided are correct. ";
    }
}
