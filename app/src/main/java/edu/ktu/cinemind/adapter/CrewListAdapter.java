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
import edu.ktu.cinemind.entity.Crew;


/**
 * The type Crew list adapter.
 */
public class CrewListAdapter extends ArrayAdapter<Crew> {

    /**
     * The Context.
     */
    private Context context;

    /**
     * Instantiates a new Crew list adapter.
     *
     * @param context the context
     * @param objects the objects
     */
    public CrewListAdapter(Context context, List<Crew> objects){
        super(context, R.layout.castcrewlistitemdesign,objects);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                v = inflater.inflate(R.layout.castcrewlistitemdesign,null);
            }
        } else {
            v = convertView;
        }

        if (v != null) {
            TextView name = v.findViewById(R.id.actorName);
            TextView job = v.findViewById(R.id.actorAs);
            ImageView image = v.findViewById(R.id.actorImg);

            Crew person = getItem(position);

            if (person != null){
                name.setText(person.getName());
                job.setText(person.getJob());

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
        return v;
    }
}

