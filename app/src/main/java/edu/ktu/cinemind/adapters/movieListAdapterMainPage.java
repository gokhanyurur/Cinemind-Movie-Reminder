package edu.ktu.cinemind.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import edu.ktu.cinemind.R;
import edu.ktu.cinemind.objects.movieObj;


public class movieListAdapterMainPage extends ArrayAdapter<movieObj> {

    private Context context;


    public movieListAdapterMainPage(Context context, List<movieObj> objects){
        //super(context, R.layout.movieobjlistitemdesign,objects);
        super(context, R.layout.movieobjlistitemdesignmainpage,objects);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v= convertView;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        if(v == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //v=inflater.inflate(R.layout.movieobjlistitemdesign,null);
            v=inflater.inflate(R.layout.movieobjlistitemdesignmainpage,null);
        }

        TextView title=(TextView) v.findViewById(R.id.movieTitle);
        TextView releaaseDate =(TextView) v.findViewById(R.id.inTheater);
        ImageView image=(ImageView) v.findViewById(R.id.movieposter);
        TextView vote_ave=(TextView) v.findViewById(R.id.vote_ave);

        movieObj item = getItem(position);

        //title.setText(item.getTitle().toUpperCase());
        title.setText(item.getTitle());
        releaaseDate.setText(item.getRelease_date());
        if(item.getVote_average()==0){
            vote_ave.setText("NA");
        }else{
            vote_ave.setText(String.valueOf(item.getVote_average()));
        }

        //String imgPath= "https://image.tmdb.org/t/p/w342"+item.getPoster_path();
        String imgPath= "https://image.tmdb.org/t/p/w500"+item.getPoster_path();

        if(item.getPoster_path().equals("null")){
            image.setImageResource(R.drawable.noimage1);
        }else{
            Picasso.with(context)
                    .load(imgPath)
                    .resize(250, 375)
                    .into(image);
        }

        return v;
    }

}

