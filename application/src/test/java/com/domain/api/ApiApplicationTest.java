package com.domain.api;

import com.domain.api.store.HttpPathStore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by dka on 4/21/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiApplicationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testPing() {
		ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(HttpPathStore.PING, String.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testPostLoginFail() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> requestEntity = new HttpEntity<>("parameters", headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(HttpPathStore.LOGIN, HttpMethod.POST, requestEntity, String.class);
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}

}