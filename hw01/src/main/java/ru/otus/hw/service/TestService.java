package ru.otus.hw.service;

import ru.otus.hw.domain.Question;

public interface TestService {

    void executeTest();

    String questionToString(Question question);
}