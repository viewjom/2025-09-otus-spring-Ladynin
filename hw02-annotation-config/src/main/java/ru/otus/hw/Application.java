package ru.otus.hw;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.hw.config.AppConfig;
import ru.otus.hw.service.TestRunnerService;

public class Application {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        TestRunnerService testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();

    }
}