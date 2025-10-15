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
        try {
            ioService.printLine(getTest(questions));
        } catch (QuestionReadException e) {
            ioService.printLine(String.format("Ошибка формата в вопросе: %s", e.getMessage()));
        }
    }

    @Override
    public String getTest(List<Question> questions) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Question q : questions) {
            try {
                stringBuffer.append(String.format("%s%n", q.text()));
                String answer = q.answers()
                        .stream()
                        .map(s -> s.text())
                        .collect(Collectors.joining(", "));
                stringBuffer.append(String.format("%s%n", answer));
            } catch (Exception e) {
                throw new QuestionReadException(stringBuffer.toString());
            }
        }
        return stringBuffer.toString();
    }
}