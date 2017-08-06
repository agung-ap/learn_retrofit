package id.developer.agungaprian.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.developer.agungaprian.popularmovies.adapter.RiviewAdapter;
import id.developer.agungaprian.popularmovies.adapter.TrailerAdapter;
import id.developer.agungaprian.popularmovies.model.Movie;
import id.developer.agungaprian.popularmovies.model.Riview;
import id.developer.agungaprian.popularmovies.model.RiviewResponse;
import id.developer.agungaprian.popularmovies.model.Trailer;
import id.developer.agungaprian.popularmovies.model.TrailerResponse;
import id.developer.agungaprian.popularmovies.rest.ApiClient;
import id.developer.agungaprian.popularmovies.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by agungaprian on 03/08/17.
 */

public class DetailActivity extends AppCompatActivity {
    Movie movies;

    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.titleView)
    TextView titleView;
    @Bind(R.id.rating)
    TextView rating;
    @Bind(R.id.ratingBar)
    RatingBar ratingBar;
    @Bind(R.id.overview)
    TextView overview;
    @Bind(R.id.releaseText)
    TextView releaseText;
    @Bind(R.id.trailersRecyclerView)
    RecyclerView trailersRecyclerView;
    @Bind(R.id.reviewsRecyclerView)
    RecyclerView riviewsRecyclerView;
    @Bind(R.id.noReviewView)
    TextView noReviewView;
    @Bind(R.id.noTrailerView)
    TextView noTrailerView;
    @Bind(R.id.extras)
    LinearLayout extraLayout;

    ArrayList<Trailer> trailerList;
    ArrayList<Riview> riviewList;

    RiviewAdapter riviewAdapter;
    TrailerAdapter trailerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        movies = new Movie();
        movies = getIntent().getParcelableExtra("movies");

        //init ArrayList
        trailerList = new ArrayList<>();
        riviewList = new ArrayList<>();

        //init adapter
        trailerAdapter = new TrailerAdapter(this,trailerList);
        riviewAdapter = new RiviewAdapter(this,riviewList);

        //set original title from json
        titleView.setText(movies.getOriginalTitle());

        //load image poster
        Picasso.with(this)
                .load(movies.getPosterPath())
                .placeholder(R.drawable.ic_action_placeholder)
                .error(R.drawable.broken_image)
                .into(imageView);

        Log.v("rating bar ", "total : " + String.valueOf(movies.getVoteAverage()));
        rating.setText(String.valueOf(movies.getVoteAverage()).concat("/10"));
        ratingBar.setMax(5);
        ratingBar.setRating(movies.getVoteAverage() / 2f);

        overview.setText(movies.getOverview());

        String releaseDate = movies.getReleaseDate();

        /*if(releaseDate != null) {
            try {
                releaseDate = DateTimeHelper.getLocalizedDate(this,
                        releaseDate, movies.getDateFormat());
            } catch (ParseException e) {
                Log.e("LOG_TAG ", "Error with parsing movie release date", e);
            }
        } else {
            releaseText.setTypeface(null, Typeface.ITALIC);
            releaseDate = "no release date found";
        }*/

        releaseText.setText("Release Date: ".concat(releaseDate));

        (new FetchRiviewData()).execute(String.valueOf(movies.getId()));
        (new FetchTrailerData()).execute(String.valueOf(movies.getId()));

        if (!isNetworkAvailable())
            extraLayout.setVisibility(View.INVISIBLE);
        else {
            //(new FetchRiviewData()).execute(String.valueOf(movies.getId()));
            //(new FetchTrailerData()).execute(String.valueOf(movies.getId()));
        }

        //set layout manager for recycler view
        LinearLayoutManager trailerLayoutManager = new LinearLayoutManager(getApplicationContext()
                ,LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager riviewLayoutManager = new LinearLayoutManager(getApplicationContext()
                ,LinearLayoutManager.VERTICAL, false);

        //set adapter to recycler view
        trailersRecyclerView.setAdapter(trailerAdapter);
        riviewsRecyclerView.setAdapter(riviewAdapter);

        trailersRecyclerView.setLayoutManager(trailerLayoutManager);
        riviewsRecyclerView.setLayoutManager(riviewLayoutManager);


    }
    //check network state
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail,menu);
        menu.findItem(R.id.share);
        menu.findItem(R.id.fav);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            //homebutton back control
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    //get data from trailer api
    public class FetchTrailerData extends AsyncTask<String, Void , List<Trailer>> {
        //place your api key here
        private final String API_KEY = "5dcd6ed59f6311eeeaeb846201f551b6";

        @Override
        protected List<Trailer> doInBackground(String... params) {
            final String sort =  params[0];

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<TrailerResponse> call = apiService.loadTrailers(sort, API_KEY);
            call.enqueue(new Callback<TrailerResponse>() {
                @Override
                public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                    Log.v("TAG ","response.raw().request().url();"+response.raw().request().url());

                    for (int i = 0 ; i < response.body().getResults().size(); i++){
                        trailerList.add(response.body().getResults().get(i));
                    }
                        trailerAdapter.notifyDataSetChanged();

                    if (trailerList.isEmpty()) {
                        trailersRecyclerView.setVisibility(View.INVISIBLE);
                        noTrailerView.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<TrailerResponse> call, Throwable t) {

                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(List<Trailer> trailers) {
            //super.onPostExecute(trailers);
        }
    }
    //get data from review api
    public class FetchRiviewData extends AsyncTask<String, Void , List<Riview>>{
        //place your api key here
        private final String API_KEY = "5dcd6ed59f6311eeeaeb846201f551b6";

        @Override
        protected List<Riview> doInBackground(String... params) {
            final String sort =  params[0];

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<RiviewResponse> call = apiService.loadRiviews(sort, API_KEY);
            call.enqueue(new Callback<RiviewResponse>() {
                @Override
                public void onResponse(Call<RiviewResponse> call, Response<RiviewResponse> response) {
                    Log.v("dapatkan response ", "result : " + response.body().getResults());
                    for (int i = 0 ; i < response.body().getResults().size(); i++){
                        riviewList.add(response.body().getResults().get(i));
                    }
                        riviewAdapter.notifyDataSetChanged();

                    if (riviewList.isEmpty()) {
                        riviewsRecyclerView.setVisibility(View.INVISIBLE);
                        noReviewView.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<RiviewResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(List<Riview> riviews) {
            //super.onPostExecute(riviews);
        }
    }
}