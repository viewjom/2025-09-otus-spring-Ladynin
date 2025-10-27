package ru.otus.hw.dao;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class CsvQuestionDaoTest {
    private TestFileNameProvider mockTestFileNameProvider;

    private QuestionDao csvQuestionDao;

    @BeforeEach
    void setUp() {
        mockTestFileNameProvider = mock(TestFileNameProvider.class);
        csvQuestionDao = new CsvQuestionDao(mockTestFileNameProvider);
    }

    @DisplayName("Интеграционный тест csvQuestionDao")
    @Test
    void shouldThrowUnsupportedQuestionFormatException() {
        Question question1 = new Question("Is there life on Mars?",
                Arrays.asList(new Answer("Science doesn't know this yet", true),
                        new Answer("Certainly. The red UFO is from Mars. And green is from Venus", false),
                        new Answer("Absolutely not", false)));
        Question question2 = new Question("How should resources be loaded form jar in Java?",
                Arrays.asList(new Answer("ClassLoader#geResourceAsStream or ClassPathResource#getInputStream", true),
                        new Answer("ClassLoader#geResource#getFile + FileReader", false),
                        new Answer("Wingardium Leviosa", false)));
        Question question3 = new Question("Which option is a good way to handle the exception?",
                Arrays.asList(new Answer("@SneakyThrow", false),
                        new Answer("e.printStackTrace()", false),
                        new Answer("Rethrow with wrapping in business exception (for example, QuestionReadException)", true),
                        new Answer("Ignoring exception", false)));
        Question question4 = new Question("What is the physical quantity \"sound intensity\" measured in?",
                Arrays.asList(new Answer("Ohm", false),
                        new Answer("Decibels", true),
                        new Answer("Watts", false)));
        Question question5 = new Question("What is the physical quantity \"pressure\" measured in?",
                Arrays.asList(new Answer("Newton meters", false),
                        new Answer("Pascals", true),
                        new Answer("Joules", false)));
        Question question6 = new Question("What is kinematics?",
                Arrays.asList(new Answer("Study of the movement of objects", true),
                        new Answer("Study of interactions between objects", false),
                        new Answer("Study of the properties of solid objects", false)));
        List<Question> expectedQuestions = Arrays.asList(question1, question2, question3, question4, question5, question6);

        given(mockTestFileNameProvider.getTestFileName()).willReturn("questions.csv");

        var questions = csvQuestionDao.findAll();
        verify(mockTestFileNameProvider, times(1)).getTestFileName();

        Assertions.assertThatList(questions).isEqualTo(expectedQuestions);
    }
}