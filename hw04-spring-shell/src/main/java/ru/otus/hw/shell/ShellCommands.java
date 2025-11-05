package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.service.LocalizedIOService;
import ru.otus.hw.service.ResultService;
import ru.otus.hw.service.StudentService;
import ru.otus.hw.service.TestService;

@ShellComponent(value = "Shell commands")
@RequiredArgsConstructor
public class ShellCommands {

    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    private final LocalizedIOService ioService;

    private Student student;

    @ShellMethod(value = "Login", key = {"l", "login"})
    public void login() {
        student = studentService.determineCurrentStudent();
        ioService.printFormattedLineLocalized("ResultService.student",
                student.getFullName());
    }

    @ShellMethod(value = "Start test command", key = {"t", "test"})
    @ShellMethodAvailability(value = "isStudentLogin")
    public void test() {
        TestResult testResult;
        testResult = testService.executeTestFor(student);
        resultService.showResult(testResult);
    }

    private Availability isStudentLogin() {
        return student == null
                ? Availability.unavailable(ioService.getMessage("ShellCommands.warning.log"))
                : Availability.available();
    }
}