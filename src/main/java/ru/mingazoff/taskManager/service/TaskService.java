package ru.mingazoff.taskManager.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mingazoff.taskManager.entity.Task;
import ru.mingazoff.taskManager.exception.TaskNotFoundException;
import ru.mingazoff.taskManager.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public int createTask(Task task) {
        Task savedTask = taskRepository.save(task);
        return savedTask.getId();
    }

    public Task getTaskById(int id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.orElseThrow(
                () -> new TaskNotFoundException("Task(id=" + id + ") not found"));

    }

    public Task updateTask(int id, Task toUpdateTask) {
        Task taskFromDB = taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException("Task(id=" + id + ") not found"));
        taskFromDB.setTitle(toUpdateTask.getTitle());
        taskFromDB.setDescription(toUpdateTask.getDescription());
        taskFromDB.setUserId(toUpdateTask.getUserId());
        return taskRepository.save(taskFromDB);
    }

    public void deleteTaskById(int id) {
        boolean taskIsExists = taskRepository.existsById(id);
        if (taskIsExists) {
            taskRepository.deleteById(id);
        } else {
            throw new TaskNotFoundException("Task(id=" + id + ") not found");
        }
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
