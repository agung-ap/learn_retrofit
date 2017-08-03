package id.developer.agungaprian.learnretrofit.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import id.developer.agungaprian.learnretrofit.adapter.MoviesAdapter;
import id.developer.agungaprian.learnretrofit.model.Movie;
import id.developer.agungaprian.learnretrofit.model.MovieResponse;
import id.developer.agungaprian.learnretrofit.rest.ApiClient;
import id.developer.agungaprian.learnretrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by agungaprian on 03/08/17.
 */


public class FetchMoviesData extends AsyncTask<String, Void , List<Movie>> {
    private static final String TAG = AsyncTask.class.getSimpleName();
    //place your api key here
    private static final String API_KEY = "5dcd6ed59f6311eeeaeb846201f551b6";

    public MoviesAdapter popularAdapter;
    public MoviesAdapter ratedAdapter;

    public ArrayList<Movie> popularList;
    public ArrayList<Movie> ratedList;

    @Override
    protected List<Movie> doInBackground(String... params) {
        final String sort = params[0];
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse> call = apiService.getTopRatedFilm(sort,API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (sort.equals("popular")){
                    for (int i = 0; i < response.body().getResults().size(); i++){
                        popularList.add(response.body().getResults().get(i));
                        Log.v(sort, response.body().getResults().get(i).getOriginalTitle());
                    }
                    popularAdapter.notifyDataSetChanged();
                }else {
                    for (int i = 0; i < response.body().getResults().size(); i++) {
                        ratedList.add(response.body().getResults().get(i));
                        Log.v(sort, response.body().getResults().get(i).getOriginalTitle());
                    }
                    ratedAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movieModels) {

    }
}
