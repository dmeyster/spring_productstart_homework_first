package com.example.spring.course.homework.service;

import com.example.spring.course.homework.model.Distance;
import com.example.spring.course.homework.model.Gender;
import com.example.spring.course.homework.model.Person;
import com.example.spring.course.homework.model.Result;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class ResultParser {

    private static final String SEPARATOR = ",";

    /**
     * Распарсить строку из файла в {@link Result}.
     * </p>
     * Результаты хранятся в формате: Иван Иванов, М, 10 км, 55:20
     */
    public Result parseResult(String line) {
        var resultParts = line.split(SEPARATOR);

        String name = resultParts[0];
        Gender gender = Gender.valueOf(resultParts[1]);
        Distance distance = Distance.valueOf(resultParts[2]);
        Duration time = parseTime(resultParts[3]);

        var person = new Person(name, gender);
        return new Result(person, distance, time);
    }

    /**
     * Распарсить строку MM:SS в {@link Duration}.
     * </p>
     * Предполагаем, что все спортсмены уложились в один час в целях упрощения парсинга.
     */
    private Duration parseTime(String time) {
        var timeParts = time.split(":");

        // Минуты умножнаем на 60 и добавляем секунды
        var totalSecond = Integer.valueOf(timeParts[0]) * 60 + Integer.valueOf(timeParts[1]);
        return Duration.ofSeconds(totalSecond);
    }
}