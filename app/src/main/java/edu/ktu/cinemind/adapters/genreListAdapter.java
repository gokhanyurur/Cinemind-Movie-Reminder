package edu.ktu.cinemind.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.ktu.cinemind.R;
import edu.ktu.cinemind.objects.genreObj;


public class genreListAdapter extends ArrayAdapter<genreObj> {

    public genreListAdapter(Context context, List<genreObj> objects){
        super(context, R.layout.genrelistitemdesign,objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v= convertView;

        if(v == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.genrelistitemdesign,null);
        }

        TextView title=(TextView) v.findViewById(R.id.genreTitle);
        ImageView image=(ImageView) v.findViewById(R.id.genreImg);

        genreObj item = getItem(position);

        title.setText(item.getTitle());
        image.setImageResource(item.getImageId());

        return v;
    }

}

