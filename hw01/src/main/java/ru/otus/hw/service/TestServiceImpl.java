package ru.otus.hw.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.converter.TestConverter;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Question;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final CsvQuestionDao csvQuestionDao;

    private final TestConverter testConverter;

    @Override
    public void executeTest() {
        String qts = null;
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        List<Question> questions = csvQuestionDao.findAll();

        for (Question q : questions) {
            qts = testConverter.questionToString(q);
            ioService.printLine(qts);
        }
    }
}