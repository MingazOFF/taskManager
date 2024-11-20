package ru.mingazoff.taskManager.exception;

/**
 * The type Task not found exception.
 */
public class TaskNotFoundException extends RuntimeException {
    /**
     * Instantiates a new Task not found exception.
     *
     * @param message the message
     */
    public TaskNotFoundException(String message) {
        super(message);
    }
}
