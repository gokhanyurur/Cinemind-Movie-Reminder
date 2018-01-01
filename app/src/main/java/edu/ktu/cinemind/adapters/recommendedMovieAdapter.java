package edu.ktu.cinemind.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import edu.ktu.cinemind.R;
import edu.ktu.cinemind.objects.movieObj;

public class recommendedMovieAdapter extends RecyclerView.Adapter<recommendedMovieAdapter.HorizontalViewHolder>{

    private List<movieObj> items;

    public Context context;

    public recommendedMovieAdapter(List<movieObj> items){
        this.items=items;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.recyclerviewitem,parent,false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, int position) {
        holder.movieTitle.setText(items.get(position).getTitle());
       // holder.voteAvg.setText(String.valueOf(items.get(position).getVote_average()));

        System.out.println(items.get(position).getTitle()+" s vote is "+items.get(position).getVote_average());

        String imgPath="https://image.tmdb.org/t/p/w500"+items.get(position).getPoster_path();

        Picasso.with(holder.moviePoster.getContext())
                .load(imgPath)
                .resize(500, 750) // 255,375 low res
                .into(holder.moviePoster);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder{
        TextView movieTitle,voteAvg;
        ImageView moviePoster;
        public HorizontalViewHolder(View itemView){
            super(itemView);
            movieTitle= itemView.findViewById(R.id.recMovieTitle);
            //voteAvg=itemView.findViewById(R.id.recVote_ave);
            moviePoster=itemView.findViewById(R.id.recMovieposter);
        }
    }
}
