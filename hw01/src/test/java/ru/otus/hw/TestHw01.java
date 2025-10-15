package ru.otus.hw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.TestService;
import ru.otus.hw.service.TestServiceImpl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestHw01 {
    private TestService testService;
    private CsvQuestionDao mockCsvQuestionDao;
    private IOService mockIOService;
    @BeforeEach
    void setUp() {
        mockIOService = mock(IOService.class);
        mockCsvQuestionDao = mock(CsvQuestionDao.class);
        testService = new TestServiceImpl(mockIOService, mockCsvQuestionDao);
    }

    @Test
    void executeTest() {
        testService.executeTest();
        verify(mockCsvQuestionDao, times(1)).findAll();
    }
}
