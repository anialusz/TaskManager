package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> addMultipleTasks(List<Task> task) {
        return taskRepository.saveAll(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(int id) {
        return taskRepository.findById(id);
    }

    public List<Task> getToDoTasks() {
        return taskRepository.findAll().stream().filter(x -> x.getTaskStatus().toString().equals("ToDo")).collect(Collectors.toList());
    }

    public List<Task> getDoingTasks() {
        return taskRepository.findAll().stream().filter(x -> x.getTaskStatus().toString().equals("Doing")).collect(Collectors.toList());
    }

    public List<Task> getDoneTasks() {
        return taskRepository.findAll().stream().filter(x -> x.getTaskStatus().toString().equals("Done")).collect(Collectors.toList());
    }

    public String deleteTaskById(int id) {
        taskRepository.deleteById(id);
        return "Task removed";
    }

    @Modifying
    public Task updateTaskStatus(Task task) {
        Task existingTask = taskRepository.findById(task.getId());
        existingTask.setTaskStatus(task.getTaskStatus());
        existingTask.setContent(task.getContent());
        existingTask.setDueDateOfTask(task.getDueDateOfTask());
        return taskRepository.save(existingTask);
    }
}