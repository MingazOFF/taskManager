package ru.mingazoff.taskManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mingazoff.taskManager.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
