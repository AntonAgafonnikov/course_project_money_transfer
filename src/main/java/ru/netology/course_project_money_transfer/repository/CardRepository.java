package ru.netology.course_project_money_transfer.repository;

import ru.netology.course_project_money_transfer.model.card.CardFrom;
import ru.netology.course_project_money_transfer.model.card.CardTo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CardRepository {
    private static final ConcurrentMap<String, CardFrom> cardFromStorage = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, CardTo> cardToStorage = new ConcurrentHashMap<>();

    public CardRepository() {
    }

    public static void saveCardFrom(CardFrom cardFrom) {
        cardFromStorage.put(cardFrom.getCardNumber(), cardFrom);
    }

    public static void saveCardTo(CardTo cardTo) {
        cardToStorage.put(cardTo.getCardNumber(), cardTo);
    }
}
