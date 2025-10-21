package ru.otus.hw.dao;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;

import java.util.List;
import ru.otus.hw.exceptions.QuestionReadException;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        try {
            List<QuestionDto> questionDtos;
            InputStream is = Question.class.getClassLoader()
                    .getResourceAsStream(fileNameProvider.getTestFileName());
            CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(is))
                    .withSkipLines(1)
                    .build();

            return new CsvToBeanBuilder<QuestionDto>(csvReader)
                    .withType(QuestionDto.class)
                    .build()
                    .parse()
                    .stream()
                    .map(QuestionDto::toDomainObject)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new QuestionReadException("Error during reading questions", e);
        }
    }

    public String questionToString(Question question) {
        String answer = question.answers()
                .stream()
                .map(s -> s.text())
                .collect(Collectors.joining(", "));

        return String.format("%s%n%s%n", question.text(), answer);
    }
}