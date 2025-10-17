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

import java.util.ArrayList;
import java.util.List;
import ru.otus.hw.exceptions.QuestionReadException;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        List<QuestionDto> questionDtos;
        InputStream is = Question.class.getClassLoader()
                .getResourceAsStream(fileNameProvider.getTestFileName());
        CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(is))
                .withSkipLines(1)
                .build();
        questionDtos = new CsvToBeanBuilder<QuestionDto>(csvReader)
                .withType(QuestionDto.class)
                .build()
                .parse();
        return new ArrayList<>(questionDtos
                .stream()
                .map(s -> {
                    if (s.getAnswers() != null) {
                        return s.toDomainObject();
                    }
                    throw new QuestionReadException(s.getText());
                })
                .collect(Collectors.toList()));
    }
}