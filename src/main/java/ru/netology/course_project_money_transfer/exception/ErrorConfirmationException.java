package ru.netology.course_project_money_transfer.exception;

public class ErrorConfirmationException extends RuntimeException {
    public ErrorConfirmationException(String msg) {
        super(msg);
    }
}
