package ru.otus.hw.service;

import java.util.List;
import ru.otus.hw.domain.Question;

public interface TestService {

    void executeTest();

    String getTest(List<Question> questions);
}