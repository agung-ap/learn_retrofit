package id.developer.agungaprian.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.developer.agungaprian.popularmovies.R;
import id.developer.agungaprian.popularmovies.model.Riview;

/**
 * Created by agungaprian on 04/08/17.
 */

public class RiviewAdapter extends RecyclerView.Adapter<RiviewAdapter.RiviewViewHolder> {
    ArrayList<Riview> data = new ArrayList<>();
    Context context;

    public RiviewAdapter(Context context, ArrayList<Riview> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public RiviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.riview_list, parent, false
        );
        return new RiviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RiviewViewHolder holder, int position) {
        holder.author.setText(data.get(position).getAuthor());
        holder.riviewer.setText(data.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RiviewViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.authorText)
        TextView author;
        @Bind(R.id.reviewText)
        TextView riviewer;

        public RiviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
