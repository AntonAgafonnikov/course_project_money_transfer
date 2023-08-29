package ru.netology.course_project_money_transfer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExHandler {

//    @ExceptionHandler(UnauthorizedUser.class)
//    public ResponseEntity<String> uauHandler(UnauthorizedUser e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(InvalidCredentials.class)
//    public ResponseEntity<String> icHandler(InvalidCredentials e) {
//        System.out.println(e.getMessage());
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<String> manveHandler(MethodArgumentNotValidException e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
//    }
}
