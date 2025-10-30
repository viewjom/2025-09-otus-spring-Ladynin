package ru.otus.hw.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.TestFileNameProvider;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static ru.otus.hw.helper.QuestionBlanks.EXPECTED_QUESTIONS;

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
        given(mockTestFileNameProvider.getTestFileName()).willReturn("questions.csv");

        var questions = csvQuestionDao.findAll();
        verify(mockTestFileNameProvider, times(1)).getTestFileName();

        Assertions.assertThatList(questions).isEqualTo(EXPECTED_QUESTIONS);
    }
}