package com.example.spring.course.homework;

import com.example.spring.course.homework.model.Distance;
import com.example.spring.course.homework.model.Gender;
import com.example.spring.course.homework.model.Result;
import com.example.spring.course.homework.service.ResultsProcessor;
import com.example.spring.course.homework.service.ResultsReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;


/**
 * На вход приложение получает csv-файл с результатами в формате: Фамилия Имя, Пол, Дистанция, Время (Пример – Иван Иванов, М, 10 км, 55:20)
 * Spring Application Context должен содержать bean с именем ResultsProcessor с методом для загрузки результатов
 * и для получения N самых быстрых бегунов, в зависимости от переданных аргументов – дистанция (5 или 10 км), пол (М или Ж)
 * Все public методы классов должны быть покрыты unit-тестами
 */
@SpringBootApplication
public class ResultsProcessorMain {

	@Autowired
	private ResultsReader resultsReader;

	public static void main(String[] args) {
		SpringApplication.run(ResultsProcessorMain.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void processResults() {
		try {
			var filePath = new ClassPathResource("results.csv").getFile().toPath();
			var results = resultsReader.readFromFile(filePath);

			var resultsProcessor = new ResultsProcessor(results);
			List<Result> fastestMen = resultsProcessor.getFastest(Gender.MALE, Distance.TEN_KM, 3);

			System.out.println(fastestMen);
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
	}
}