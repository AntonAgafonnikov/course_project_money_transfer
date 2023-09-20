package ru.netology.course_project_money_transfer.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.GenericContainer;
import ru.netology.course_project_money_transfer.exception.ErrorInputDataException;
import ru.netology.course_project_money_transfer.exception.ErrorTransferException;
import ru.netology.course_project_money_transfer.model.card.CardFrom;
import ru.netology.course_project_money_transfer.model.card.CardTo;
import ru.netology.course_project_money_transfer.model.operation.Amount;
import ru.netology.course_project_money_transfer.model.operation.Operation;
import ru.netology.course_project_money_transfer.model.operation.Verification;
import ru.netology.course_project_money_transfer.repository.TransferRepository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TransferServiceTest {
    private final GenericContainer<?> courseApp = new GenericContainer<>("courseapp:latest").withExposedPorts(8080);

    @BeforeEach
    void setUp() {
        courseApp.start();
    }

    @ParameterizedTest
    @MethodSource("addTestParamForSearchAndConfirmOperation")
    public void searchAndConfirmOperationTest(Verification verification, String expected){
        TransferRepository transferRepository = Mockito.mock(TransferRepository.class);
        TransferService transferService = new TransferService(transferRepository);

        ConcurrentMap<String, Operation> mapOperation = new ConcurrentHashMap<>();
        mapOperation.put(verification.getOperationId(), createOperation());
        Mockito.when(transferRepository.getOperationStorage()).thenReturn(mapOperation);
        //Проверим результат без учёта даты и времени - первые 17 символов
        String executable = transferService.searchAndConfirmOperation(verification).substring(17);

        Assertions.assertEquals(expected, executable);
    }

    @ParameterizedTest
    @MethodSource("addTestParamForSearchAndConfirmOperationExceptionErrorInputDataException")
    public void searchAndConfirmOperationTestExceptionErrorInputDataException(Verification verification) {
        TransferRepository transferRepository = Mockito.mock(TransferRepository.class);
        TransferService transferService = new TransferService(transferRepository);

        assertThrows(ErrorInputDataException.class, () -> {
            transferService.searchAndConfirmOperation(verification);
        });

    }

    @ParameterizedTest
    @MethodSource("addTestParamForSearchAndConfirmOperationExceptionErrorTransferException")
    public void searchAndConfirmOperationTestExceptionErrorTransferException(Verification verification) {
        TransferRepository transferRepository = Mockito.mock(TransferRepository.class);
        TransferService transferService = new TransferService(transferRepository);

        ConcurrentMap<String, Operation> mapOperation = new ConcurrentHashMap<>();
        Operation operation = createOperation();
        mapOperation.put(operation.getUuid(), operation);
        Mockito.when(transferRepository.getOperationStorage()).thenReturn(mapOperation);

        Assertions.assertThrows(ErrorTransferException.class, () -> {
            transferService.searchAndConfirmOperation(verification);
        });
    }

    private static Stream<Arguments> addTestParamForSearchAndConfirmOperation() {
        return Stream.of(
                Arguments.of(new Verification("4a156e33-679b-4370-a629-615a582bfad7", "0000"),
                        "from card: '1000200030004000' to card: '5000600070008000'. Amount: 9000RUR. Commission 1%: 90RUR - [COMPLETED]")
                );
    }

    private static Stream<Arguments> addTestParamForSearchAndConfirmOperationExceptionErrorInputDataException() {
        return Stream.of(
                Arguments.of(new Verification("4a156e33-679b-4370-a629-615a582bfad7", "1111")),
                Arguments.of(new Verification("4a156e33-679b-4370-a629-615a582bfad7", "0001")),
                Arguments.of(new Verification("4a156e33-679b-4370-a629-615a582bfad7", "1000"))
        );
    }

    private static Stream<Arguments> addTestParamForSearchAndConfirmOperationExceptionErrorTransferException() {
        return Stream.of(
                Arguments.of(new Verification("59a0f967-8118-42d8-8986-20e085631f0d", "0000")),
                Arguments.of(new Verification("234vf-234fsdf-232423423dfs-32423fdss", "0000")),
                Arguments.of(new Verification("34344334", "0000"))
        );
    }

    private static Operation createOperation() {
        return new Operation(
                "4a156e33-679b-4370-a629-615a582bfad7",
                new CardFrom(
                        "1000200030004000",
                        "09/23",
                        "444"
                ),
                new CardTo("5000600070008000"),
                new Amount(
                        9000,
                        "RUR"
                ));
    }
}
