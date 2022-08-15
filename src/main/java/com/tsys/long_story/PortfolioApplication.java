package com.tsys.long_story;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

@SpringBootApplication
public class PortfolioApplication {

	private static final Logger LOG = Logger.getLogger(PortfolioApplication.class.getName());

	public static void main(String[] args) {
		Stream.of(args)
				.flatMap(arg -> Stream.iterate(0, i -> i + 1)
						.map(idx -> List.of(idx, arg)))
				.forEach(argWithIdx -> LOG.info(argWithIdx.toString()));
		SpringApplication.run(PortfolioApplication.class, args);
	}
}
