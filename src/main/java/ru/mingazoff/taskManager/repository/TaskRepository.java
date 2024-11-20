package ru.mingazoff.taskManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mingazoff.taskManager.entity.Task;

/**
 * The interface Task repository.
 */
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
