package ru.netology.course_project_money_transfer.model.card;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CardTo {
//    @NotEmpty(message = "A card can't be without number!")
//    @Size(min = 16, max = 16, message = "A card size should be 16 characters!")
    private String cardNumber;

    public CardTo() {
    }

    public CardTo(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
