package ru.netology.course_project_money_transfer.service;

import org.springframework.stereotype.Service;
import ru.netology.course_project_money_transfer.logger.WriterLogsInFile;
import ru.netology.course_project_money_transfer.model.card.CardFrom;
import ru.netology.course_project_money_transfer.model.card.CardTo;
import ru.netology.course_project_money_transfer.model.operation.OperationForm;
import ru.netology.course_project_money_transfer.model.operation.Operation;
import ru.netology.course_project_money_transfer.model.operation.Verification;
import ru.netology.course_project_money_transfer.repository.CardRepository;
import ru.netology.course_project_money_transfer.repository.TransferRepository;

import java.util.UUID;

@Service
public class TransferService {
    TransferRepository transferRepository;

    public TransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public void operationProcessing(String uuid, OperationForm operationForm) {
        Runnable task = () -> {
            CardFrom cardFrom = new CardFrom(operationForm.getCardFromNumber(), operationForm.getCardFromValidTill(),
                    operationForm.getCardFromCVV());
            saveCardFrom(cardFrom);

            CardTo cardTo = new CardTo(operationForm.getCardToNumber());
            saveCardTo(cardTo);

            Operation operation = new Operation(uuid, cardFrom, cardTo, operationForm.getAmount());
            saveOperation(operation);

            int codeWaitingTimer = 60;
            while (!operation.isSuccessful() && codeWaitingTimer > 0) {
                try {
                    Thread.sleep(1000);
                    codeWaitingTimer--;
                    System.out.println("THE WORLDO!!!!!!!!!!!!!!!!!!!!");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (codeWaitingTimer <= 0) {
                throw new RuntimeException();
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    private void saveCardFrom(CardFrom cardFrom) {
        CardRepository.saveCardFrom(cardFrom);
    }

    private void saveCardTo(CardTo cardTo) {
        CardRepository.saveCardTo(cardTo);
    }

    private void saveOperation(Operation operation) {
        TransferRepository.saveOperation(operation);
    }

    public String searchAndConfirmOperation(Verification verification) {
        String uuid = verification.getOperationId();
        if (transferRepository.getOperationStorage().containsKey(uuid)) {
            transferRepository.getOperationStorage().get(uuid).setSuccessful(true);
        }
        String msg = transferRepository.getOperationStorage().get(uuid).toString();
        WriterLogsInFile.writeLogsInFile(msg);
        return msg;
    }
}
