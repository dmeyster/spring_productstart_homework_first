package com.example.spring.course.homework;

import com.example.spring.course.homework.model.Distance;
import com.example.spring.course.homework.model.Gender;
import com.example.spring.course.homework.model.Person;
import com.example.spring.course.homework.model.Result;
import com.example.spring.course.homework.service.ResultParser;
import com.example.spring.course.homework.service.ResultsReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SpringCourseHomeworkApplicationTests {
	@Autowired
	private ResultsProcessorMain resultsProcessorMain;
	@Autowired
	private ResultsReader resultsReader;
	@Test
	void processResults_shouldPrintFastestMen() {
		var filePath = new ClassPathResource("results.csv").getPath();
		var expectedOutput = "[Result{person=Person{name='John Smith', gender=MALE}, distance=TEN_KM, time=PT38M32S}, Result{person=Person{name='Adam Johnson', gender=MALE}, distance=TEN_KM, time=PT39M21S}, Result{person=Person{name='Evan Lee', gender=MALE}, distance=TEN_KM, time=PT40M12S}]";
		var output = executeAndCaptureOutput(() -> resultsProcessorMain.processResults());
		assertThat(output).isEqualTo(expectedOutput);
	}
	@Test
	void readFromFile_shouldParseResults() throws IOException {
		var filePath = new ClassPathResource("test_results.csv").getFile().toPath();
		List<Result> expectedResults = List.of(
				new Result(new Person("John Doe", Gender.MALE), Distance.FIVE_KM, Duration.ofSeconds(1526)),
				new Result(new Person("Jane Doe", Gender.FEMALE), Distance.TEN_KM, Duration.ofSeconds(2520))
		);
		var results = resultsReader.readFromFile(filePath);
		assertThat(results).hasSize(2);
		assertThat(results).containsExactlyInAnyOrderElementsOf(expectedResults);
	}
	@Test
	void parseResult_shouldReturnCorrectResult() {
		var line = "John Doe, M, 5 km, 25:26";
		var expected = new Result(new Person("John Doe", Gender.MALE), Distance.FIVE_KM, Duration.ofSeconds(1526));
		var result = new ResultParser().parseResult(line);
		assertThat(result).isEqualTo(expected);
	}
	private String executeAndCaptureOutput(Runnable task) {
		var outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		task.run();
		System.setOut(System.out);
		return outContent.toString().trim();
	}
}