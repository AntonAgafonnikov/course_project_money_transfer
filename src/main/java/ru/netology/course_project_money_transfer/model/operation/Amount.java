package ru.netology.course_project_money_transfer.model.operation;

import jakarta.validation.constraints.Size;

public class Amount {
    @Size(min = 0, max = 2000000000, message = "Incorrect amount. Range 0 - 2 000 000 000.")
    private int value;
    private String currency;

    public Amount() {
    }

    public Amount(int value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }



    @Override
    public String toString() {
        return "Amount{" +
                "value=" + value +
                ", currency='" + currency + '\'' +
                '}';
    }
}
