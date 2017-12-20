package edu.ktu.cinemind.adapters;



import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.ktu.cinemind.R;

public class mySwipeAdapter extends android.support.v4.view.PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    public static List<String> images =new  ArrayList<>();


    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.movieimagesdesign,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.movieImageView);
        //imageView.setImageResource(images.get(position));

        String imgPath= "https://image.tmdb.org/t/p/w342"+images.get(position);

        Picasso.with(context).load(imgPath)
                .error(R.drawable.noimage2)
                .placeholder(R.drawable.noimage2)
                .into(imageView);

        ViewPager vp = (ViewPager) container;
        vp.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
