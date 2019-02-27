package org.san.ratingsdataservice.resources;

import java.util.Arrays;
import java.util.List;

import org.san.ratingsdataservice.models.Rating;
import org.san.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId) {
		return new Rating(movieId, 4);
	}

	@RequestMapping("/users/{userId}")
	public UserRating getUserRatings(@PathVariable("userId") String userId) {
		// @formatter:off
		List<Rating> ratings = Arrays.asList(
				new Rating("1234", 4), 
				new Rating("5678", 3)
				);
		// @formatter:on

		return new UserRating(ratings);
	}

}
