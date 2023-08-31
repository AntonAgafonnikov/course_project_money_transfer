package ru.netology.course_project_money_transfer.service;

import org.springframework.stereotype.Service;
import ru.netology.course_project_money_transfer.exception.ErrorConfirmationException;
import ru.netology.course_project_money_transfer.exception.ErrorInputDataException;
import ru.netology.course_project_money_transfer.exception.ErrorTransferException;
import ru.netology.course_project_money_transfer.logger.WriterLogsInFile;
import ru.netology.course_project_money_transfer.model.card.CardFrom;
import ru.netology.course_project_money_transfer.model.card.CardTo;
import ru.netology.course_project_money_transfer.model.operation.OperationForm;
import ru.netology.course_project_money_transfer.model.operation.Operation;
import ru.netology.course_project_money_transfer.model.operation.Verification;
import ru.netology.course_project_money_transfer.repository.CardRepository;
import ru.netology.course_project_money_transfer.repository.TransferRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentMap;

@Service
public class TransferService {
    TransferRepository transferRepository;

    public TransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public void operationProcessing(String uuid, OperationForm operationForm) {
        String date = operationForm.getCardFromValidTill();
        try {
            if (dateTillValidityCheck(date)) {

                Runnable task = () -> {
                    CardFrom cardFrom = new CardFrom(operationForm.getCardFromNumber(),
                            operationForm.getCardFromValidTill(), operationForm.getCardFromCVV());
                    saveCardFrom(cardFrom);

                    CardTo cardTo = new CardTo(operationForm.getCardToNumber());
                    saveCardTo(cardTo);

                    Operation operation = new Operation(uuid, cardFrom, cardTo, operationForm.getAmount());
                    saveOperation(operation);

                    int codeWaitingTimer = 600;
                    while (!operation.isSuccessful() && codeWaitingTimer > 0) {
                        try {
                            Thread.sleep(100);
                            codeWaitingTimer--;
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (codeWaitingTimer <= 0) {
                        throw new ErrorConfirmationException("Code confirmation timeout");
                    }
                };
                Thread thread = new Thread(task);
                thread.start();
            }
        } catch (ParseException e) {
            throw new ErrorInputDataException("Invalid date format");
        }
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
        if(!verification.getCode().equals("0000")) {
            throw new ErrorInputDataException("Incorrect code. Default - 0000");
        }

        String uuid = verification.getOperationId();
        ConcurrentMap<String, Operation> operationStorage = transferRepository.getOperationStorage();
        if (!operationStorage.containsKey(uuid)) {
            throw new ErrorTransferException("UUID not found");
        }

        operationStorage.get(uuid).setSuccessful(true);
        String msg = operationStorage.get(uuid).toString();
        WriterLogsInFile.writeLogsInFile(msg);
        return msg;
    }

    private boolean dateTillValidityCheck(String dateCard) throws ParseException {
        String pattern = "MM/yy";

        Date dateCheckAd = new SimpleDateFormat(pattern).parse(dateCard);

        SimpleDateFormat simpleDateNowFormat = new SimpleDateFormat(pattern);
        String dateNow = simpleDateNowFormat.format(new Date());
        Date dateNowDate = simpleDateNowFormat.parse(dateNow);

        if (dateCheckAd.compareTo(dateNowDate) < 0) {
            throw new ErrorInputDataException("The card is invalid. The validity period has ended");
        }

        return true;
    }
}
