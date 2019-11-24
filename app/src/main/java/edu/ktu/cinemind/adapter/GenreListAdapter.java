package edu.ktu.cinemind.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.ktu.cinemind.R;
import edu.ktu.cinemind.entity.genreObj;


/**
 * The type Genre list adapter.
 */
public class GenreListAdapter extends ArrayAdapter<genreObj> {

    /**
     * Instantiates a new Genre list adapter.
     *
     * @param context the context
     * @param objects the objects
     */
    public GenreListAdapter(Context context, List<genreObj> objects){
        super(context, R.layout.genrelistitemdesign,objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        if(v == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (inflater != null){
                v = inflater.inflate(R.layout.genrelistitemdesign,null);

                TextView title = v.findViewById(R.id.genreTitle);
                ImageView image = v.findViewById(R.id.genreImg);

                genreObj genre = getItem(position);

                if (genre != null){
                    title.setText(genre.getTitle());
                    image.setImageResource(genre.getImageId());
                }
            }

        }
        return v;
    }
}

