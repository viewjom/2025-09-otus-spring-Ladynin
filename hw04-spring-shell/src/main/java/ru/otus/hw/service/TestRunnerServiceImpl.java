package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.QuestionReadException;

@Service
@RequiredArgsConstructor
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    private final LocalizedIOService ioService;

    @ShellMethod(value = "Hello", key = {"r", "run"})
    public void run() {
        try {
            var student = studentService.determineCurrentStudent();
            var testResult = testService.executeTestFor(student);
            resultService.showResult(testResult);
        } catch (QuestionReadException e) {
            ioService.printLineLocalized("TestRunnerService.error.reading.questions");
        } catch (IllegalArgumentException e) {
            ioService.printLineLocalized("TestRunnerService.error.reading.int.value");
        } catch (Exception e) {
            ioService.printLineLocalized("TestRunnerService.error.unknown");
        }
    }
}