package id.developer.agungaprian.learnretrofit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.developer.agungaprian.learnretrofit.R;
import id.developer.agungaprian.learnretrofit.model.Movie;

/**
 * Created by agungaprian on 30/07/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private List<Movie> movies;
    private int rowLayout;
    private Context context;

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;
        ImageView imageView;


        public MovieViewHolder(View view) {
            super(view);
            //moviesLayout = (LinearLayout) view.findViewById(R.id.movies_layout);
            //movieTitle = (TextView) view.findViewById(R.id.title);
            //data = (TextView) view.findViewById(R.id.subtitle);
            //movieDescription = (TextView) view.findViewById(R.id.description);
            //rating = (TextView) view.findViewById(R.id.rating);
            imageView = (ImageView) view.findViewById(R.id.image_view);
        }
    }

    public MoviesAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        //load image recyclerview
        Picasso.with(context)
                .load(movies.get(position).getPosterPath())
                //.resize(context.getResources().getInteger(R.integer.tmdb_poster_w185_height),
                    //    context.getResources().getInteger(R.integer.tmdb_poster_w185_width))
                .error(R.drawable.broken_image)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
