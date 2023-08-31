package ru.netology.course_project_money_transfer.exception;

public class ErrorInputDataException extends RuntimeException {
    public ErrorInputDataException(String msg) {
        super(msg);
    }
}
