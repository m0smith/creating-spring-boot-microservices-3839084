package com.example.explorecalijpa.model;

/**
 * Simple value object representing a recommended tour and its average score.
 */
public class TourRecommendation {
  private Integer tourId;
  private String title;
  private Double averageScore;

  public TourRecommendation(Tour tour, Double averageScore) {
    this.tourId = tour.getId();
    this.title = tour.getTitle();
    this.averageScore = averageScore;
  }

  public Integer getTourId() {
    return tourId;
  }

  public String getTitle() {
    return title;
  }

  public Double getAverageScore() {
    return averageScore;
  }
}

