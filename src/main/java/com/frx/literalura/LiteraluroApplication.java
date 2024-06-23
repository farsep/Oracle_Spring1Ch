package com.frx.literalura;

import com.frx.literalura.principal.Executable;
import com.frx.literalura.service.Http;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluroApplication implements CommandLineRunner {
	@Autowired
	private Executable executable;

	private Dotenv dotenv = Dotenv.load();

	@Autowired
	private Http http;
	public static void main(String[] args) {
		SpringApplication.run(LiteraluroApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		String databaseUrl = dotenv.get("DATABASE_URL");
		String databaseUsername = dotenv.get("DATABASE_USERNAME");
		String databasePassword = dotenv.get("DATABASE_PASSWORD");

		System.out.println("DATABASE_URL: " + databaseUrl);
		System.out.println("DATABASE_USERNAME: " + databaseUsername);
		System.out.println("DATABASE_PASSWORD: " + databasePassword);
		executable.execute();
	}
}
