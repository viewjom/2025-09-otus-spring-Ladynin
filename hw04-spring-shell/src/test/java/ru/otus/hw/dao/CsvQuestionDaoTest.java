package ru.otus.hw.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.otus.hw.config.TestFileNameProvider;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static ru.otus.hw.helper.QuestionBlanks.EXPECTED_QUESTIONS;

@SpringBootTest(classes = CsvQuestionDao.class)
public class CsvQuestionDaoTest {
    @MockitoBean
    private TestFileNameProvider mockTestFileNameProvider;
    @Autowired
    private QuestionDao csvQuestionDao;

    @DisplayName("Интеграционный тест csvQuestionDao")
    @Test
    void shouldThrowUnsupportedQuestionFormatException() {
        given(mockTestFileNameProvider.getTestFileName()).willReturn("questions.csv");

        var questions = csvQuestionDao.findAll();
        verify(mockTestFileNameProvider, times(1)).getTestFileName();

        Assertions.assertThatList(questions).isEqualTo(EXPECTED_QUESTIONS);
    }
}