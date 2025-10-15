package ru.otus.hw.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final CsvQuestionDao csvQuestionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        List<Question> questions = csvQuestionDao.findAll();

        for (Question q : questions) {
            try {
                ioService.printLine(getTest(q));
            } catch (QuestionReadException e) {
                ioService.printLine(String.format("%s%n", e.getMessage()));
            }
        }
    }

    @Override
    public String getTest(Question question) {
        String answer = null;
        String qst = null;
        try {
            qst = question.text();
        } catch (Exception e) {
            throw new QuestionReadException("Format error in questions");
        }
        try {
            answer = question.answers()
                    .stream()
                    .map(s -> s.text())
                    .collect(Collectors.joining(", "));

        } catch (Exception e) {
            throw new QuestionReadException("Format error in answers");
        }
        return String.format("%s%n%s%n", question.text(), answer);
    }
}