package id.developer.agungaprian.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.developer.agungaprian.popularmovies.adapter.MoviesAdapter;
import id.developer.agungaprian.popularmovies.model.Movie;
import id.developer.agungaprian.popularmovies.model.MovieResponse;
import id.developer.agungaprian.popularmovies.rest.ApiClient;
import id.developer.agungaprian.popularmovies.rest.ApiInterface;
import id.developer.agungaprian.popularmovies.utils.RecyclerTouchListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.recycler_view)
    public RecyclerView recyclerView;

    public MoviesAdapter popularAdapter;
    public MoviesAdapter ratedAdapter;
    public MoviesAdapter favouriteAdapter;

    public String SORT_BY = "POPULAR";

    public ArrayList<Movie> popularList;
    public ArrayList<Movie> ratedList;
    public ArrayList<Movie> favouriteList;

    public GridLayoutManager gridLayoutManager;

    //public NetworkReceiver networkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        popularList = new ArrayList<>();
        ratedList = new ArrayList<>();
        favouriteList = new ArrayList<>();

        popularAdapter = new MoviesAdapter(popularList, this);
        ratedAdapter = new MoviesAdapter(ratedList,this);
        favouriteAdapter = new MoviesAdapter(favouriteList, this);

        //networkReceiver = new NetworkReceiver();

        if (savedInstanceState != null){
            popularAdapter.addAll(savedInstanceState.<Movie>getParcelableArrayList("POP"));
            ratedAdapter.addAll(savedInstanceState.<Movie>getParcelableArrayList("RATED"));
            SORT_BY = savedInstanceState.getString("SORT_BY");
        }else {
            if (isNetworkAvailable()) {
                (new FetchMoviesData()).execute("popular");
                (new FetchMoviesData()).execute("top_rated");
            }
        }

        setupRecyclerView();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                new RecyclerTouchListener.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {
                        Movie movie = null;
                        switch (SORT_BY){
                            case "POPULAR":
                                movie = popularList.get(position);
                                break;
                            case "TOP_RATED":
                                movie = ratedList.get(position);
                                break;
                            /*default:
                                movie = favouriteList.get(position);
                                break;*/
                        }

                        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                        intent.putExtra("movies", movie);

                        startActivity(intent);
                    }
                }));
    }

    //network checker
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    private void setupRecyclerView() {
        gridLayoutManager = new GridLayoutManager(this, 2);

        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT){
            gridLayoutManager.setSpanCount(2);
        }else {
            gridLayoutManager.setSpanCount(3);
        }

        switch (SORT_BY){
            case "POPULAR" : recyclerView.setAdapter(popularAdapter);
                break;
            case "RATED" : recyclerView.setAdapter(ratedAdapter);
                break;
            case "FAVOURITE" : recyclerView.setAdapter(favouriteAdapter);
        }

        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        switch (SORT_BY){
            case "POPULAR" : menu.findItem(R.id.action_sort_by_popularity).setChecked(true);
                break;
            case "RATED" : menu.findItem(R.id.action_sort_by_rating).setChecked(true);
                break;
            case "FAVOURITE" : menu.findItem(R.id.action_sort_by_favourite).setChecked(true);
                break;
            }

            return true;
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_sort_by_popularity:
                recyclerView.setAdapter(popularAdapter);
                SORT_BY = "POPULAR";
                break;
            case R.id.action_sort_by_rating:
                recyclerView.setAdapter(ratedAdapter);
                SORT_BY = "RATED";
                break;
            case R.id.action_sort_by_favourite:
                recyclerView.setAdapter(favouriteAdapter);
                SORT_BY = "FAVOURITE";
                break;
        }

        item.setChecked(true);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayoutManager.setSpanCount(3);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager.setSpanCount(2);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("POP", popularList);
        outState.putParcelableArrayList("RATED",ratedList);
        outState.putString("SORT_BY",SORT_BY);

    }

    /*@Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkReceiver);
    }*/

    public class FetchMoviesData extends AsyncTask<String, Void , List<Movie>> {
        //place your api key here
        private final String API_KEY = "5dcd6ed59f6311eeeaeb846201f551b6";

        @Override
        protected List<Movie> doInBackground(String... params) {
            final String sort = params[0];

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<MovieResponse> call = apiService.loadMovies(sort,API_KEY);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    if (sort.equals("popular")){
                        for (int i = 0; i < response.body().getResults().size(); i++){
                            popularList.add(response.body().getResults().get(i));
                            Log.v(sort, response.body().getResults().get(i).getOriginalTitle());
                        }
                        //popularList = (ArrayList<Movie>) response.body().getResults();
                        popularAdapter.notifyDataSetChanged();
                    }else if (sort.equals("top_rated")){
                        for (int i = 0; i < response.body().getResults().size(); i++) {
                            ratedList.add(response.body().getResults().get(i));
                            Log.v(sort, response.body().getResults().get(i).getOriginalTitle());
                        }
                        // ratedList = (ArrayList<Movie>) response.body().getResults();
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

    /*public class NetworkReceiver extends BroadcastReceiver {
        public ArrayList<Movie> popularList;

        public NetworkReceiver(){

        }

        @Override
        public void onReceive(Context context, Intent intent) {
        MainActivity activity = new MainActivity();
        if (activity.isNetworkAvailable() && popularList.size()!=0) {
            (new FetchMoviesData()).execute("popular");
            (new FetchMoviesData()).execute("top_rated");
        }
        Toast.makeText(activity, "Jaringan tidak stabil", Toast.LENGTH_SHORT).show();

        }
    }*/

}


















