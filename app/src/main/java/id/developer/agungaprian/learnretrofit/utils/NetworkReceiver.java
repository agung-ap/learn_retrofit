package id.developer.agungaprian.learnretrofit.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;

import id.developer.agungaprian.learnretrofit.MainActivity;
import id.developer.agungaprian.learnretrofit.model.Movie;

/**
 * Created by agungaprian on 03/08/17.
 */

public class NetworkReceiver extends BroadcastReceiver {
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
}
