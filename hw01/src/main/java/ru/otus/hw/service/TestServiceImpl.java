package ru.otus.hw.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Question;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final CsvQuestionDao csvQuestionDao;

    @Override
    public void executeTest() {
        String qts = null;
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        List<Question> questions = csvQuestionDao.findAll();

        for (Question q : questions) {
            qts = questionToString(q);
            ioService.printLine(qts);
        }
    }

    @Override
    public String questionToString(Question question) {
        String answer = question.answers()
                .stream()
                .map(s -> s.text())
                .collect(Collectors.joining(", "));

        return String.format("%s%n%s%n", question.text(), answer);
    }
}