package ru.otus.hw;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.otus.hw.converter.TestConverter;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.service.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TestTestService {
    private final static String QUESTION_1 = "Is there life on Mars?\r\nScience doesn't know this yet, Certainly. The red UFO is from Mars. And green is from Venus, Absolutely not\r\n";

    private final static String QUESTION_2 = "How should resources be loaded form jar in Java?\r\nClassLoader#geResourceAsStream or ClassPathResource#getInputStream, ClassLoader#geResource#getFile + FileReader, Wingardium Leviosa\r\n";

    private final static String QUESTION_3 = "Which option is a good way to handle the exception?\r\n@SneakyThrow, e.printStackTrace(), Rethrow with wrapping in business exception (for example, QuestionReadException), Ignoring exception\r\n";

    private final static String QUESTION_4 = "What is the physical quantity SOUND-INTENSITY measured in?\r\nOhm, Decibels, Watts\r\n";

    private final static String QUESTION_5 = "What is the physical quantity \"pressure\" measured in?\r\nNewton meters, Pascals, Joules\r\n";

    private final static String QUESTION_6 = "What is kinematics?\r\nStudy of the movement of objects, Study of interactions between objects, Study of the properties of solid objects\r\n";

    private TestRunnerService testRunnerService;

    private TestService testService;

    private CsvQuestionDao mockCsvQuestionDao;

    private IOService mockIOService;

    private TestConverter mockTestConverter;

    @BeforeEach
    void setUp() {
        mockIOService = mock(IOService.class);
        mockCsvQuestionDao = mock(CsvQuestionDao.class);
        mockTestConverter = mock(TestConverter.class);
        testService = new TestServiceImpl(mockIOService, mockCsvQuestionDao, mockTestConverter);
    }

    @Test
    void executeTest() {
        Question question1 = new Question("Is there life on Mars?, answers=[Answer[text=Science doesn't know this yet, isCorrect=true], Answer[text=Certainly. The red UFO is from Mars. And green is from Venus, isCorrect=false], Answer[text=Absolutely not, isCorrect=false]]]",
                Arrays.asList(new Answer("Science doesn't know this yet", true),
                        new Answer("Certainly. The red UFO is from Mars. And green is from Venus, isCorrect", false),
                        new Answer("Absolutely not", false)));
        Question question2 = new Question("How should resources be loaded form jar in Java?",
                Arrays.asList(new Answer("ClassLoader#geResourceAsStream or ClassPathResource#getInputStream", true),
                        new Answer("ClassLoader#geResource#getFile + FileReader", false),
                        new Answer("Wingardium Leviosa", false)));
        Question question3 = new Question("Which option is a good way to handle the exception?",
                Arrays.asList(new Answer("@SneakyThrow", false),
                        new Answer(" e.printStackTrace()", false),
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

        given(mockCsvQuestionDao.findAll()).willReturn(expectedQuestions);
        var captor = ArgumentCaptor.forClass(Question.class);

        given(mockTestConverter.questionToString(question1)).willReturn(QUESTION_1);
        given(mockTestConverter.questionToString(question2)).willReturn(QUESTION_2);
        given(mockTestConverter.questionToString(question3)).willReturn(QUESTION_3);
        given(mockTestConverter.questionToString(question4)).willReturn(QUESTION_4);
        given(mockTestConverter.questionToString(question5)).willReturn(QUESTION_5);
        given(mockTestConverter.questionToString(question6)).willReturn(QUESTION_6);
        var questionCaptor = ArgumentCaptor.forClass(String.class);

        testService.executeTest();

        verify(mockIOService, times(1)).printFormattedLine("Please answer the questions below%n");
        verify(mockCsvQuestionDao, times(1)).findAll();
        verify(mockTestConverter, times(6)).questionToString(captor.capture());

        verify(mockIOService, times(7)).printLine(questionCaptor.capture());
        var actualQuestion = questionCaptor.getAllValues();

        assertThat(QUESTION_1).isEqualTo(actualQuestion.get(1));
        assertThat(QUESTION_2).isEqualTo(actualQuestion.get(2));
        assertThat(QUESTION_3).isEqualTo(actualQuestion.get(3));
        assertThat(QUESTION_4).isEqualTo(actualQuestion.get(4));
        assertThat(QUESTION_5).isEqualTo(actualQuestion.get(5));
        assertThat(QUESTION_6).isEqualTo(actualQuestion.get(6));
    }
}