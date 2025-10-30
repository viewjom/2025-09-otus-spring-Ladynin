package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.converter.TestConverter;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {
    private static final String PROMPT = "Enter the number of the correct answer:";

    private static final String ERROR_MESSAGE = "There is no such answer";

    private final IOService ioService;

    private final QuestionDao questionDao;

    private final TestConverter testConverter;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            var isAnswerValid = ask(question);
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    private boolean ask(Question question) {
        String qts = testConverter.questionToString(question);
        ioService.printLine(qts);
        int result = ioService.readIntForRangeWithPrompt(1, question.answers().size(), PROMPT, ERROR_MESSAGE);
        return question
                .answers()
                .get(result - 1)
                .isCorrect();
    }
}