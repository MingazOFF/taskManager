package ru.mingazoff.taskManager.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mingazoff.taskManager.aspect.LogAfterReturning;
import ru.mingazoff.taskManager.aspect.LogAfterThrowing;
import ru.mingazoff.taskManager.aspect.LogAround;
import ru.mingazoff.taskManager.aspect.LogBefore;
import ru.mingazoff.taskManager.entity.Task;
import ru.mingazoff.taskManager.exception.TaskNotFoundException;
import ru.mingazoff.taskManager.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    @LogAround
    public int createTask(Task task) {
        Task savedTask = taskRepository.save(task);
        return savedTask.getId();
    }

    @LogBefore
    @LogAfterReturning
    @LogAfterThrowing
    public Task getTaskById(int id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.orElseThrow(
                () -> new TaskNotFoundException("Task(id=" + id + ") not found"));

    }

    @LogBefore
    @LogAfterReturning
    @LogAfterThrowing
    public Task updateTask(int id, Task toUpdateTask) {
        Task taskFromDB = taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException("Task(id=" + id + ") not found"));
        taskFromDB.setTitle(toUpdateTask.getTitle());
        taskFromDB.setDescription(toUpdateTask.getDescription());
        taskFromDB.setUserId(toUpdateTask.getUserId());
        return taskRepository.save(taskFromDB);
    }

    @LogBefore
    @LogAfterReturning
    @LogAfterThrowing
    public void deleteTaskById(int id) {
        boolean taskIsExists = taskRepository.existsById(id);
        if (taskIsExists) {
            taskRepository.deleteById(id);
        } else {
            throw new TaskNotFoundException("Task(id=" + id + ") not found");
        }
    }

    @LogAround
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
