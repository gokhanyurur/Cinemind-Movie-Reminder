package edu.ktu.cinemind;

import android.content.Context;

import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.sql.DataSource;


public class movieListAdapter extends ArrayAdapter<movieObj> {

    private Context context;


    public movieListAdapter(Context context, List<movieObj> objects){
        super(context, R.layout.movieobjlistitemdesign,objects);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v= convertView;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        if(v == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.movieobjlistitemdesign,null);
        }

        TextView title=(TextView) v.findViewById(R.id.movieTitle);
        TextView releaaseDate =(TextView) v.findViewById(R.id.inTheater);
        ImageView image=(ImageView) v.findViewById(R.id.movieposter);

        movieObj item = getItem(position);

        title.setText(item.getTitle().toUpperCase());
        releaaseDate.setText(item.getRelease_date());

        //String imgPath= "https://image.tmdb.org/t/p/w342"+item.getPoster_path();
        String imgPath= "https://image.tmdb.org/t/p/w500"+item.getPoster_path();
        Drawable drawable = LoadImageFromWebOperations(imgPath.toString());
        //image.setImageDrawable(drawable);

        Picasso.with(context)
                .load(imgPath)
                .resize(250, 375)
                .into(image);

        return v;
    }

    private Drawable LoadImageFromWebOperations(String url) {

        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            System.out.println("Exc=" + e);
            return null;
        }

    }

}

