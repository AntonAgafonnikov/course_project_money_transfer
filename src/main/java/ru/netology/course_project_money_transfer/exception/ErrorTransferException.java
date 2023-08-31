package ru.netology.course_project_money_transfer.exception;

public class ErrorTransferException extends RuntimeException {
    public ErrorTransferException(String msg) {
        super(msg);
    }
}
