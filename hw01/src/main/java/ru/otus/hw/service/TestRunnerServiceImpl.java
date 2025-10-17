package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.exceptions.QuestionReadException;

@RequiredArgsConstructor
public class TestRunnerServiceImpl implements TestRunnerService {

   private final TestService testService;

    @Override
    public void run() {
        try {
        testService.executeTest();
        } catch (QuestionReadException e) {
            //QuestionReadException - не перехватывается
            System.out.println("Format error in answers: " + e);
        }
    }
}