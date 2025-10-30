package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AppRunner implements CommandLineRunner {
    private final TestRunnerService testRunnerService;

    @Override
    public void run(String... args) throws Exception {
        testRunnerService.run();
    }
}
