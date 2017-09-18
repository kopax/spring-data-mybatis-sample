package com.domain.api;

import com.domain.api.config.DataSourceCoreProperties;
import com.domain.api.config.Oauth2Properties;
import com.domain.api.store.HttpPathStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@EnableAutoConfiguration
@RestController
@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ApiApplication.class);

	@Autowired
	private DataSourceCoreProperties dataSourceCoreProperties;

	@Override
	public void run(String... args) throws Exception {
		logger.info("--- dataSourceCoreProperties ---");
		logger.info("URL: " + dataSourceCoreProperties.getUrl());
		logger.info("VERSION: " + dataSourceCoreProperties.getVersion());
	}

	public static void main(String[] args) throws Exception {
		org.apache.ibatis.logging.LogFactory.useSlf4jLogging();
		SpringApplication.run(ApiApplication.class, args);
		logger.info("API started!");
	}

	@RequestMapping(value = HttpPathStore.PING, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> ping() throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		map.put("message", "Welcome to our API");
		map.put("date", new Date());
		map.put("status", HttpStatus.OK);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

}