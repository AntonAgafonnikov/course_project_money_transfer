package ru.netology.course_project_money_transfer.model.card;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CardFrom {
    @NotEmpty(message = "A card can't be without number!")
    @Size(min = 16, max = 16, message = "A card size should be 16 characters!")
    private String cardNumber;

    @NotEmpty(message = "A card can't be without valid till!")
    @Size(min = 5, max = 5, message = "A card size should be 5 characters!")
    private String validTill;

    @NotEmpty(message = "A card can't be without CVV!")
    @Size(min = 3, max = 3, message = "A CVV size should be 3 characters!")
    private String cvv;

    public CardFrom() {
    }

    public CardFrom(String cardNumber, String validTill, String cvv) {
        this.cardNumber = cardNumber;
        this.validTill = validTill;
        this.cvv = cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getValidTill() {
        return validTill;
    }

    public void setValidTill(String validTill) {
        this.validTill = validTill;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
