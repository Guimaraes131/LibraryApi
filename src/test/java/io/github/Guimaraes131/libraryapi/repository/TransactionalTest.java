package io.github.Guimaraes131.libraryapi.repository;

import io.github.Guimaraes131.libraryapi.service.TransactionalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransactionalTest {

    private TransactionalService transactionalService;

    @Autowired
    public TransactionalTest(TransactionalService transactionalService) {
        this.transactionalService = transactionalService;
    }

    @Test
    void simpleTransactionTest() {
        transactionalService.execute();
    }

    @Test
    void updateTransactionTest() {
        transactionalService.updateWithoutQuery();
    }
}
