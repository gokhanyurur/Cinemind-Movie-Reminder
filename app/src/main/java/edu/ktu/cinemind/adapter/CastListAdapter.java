package edu.ktu.cinemind.adapter;

import android.content.Context;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.ktu.cinemind.R;
import edu.ktu.cinemind.config.PropertyReader;
import edu.ktu.cinemind.entity.Cast;


/**
 * The type Cast list adapter.
 */
public class CastListAdapter extends ArrayAdapter<Cast> {

    /**
     * The Context.
     */
    private Context context;

    /**
     * Instantiates a new Cast list adapter.
     *
     * @param context the context
     * @param objects the objects
     */
    public CastListAdapter(Context context, List<Cast> objects){
        super(context, R.layout.castcrewlistitemdesign,objects);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        if (v == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                v = inflater.inflate(R.layout.castcrewlistitemdesign,null);

                TextView name = v.findViewById(R.id.actorName);
                TextView roleAs = v.findViewById(R.id.actorAs);
                ImageView image = v.findViewById(R.id.actorImg);

                Cast person = getItem(position);

                if (person != null) {
                    name.setText(person.getName());
                    roleAs.setText(person.getCharacter());

                    String imgPath;
                    if (person.getImagePath().equals("null")){
                        imgPath = PropertyReader.getProperty("no.image.url", context);
                    } else{
                        imgPath = PropertyReader.getProperty("person.image.prefix", context) + person.getImagePath();
                    }
                    Glide.with(context)
                            .load(imgPath)
                            .into(image);
                }
            }
        }
        return v;
    }
}

