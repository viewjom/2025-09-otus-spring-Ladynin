package ru.otus.hw.service;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.otus.hw.converter.TestConverter;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static ru.otus.hw.helper.QuestionBlanks.*;

@SpringBootTest(classes = {TestServiceImpl.class})
public class TestServiceTest {

    private final static String NULL = "";

    private final static String PRINT_QUESTION_1 = "Is there life on Mars?" + System.lineSeparator() +
            "Science doesn't know this yet," +
            " Certainly. The red UFO is from Mars. And green is from Venus," +
            " Absolutely not" + System.lineSeparator();

    private final static String PRINT_QUESTION_2 = "How should resources be loaded form" +
            " jar in Java?" + System.lineSeparator() +
            "ClassLoader#geResourceAsStream or ClassPathResource#getInputStream," +
            " ClassLoader#geResource#getFile + FileReader, Wingardium Leviosa" + System.lineSeparator();

    private final static String PRINT_QUESTION_3 = "Which option is a good way to" +
            " handle the exception?" + System.lineSeparator() +
            "@SneakyThrow, e.printStackTrace()," +
            " Rethrow with wrapping in business exception (for example, QuestionReadException)," +
            " Ignoring exception" + System.lineSeparator();

    private final static String PRINT_QUESTION_4 = "What is the physical quantity" +
            " SOUND-INTENSITY measured in?" + System.lineSeparator() +
            "Ohm, Decibels, Watts" + System.lineSeparator();

    private final static String PRINT_QUESTION_5 = "What is the physical quantity \"pressure\"" +
            " measured in?" + System.lineSeparator() +
            "Newton meters, Pascals, Joules" + System.lineSeparator();

    private final static String PRINT_QUESTION_6 = "What is kinematics?" + System.lineSeparator() +
            "Study of the movement of objects," +
            " Study of interactions between objects," +
            " Study of the properties of solid objects" + System.lineSeparator();

    private final static List<String> EXPECTED_PRINT_QUESTIONS = Arrays.asList(
            NULL,
            NULL,
            PRINT_QUESTION_1,
            PRINT_QUESTION_2,
            PRINT_QUESTION_3,
            PRINT_QUESTION_4,
            PRINT_QUESTION_5,
            PRINT_QUESTION_6);

    private static final String PROMPT = "TestService.answer.number";

    private static final String ERROR_MESSAGE = "TestService.answer.error";
    @Autowired
    private TestService testService;
    @MockitoBean
    private QuestionDao mockBeanCsvQuestionDao;
    @MockitoBean
    private LocalizedIOService mockBeanIOService;
    @MockitoBean
    private TestConverter mockBeanTestConverter;
    @Mock
    private Student mockStudent;

    @Test
    void executeTest() {
        given(mockBeanCsvQuestionDao.findAll()).willReturn(EXPECTED_QUESTIONS);
        var captor = ArgumentCaptor.forClass(Question.class);

        given(mockBeanTestConverter.questionToString(QUESTION_1)).willReturn(PRINT_QUESTION_1);
        given(mockBeanTestConverter.questionToString(QUESTION_2)).willReturn(PRINT_QUESTION_2);
        given(mockBeanTestConverter.questionToString(QUESTION_3)).willReturn(PRINT_QUESTION_3);
        given(mockBeanTestConverter.questionToString(QUESTION_4)).willReturn(PRINT_QUESTION_4);
        given(mockBeanTestConverter.questionToString(QUESTION_5)).willReturn(PRINT_QUESTION_5);
        given(mockBeanTestConverter.questionToString(QUESTION_6)).willReturn(PRINT_QUESTION_6);
        var questionCaptor = ArgumentCaptor.forClass(String.class);

        given(mockBeanIOService.readIntForRangeWithPromptLocalized(1, 4, PROMPT, ERROR_MESSAGE)).willReturn(1);
        given(mockBeanIOService.readIntForRangeWithPromptLocalized(1, 3, PROMPT, ERROR_MESSAGE)).willReturn(1);
        given(mockBeanIOService.readIntForRangeWithPromptLocalized(1, 2, PROMPT, ERROR_MESSAGE)).willReturn(1);

        testService.executeTestFor(mockStudent);

        verify(mockBeanIOService, times(2)).printLine("");
        verify(mockBeanIOService, times(1)).printLineLocalized("TestService.answer.the.questions");
        verify(mockBeanCsvQuestionDao, times(1)).findAll();
        verify(mockBeanTestConverter, times(6)).questionToString(captor.capture());

        verify(mockBeanIOService, times(8)).printLine(questionCaptor.capture());
        var actualPrintQuestions = questionCaptor.getAllValues();

        assertThat(actualPrintQuestions).isEqualTo(EXPECTED_PRINT_QUESTIONS);
    }
}