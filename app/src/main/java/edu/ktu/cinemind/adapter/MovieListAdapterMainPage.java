package edu.ktu.cinemind.adapter;

import android.content.Context;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.ktu.cinemind.R;
import edu.ktu.cinemind.config.PropertyReader;
import edu.ktu.cinemind.entity.Movie;


/**
 * The type Movie list adapter main page.
 */
public class MovieListAdapterMainPage extends ArrayAdapter<Movie> {

    /**
     * The Context.
     */
    private Context context;

    /**
     * Instantiates a new Movie list adapter main page.
     *
     * @param context the context
     * @param objects the objects
     */
    public MovieListAdapterMainPage(Context context, List<Movie> objects){
        super(context, R.layout.movieobjlistitemdesignmainpage,objects);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        if(v == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (inflater != null) {
                v = inflater.inflate(R.layout.movieobjlistitemdesignmainpage,null);

                TextView title = v.findViewById(R.id.movieTitle);
                TextView releaseDate = v.findViewById(R.id.inTheater);
                ImageView image = v.findViewById(R.id.movieposter);
                TextView voteAverage = v.findViewById(R.id.vote_ave);

                Movie movie = getItem(position);

                if (movie != null){
                    title.setText(movie.getTitle());
                    releaseDate.setText(movie.getReleaseDate());

                    if(movie.getVoteAverage() == 0){
                        voteAverage.setText(PropertyReader.getProperty("not.known", context));
                    } else{
                        voteAverage.setText(String.valueOf(movie.getVoteAverage()));
                    }

                    String imgPath= PropertyReader.getProperty("movie.poster.prefix", context) + movie.getPosterPath();

                    if (movie.getPosterPath().equals("null")){
                        image.setImageResource(R.drawable.noimage1);
                    } else {
                        Picasso.with(context)
                                .load(imgPath)
                                .resize(250, 375)
                                .into(image);
                    }
                }
            }
        }
        return v;
    }

}

