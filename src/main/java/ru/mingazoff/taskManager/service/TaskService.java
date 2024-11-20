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

/**
 * This is Task service class.
 */
@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    /**
     * Create task.
     *
     * @param task the task for save
     * @return the createdTask id from DB
     */
    @LogAround
    public int createTask(Task task) {
        Task savedTask = taskRepository.save(task);
        return savedTask.getId();
    }

    /**
     * Gets task by id from DB.
     *
     * @param id the id
     * @return the task by id
     */
    @LogBefore
    @LogAfterReturning
    @LogAfterThrowing
    public Task getTaskById(int id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.orElseThrow(
                () -> new TaskNotFoundException("Task(id=" + id + ") not found"));

    }

    /**
     * Update task.
     *
     * @param id the task id
     * @param toUpdateTask the to update task
     * @return the updatedTask
     */
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

    /**
     * Delete task by id.
     *
     * @param id the task id
     */
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

    /**
     * Gets all tasks.
     *
     * @return the all tasks from DB
     */
    @LogAround
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
