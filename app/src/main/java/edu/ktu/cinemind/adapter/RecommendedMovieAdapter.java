package edu.ktu.cinemind.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.ktu.cinemind.R;
import edu.ktu.cinemind.config.PropertyReader;
import edu.ktu.cinemind.entity.movieObj;

/**
 * The type Recommended movie adapter.
 */
public class RecommendedMovieAdapter extends RecyclerView.Adapter<RecommendedMovieAdapter.HorizontalViewHolder>{

    /**
     * The Movies.
     */
    private List<movieObj> movies;

    /**
     * The Context.
     */
    public Context context;

    /**
     * Instantiates a new Recommended movie adapter.
     *
     * @param movies the movies
     */
    public RecommendedMovieAdapter(List<movieObj> movies){
        this.movies =movies;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.recyclerviewitem,parent,false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, int position) {
        holder.movieTitle.setText(movies.get(position).getTitle());

        String imgPath = PropertyReader.getProperty("movie.poster.prefix", context) + movies.get(position).getPoster_path();

        Picasso.with(holder.moviePoster.getContext())
                .load(imgPath)
                .resize(500, 750)
                .into(holder.moviePoster);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    /**
     * The type Horizontal view holder.
     */
    public class HorizontalViewHolder extends RecyclerView.ViewHolder{

        /**
         * The Movie title.
         */
        private TextView movieTitle;

        /**
         * The Movie poster.
         */
        private ImageView moviePoster;

        /**
         * Instantiates a new Horizontal view holder.
         *
         * @param itemView the item view
         */
        public HorizontalViewHolder(View itemView){
            super(itemView);
            movieTitle= itemView.findViewById(R.id.recMovieTitle);
            moviePoster=itemView.findViewById(R.id.recMovieposter);
        }
    }
}
