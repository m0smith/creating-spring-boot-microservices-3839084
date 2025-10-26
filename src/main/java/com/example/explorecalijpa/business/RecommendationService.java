package com.example.explorecalijpa.business;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.explorecalijpa.model.Tour;
import com.example.explorecalijpa.model.TourRating;
import com.example.explorecalijpa.model.TourRecommendation;
import com.example.explorecalijpa.repo.TourRatingRepository;

/**
 * Service providing tour recommendations based on ratings.
 */
@Service
public class RecommendationService {

  private final TourRatingRepository tourRatingRepository;
  private final Map<Integer, List<TourRecommendation>> topNCache = new ConcurrentHashMap<>();

  public RecommendationService(TourRatingRepository tourRatingRepository) {
    this.tourRatingRepository = tourRatingRepository;
  }

  /**
   * Recommend tours to a customer based on overall ratings excluding tours the
   * customer has already rated.
   *
   * @param customerId customer identifier
   * @return list of recommended tours sorted by average score
   */
  public List<TourRecommendation> recommendByCustomer(int customerId) {
    List<TourRating> allRatings = tourRatingRepository.findAll();
    Set<Integer> ratedByCustomer = tourRatingRepository.findByCustomerId(customerId).stream()
        .map(tr -> tr.getTour().getId()).collect(Collectors.toSet());

    Map<Tour, Double> averages = allRatings.stream()
        .collect(Collectors.groupingBy(TourRating::getTour, Collectors.averagingInt(TourRating::getScore)));

    return averages.entrySet().stream()
        .filter(e -> !ratedByCustomer.contains(e.getKey().getId()))
        .sorted(Map.Entry.<Tour, Double>comparingByValue().reversed())
        .map(e -> new TourRecommendation(e.getKey(), e.getValue()))
        .collect(Collectors.toList());
  }

  /**
   * Recommend the top N tours across all customers.
   *
   * @param limit number of tours to return
   * @return top rated tours
   */
  public List<TourRecommendation> recommendTopN(int limit) {
    if (topNCache.containsKey(limit)) {
      return topNCache.get(limit);
    }

    List<TourRating> ratings = tourRatingRepository.findAll();
    Map<Tour, Double> averages = ratings.stream()
        .collect(Collectors.groupingBy(TourRating::getTour, Collectors.averagingInt(TourRating::getScore)));

    List<TourRecommendation> result = averages.entrySet().stream()
        .sorted(Map.Entry.<Tour, Double>comparingByValue().reversed())
        .limit(limit)
        .map(e -> new TourRecommendation(e.getKey(), e.getValue()))
        .collect(Collectors.toList());

    topNCache.put(limit, result);
    return result;
  }

  /**
   * Clear cached recommendation results periodically.
   */
  @Scheduled(fixedDelay = 300000)
  public void evictCache() {
    topNCache.clear();
  }
}

