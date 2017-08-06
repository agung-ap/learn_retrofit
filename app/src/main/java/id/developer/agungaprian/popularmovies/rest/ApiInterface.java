package id.developer.agungaprian.popularmovies.rest;

import id.developer.agungaprian.popularmovies.model.MovieResponse;
import id.developer.agungaprian.popularmovies.model.RiviewResponse;
import id.developer.agungaprian.popularmovies.model.TrailerResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by agungaprian on 30/07/17.
 */

public interface ApiInterface {
    @GET("movie/{sort}")
    Call<MovieResponse> loadMovies(@Path("sort") String sorting , @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<TrailerResponse> loadTrailers(@Path("id") String id, @Query("api_key") String api_key);

    @GET("movie/{id}/reviews")
    Call<RiviewResponse> loadRiviews(@Path("id") String id, @Query("api_key") String api_key);
}
