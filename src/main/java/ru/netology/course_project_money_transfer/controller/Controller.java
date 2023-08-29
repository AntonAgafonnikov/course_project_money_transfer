package ru.netology.course_project_money_transfer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.course_project_money_transfer.model.operation.OperationForm;
import ru.netology.course_project_money_transfer.model.operation.Verification;
import ru.netology.course_project_money_transfer.service.TransferService;

import java.util.UUID;

@RestController
public class Controller {

    private TransferService transferService;

    public Controller(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public String getResponse(@RequestBody OperationForm operationForm) {
        String uuid = UUID.randomUUID().toString();
        transferService.operationProcessing(uuid, operationForm);
        return uuid;
    }

    @PostMapping("/confirmOperation")
    public String confirmOperation(@RequestBody Verification verification) {
        return transferService.searchAndConfirmOperation(verification);
    }
}
