package com.example.explorecalijpa.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.explorecalijpa.business.RecommendationService;
import com.example.explorecalijpa.model.TourRecommendation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * REST controller exposing tour recommendation endpoints.
 */
@RestController
@Tag(name = "Recommendations", description = "Tour recommendation API")
@RequestMapping("/recommendations")
public class RecommendationController {

  private final RecommendationService recommendationService;

  public RecommendationController(RecommendationService recommendationService) {
    this.recommendationService = recommendationService;
  }

  @GetMapping("/customer/{customerId}")
  @Operation(summary = "Recommend tours for a customer")
  public List<TourRecommendation> recommendForCustomer(@PathVariable int customerId) {
    return recommendationService.recommendByCustomer(customerId);
  }

  @GetMapping("/top/{limit}")
  @Operation(summary = "Get top rated tours")
  public List<TourRecommendation> recommendTop(@PathVariable int limit) {
    return recommendationService.recommendTopN(limit);
  }
}

