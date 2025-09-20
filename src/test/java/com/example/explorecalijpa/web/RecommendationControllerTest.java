package com.example.explorecalijpa.web;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.explorecalijpa.business.RecommendationService;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class RecommendationControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @MockBean
  private RecommendationService recommendationService;

  @Test
  void testTopRecommendations() {
    when(recommendationService.recommendTopN(anyInt())).thenReturn(List.of());
    ResponseEntity<String> res = restTemplate.getForEntity("/recommendations/top/2", String.class);
    assertThat(res.getStatusCode(), is(HttpStatus.OK));
    verify(recommendationService).recommendTopN(2);
  }

  @Test
  void testRecommendationsByCustomer() {
    when(recommendationService.recommendByCustomer(anyInt())).thenReturn(List.of());
    ResponseEntity<String> res = restTemplate.getForEntity("/recommendations/customer/1", String.class);
    assertThat(res.getStatusCode(), is(HttpStatus.OK));
    verify(recommendationService).recommendByCustomer(1);
  }
}

