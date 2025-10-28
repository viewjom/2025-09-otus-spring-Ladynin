package ru.otus.hw.helper;

import java.util.Arrays;
import java.util.List;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

public class QuestionBlanks {
    public static  final Question QUESTION_1 = new Question("Is there life on Mars?",
            Arrays.asList(new Answer("Science doesn't know this yet", true),
                    new Answer("Certainly. The red UFO is from Mars. And green is from Venus", false),
                    new Answer("Absolutely not", false)));
    public static  final Question QUESTION_2 = new Question("How should resources be loaded form jar in Java?",
            Arrays.asList(new Answer("ClassLoader#geResourceAsStream or ClassPathResource#getInputStream", true),
                    new Answer("ClassLoader#geResource#getFile + FileReader", false),
                    new Answer("Wingardium Leviosa", false)));
    public static  final Question QUESTION_3 = new Question("Which option is a good way to handle the exception?",
            Arrays.asList(new Answer("@SneakyThrow", false),
                    new Answer("e.printStackTrace()", false),
                    new Answer("Rethrow with wrapping in business exception (for example, QuestionReadException)", true),
                    new Answer("Ignoring exception", false)));
    public static  final Question QUESTION_4 = new Question("What is the physical quantity \"sound intensity\" measured in?",
            Arrays.asList(new Answer("Ohm", false),
                    new Answer("Decibels", true),
                    new Answer("Watts", false)));
    public static  final Question QUESTION_5 = new Question("What is the physical quantity \"pressure\" measured in?",
            Arrays.asList(new Answer("Newton meters", false),
                    new Answer("Pascals", true),
                    new Answer("Joules", false)));
    public static  final Question QUESTION_6 = new Question("What is kinematics?",
            Arrays.asList(new Answer("Study of the movement of objects", true),
                    new Answer("Study of interactions between objects", false),
                    new Answer("Study of the properties of solid objects", false)));
    public static  final List<Question> EXPECTED_QUESTIONS = Arrays.asList(QUESTION_1, QUESTION_2, QUESTION_3, QUESTION_4, QUESTION_5, QUESTION_6);
}