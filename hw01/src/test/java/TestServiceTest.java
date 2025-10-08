import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

public class TestServiceTest {
    private CsvQuestionDao mockCsvQuestionDao;
    @BeforeEach
    void setUp() {
        mockCsvQuestionDao = mock(CsvQuestionDao.class);
    }

    @Test
    void executeTest() {
        List<Answer> answer = Arrays.asList(
                new Answer("Ohm", false),
                new Answer("Decibels", true),
                new Answer("Watts", false));

        List<Question> questionsExpected =
                Arrays.asList(
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

        given(mockCsvQuestionDao.findAll()).willReturn(questionsExpected);

        List<Question> questions = mockCsvQuestionDao.findAll();

        verify(mockCsvQuestionDao, times(1)).findAll();

        assertThat(questionsExpected).isEqualTo(questions);
    }
}
