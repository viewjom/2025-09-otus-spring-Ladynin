package ru.otus.hw;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.TestService;
import ru.otus.hw.service.TestServiceImpl;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TestTestService {
    private TestService testService;
    private CsvQuestionDao mockCsvQuestionDao;
    private IOService mockIOService;
    private List<Question> expectedQuestions;

    @BeforeEach
    void setUp() {
        mockIOService = mock(IOService.class);
        mockCsvQuestionDao = mock(CsvQuestionDao.class);
        testService = new TestServiceImpl(mockIOService, mockCsvQuestionDao);

        expectedQuestions =
                Arrays.asList(
                        new Question("Is there life on Mars?;Science doesn't know this yet%true|Certainly. The red UFO is from Mars. And green is from Venus%false|Absolutely not%false",
                                null),
                        new Question("How should resources be loaded form jar in Java?;ClassLoader#geResourceAsStream or ClassPathResource#getInputStream%true|ClassLoader#geResource#getFile + FileReader%false|Wingardium Leviosa%false",
                                null),
                        new Question("Which option is a good way to handle the exception?;@SneakyThrow%false|e.printStackTrace()%false|Rethrow with wrapping in business exception (for example",
                                Arrays.asList(new Answer("Ohm", false),
                                new Answer(" QuestionReadException)", true),
                                new Answer("Ignoring exception", false))),
                        new Question("What is the physical quantity \"sound intensity\" measured in?",
                                Arrays.asList(new Answer("Ohm", false),
                                        new Answer("Decibels", true),
                                        new Answer("Watts", false))),
                        new Question("What is the physical quantity \"pressure\" measured in?",
                                Arrays.asList(new Answer("Newton meters", false),
                                        new Answer("Pascals", true),
                                        new Answer("Joules", false))),
                        new Question("What is kinematics?",
                                Arrays.asList(new Answer("Study of the movement of objects", true),
                                        new Answer("Study of interactions between objects", false),
                                        new Answer("Study of the properties of solid objects", false)))
                );
    }

    @Test
    void executeTest() {
        given(mockCsvQuestionDao.findAll()).willReturn(expectedQuestions);
        var captor = ArgumentCaptor.forClass(Question.class);

        testService.executeTest();

        verify(mockIOService, times(1)).printLine("");
        verify(mockIOService, times(1)).printFormattedLine("Please answer the questions below%n");
        verify(mockCsvQuestionDao, times(1)).findAll();
        verify(mockCsvQuestionDao, times(6)).questionToString(captor.capture());

        var actualQuestions = captor.getAllValues();
        assertThat(actualQuestions).containsExactlyInAnyOrderElementsOf(expectedQuestions);
    }
}