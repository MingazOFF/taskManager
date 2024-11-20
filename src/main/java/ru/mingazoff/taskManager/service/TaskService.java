package ru.mingazoff.taskManager.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mingazoff.taskManager.repository.TaskRepository;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
}
