package id.developer.agungaprian.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.developer.agungaprian.popularmovies.R;
import id.developer.agungaprian.popularmovies.model.Trailer;

/**
 * Created by agungaprian on 04/08/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    Context context;
    ArrayList<Trailer> data = new ArrayList<>();

    public TrailerAdapter(Context context, ArrayList<Trailer> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.trailer_list, parent,false
        );
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        String url = "http://img.youtube.com/vi/".concat(data.get(position).getKey()).concat("/hqdefault.jpg");
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.unavailable_video)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.trailerImage);
        }
    }
}
