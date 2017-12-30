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

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import edu.ktu.cinemind.R;
import edu.ktu.cinemind.objects.crew;


public class crewListAdapter extends ArrayAdapter<crew> {

    private Context context;

    public crewListAdapter(Context context, List<crew> objects){
        super(context, R.layout.castcrewlistitemdesign,objects);

        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v= convertView;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        if(v == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.castcrewlistitemdesign,null);
        }

        TextView name=(TextView) v.findViewById(R.id.actorName);
        TextView job =(TextView) v.findViewById(R.id.actorAs);
        ImageView image=(ImageView) v.findViewById(R.id.actorImg);

        crew person = getItem(position);

        name.setText(person.getName());
        job.setText(person.getJob());

        String imgPath= "https://image.tmdb.org/t/p/w92"+person.getImagePath();

        if(person.getImagePath().equals("null")){
            Glide.with(context)
                    .load("http://mariabiju.com/wp-content/themes/bazar/core/assets/images/no-featured-175.jpg")
                    .into(image);
        }
        else{
            Glide.with(context)
                    .load(imgPath)
                    .into(image);
        }

        return v;
    }


}

