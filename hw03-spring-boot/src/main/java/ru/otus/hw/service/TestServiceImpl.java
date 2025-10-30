package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.converter.TestConverter;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    private final TestConverter testConverter;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");

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
        int result = ioService.readIntForRangeWithPromptLocalized(1, question.answers().size(),
                "TestService.answer.number",
                "TestService.answer.error");
        return question
                .answers()
                .get(result - 1)
                .isCorrect();
    }
}