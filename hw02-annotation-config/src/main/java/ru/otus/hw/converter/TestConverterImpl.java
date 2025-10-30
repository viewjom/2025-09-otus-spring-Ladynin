package ru.otus.hw.converter;

import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import ru.otus.hw.domain.Question;

@Component
public class TestConverterImpl implements TestConverter {
    @Override
    public String questionToString(Question question) {
        String answer = question.answers()
                .stream()
                .map(s -> s.text())
                .collect(Collectors.joining(", "));
        return String.format("%s%n%s%n", question.text(), answer);
    }
}
