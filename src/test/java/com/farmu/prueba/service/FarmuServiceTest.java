package com.farmu.prueba.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.farmu.prueba.dto.URLRequest;
import com.farmu.prueba.entity.URL;
import com.farmu.prueba.repository.FarmuRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class FarmuServiceTest {

	@InjectMocks
	FarmuService farmuService;

	@Mock
	FarmuRepository farmuRepository;

	@Test
	public void shortUrlTest() throws Exception {
		var url = new URL();
		url.setLongUrl("https://www.linkedin.com/in/jhon-alexander-lara-barrera-46156695/");
		url.setId(5);
		when(farmuRepository.save(any(URL.class))).thenReturn(url);
		var urlRequest = new URLRequest();
		urlRequest.setLongUrl("https://www.linkedin.com/in/jhon-alexander-lara-barrera-46156695/");

		assertEquals("F", farmuService.shortUrl(urlRequest));
	}

	@Test
	public void getLargeUrlTest() throws Exception {
		var url = new URL();
		url.setLongUrl("https://www.linkedin.com/in/jhon-alexander-lara-barrera-46156695/");
		url.setId(7);
		when(farmuRepository.findById((long) 7)).thenReturn(java.util.Optional.of(url));

		assertEquals("https://www.linkedin.com/in/jhon-alexander-lara-barrera-46156695/",
				farmuService.getLargeUrl("K"));
	}
}
