package edu.ktu.cinemind.adapters;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.ktu.cinemind.R;
import edu.ktu.cinemind.pages.movieDetails;
import edu.ktu.cinemind.objects.image;

public class ViewPagerAdapter extends android.support.v4.view.PagerAdapter{

    private Context context;
    private LayoutInflater layoutInflater;
    public static List<image> images;

    public ViewPagerAdapter(Context context){
        this.context=context;
    }


    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.movieimagesitem, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.movieImages);
       // imageView.setImageResource(); //use picasso to show images

        String imgPath= "https://image.tmdb.org/t/p/w500"+images.get(position).getFilePath();

        Picasso.with(context)
                .load(imgPath)
                //.resize(500, 281)
                .into(imageView);

        ViewPager vp= (ViewPager) container;
        vp.addView(view,0);

        final float scale = context.getResources().getDisplayMetrics().density;
        int pixels = (int) (210 * scale + 0.5f);

        ViewGroup.LayoutParams params = vp.getLayoutParams();
        params.height = pixels;
        vp.setLayoutParams(params);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
