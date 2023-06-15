package com.example.spring.course.homework.service;

import com.example.spring.course.homework.model.Distance;
import com.example.spring.course.homework.model.Gender;
import com.example.spring.course.homework.model.Result;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ResultsProcessor {

    private final Collection<Result> results;

    public ResultsProcessor(Collection<Result> results) {
        this.results = results;
    }

    /**
     * Позволяет определить N самых быстрых мужчин или женщин на дистанции 5 или 10 км.
     * </p>
     * Пример запроса - найти 3 самых быстрых женщин на дистанции 10 км.
     */
    public List<Result> getFastest(Gender gender, Distance distance, int limit) {
        return results.stream()
                .filter(result -> result.distance().equals(distance) && result.person().gender().equals(gender))
                .sorted(Comparator.comparing(Result::time))
                .limit(limit)
                .collect(Collectors.toList());
    }
}

