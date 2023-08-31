package ru.netology.course_project_money_transfer.model.operation;

import ru.netology.course_project_money_transfer.model.card.CardFrom;
import ru.netology.course_project_money_transfer.model.card.CardTo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Operation {
    private static final int COMMISSION = 1;
    private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final String COMPLETED = "COMPLETED";
    private static final String NOT_COMPLETED = "NOT COMPLETED";

    private Date date;
    private CardFrom cardFrom;
    private CardTo cardTo;
    private Amount amount;
    private long commissionAmount;
    private String uuid;
    private boolean isSuccessful;
    private String success;

    public Operation() {
    }

    public Operation(String uuid, CardFrom cardFrom, CardTo cardTo, Amount amount) {
        this.uuid = uuid;
        this.date = new Date();
        this.cardFrom = cardFrom;
        this.cardTo = cardTo;
        this.amount = amount;
        this.commissionAmount = amount.getValue() / 100 * COMMISSION;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CardFrom getCardFrom() {
        return cardFrom;
    }

    public void setCardFrom(CardFrom cardFrom) {
        this.cardFrom = cardFrom;
    }

    public CardTo getCardTo() {
        return cardTo;
    }

    public void setCardTo(CardTo cardTo) {
        this.cardTo = cardTo;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public long getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(long commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public String toString() {
        this.success = this.isSuccessful?COMPLETED:NOT_COMPLETED;
        return DF.format(date) +
                " from card: '" + this.cardFrom.getCardNumber() +
                "' to card: '" + this.cardTo.getCardNumber() +
                "'. Amount: " + this.amount.getValue() + this.amount.getCurrency() +
                ". Commission " + COMMISSION +
                "%: " + this.commissionAmount + this.amount.getCurrency() +
                " - [" + this.success + "]";
    }
}
