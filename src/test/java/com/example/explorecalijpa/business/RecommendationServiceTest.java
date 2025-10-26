package com.example.explorecalijpa.business;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.explorecalijpa.model.Tour;
import com.example.explorecalijpa.model.TourRating;
import com.example.explorecalijpa.model.TourRecommendation;
import com.example.explorecalijpa.repo.TourRatingRepository;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTest {

  @Mock
  private TourRatingRepository tourRatingRepository;

  private RecommendationService recommendationService;

  private Tour tour1;
  private Tour tour2;
  private Tour tour3;
  private List<TourRating> allRatings;

  @BeforeEach
  void setup() {
    recommendationService = new RecommendationService(tourRatingRepository);

    tour1 = mock(Tour.class);
    when(tour1.getId()).thenReturn(1);
    when(tour1.getTitle()).thenReturn("t1");

    tour2 = mock(Tour.class);
    when(tour2.getId()).thenReturn(2);
    when(tour2.getTitle()).thenReturn("t2");

    tour3 = mock(Tour.class);
    when(tour3.getId()).thenReturn(3);
    when(tour3.getTitle()).thenReturn("t3");

    allRatings = List.of(
        new TourRating(tour1, 1, 5),
        new TourRating(tour1, 2, 4),
        new TourRating(tour2, 3, 2),
        new TourRating(tour3, 2, 5),
        new TourRating(tour3, 3, 5));
  }

  @Test
  void recommendTopN() {
    when(tourRatingRepository.findAll()).thenReturn(allRatings);

    List<TourRecommendation> recs = recommendationService.recommendTopN(2);

    assertThat(recs.size(), is(2));
    assertThat(recs.get(0).getTourId(), is(3));
    assertThat(recs.get(1).getTourId(), is(1));
  }

  @Test
  void recommendByCustomer() {
    when(tourRatingRepository.findAll()).thenReturn(allRatings);
    when(tourRatingRepository.findByCustomerId(1)).thenReturn(List.of(new TourRating(tour1, 1, 5)));

    List<TourRecommendation> recs = recommendationService.recommendByCustomer(1);

    assertThat(recs.size(), is(2));
    assertThat(recs.get(0).getTourId(), is(3));
    assertThat(recs.get(1).getTourId(), is(2));
  }
}

