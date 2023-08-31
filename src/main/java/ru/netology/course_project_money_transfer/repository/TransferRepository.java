package ru.netology.course_project_money_transfer.repository;

import org.springframework.stereotype.Repository;
import ru.netology.course_project_money_transfer.model.operation.Operation;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TransferRepository {
    private static final ConcurrentMap<String, Operation> operationStorage = new ConcurrentHashMap<>();

    public TransferRepository() {
    }

    public static void saveOperation(Operation operation) {
        operationStorage.put(operation.getUuid(), operation);
    }

    public ConcurrentMap<String, Operation> getOperationStorage() {
        return operationStorage;
    }

}
