package org.san.moviecatalogservice.resources;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.san.moviecatalogservice.models.CatalogItem;
import org.san.moviecatalogservice.models.Movie;
import org.san.moviecatalogservice.models.Rating;
import org.san.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private WebClient.Builder webClientBuilder;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

//		List<Rating> ratings = Arrays.asList(new Rating("1234", 2), new Rating("5678", 5));
		UserRating userRating = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/" + userId,
				UserRating.class);
		// @formatter:off
		return userRating.getUserRatings().stream()
				.map(rating -> 
				{
					Movie movie = restTemplate.getForObject("http://localhost:8082/movies/"+rating.getMovieId(), Movie.class);					
					return new CatalogItem(movie.getName(), "Desc", rating.getRating());
				}
				)
				.collect(Collectors.toList());
			// @formatter:on

	}

}

//alternative for RestTemplate i.e. Reactive way in spring 
// Declare this bean definition in starter file
//@Bean
//public WebClient.Builder getWebClient() {
//	return WebClient.builder();
//}

//@Autowired
//private WebClient.Builder webClientBuilder;
//Movie movie = webClientBuilder.build()
//.get()
//.uri("http://localhost:8082/movies/"+rating.getMovieId())
//.retrieve()
//.bodyToMono(Movie.class)
//.block();
