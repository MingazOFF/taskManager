package ru.mingazoff.taskManager.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mingazoff.taskManager.aspect.LogBefore;
import ru.mingazoff.taskManager.entity.Task;
import ru.mingazoff.taskManager.exception.TaskNotFoundException;
import ru.mingazoff.taskManager.service.TaskService;

import java.util.List;

/**
 * The type Task controller.
 */
@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    /**
     * Create task.
     *
     * @param task the task for save
     * @return the saved Task id
     */
    @LogBefore
    @PostMapping()
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        int id = taskService.createTask(task);
        return ResponseEntity.ok(id);
    }

    /**
     * Gets task by id.
     *
     * @param id the Task id
     * @return the task by id from DB
     */
    @LogBefore
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable int id) {
        try {
            Task task = taskService.getTaskById(id);
            return ResponseEntity.ok(task);
        } catch (TaskNotFoundException e) {
            return ResponseEntity.status(404).build();
        }

    }

    /**
     * Update task.
     *
     * @param id   the to update Task id
     * @param task the task for update
     * @return the updated Task from DB
     */
    @LogBefore
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody Task task) {
        try {
            Task updatedTask = taskService.updateTask(id, task);
            return ResponseEntity.ok(updatedTask);
        }catch (TaskNotFoundException e) {
            return ResponseEntity.status(404).build();

        }
    }

    /**
     * Delete task by id.
     *
     * @param id the Task id
     * @return delete status
     */
    @LogBefore
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable int id) {
        try {
            taskService.deleteTaskById(id);
            return ResponseEntity.ok().build();
        } catch (TaskNotFoundException e) {
            return ResponseEntity.status(404).build();
        }
    }

    /**
     * Get all tasks.
     *
     * @return all Tasks from DB
     */
    @LogBefore
    @GetMapping()
    public ResponseEntity<?> getAllTasks(){
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }
}
