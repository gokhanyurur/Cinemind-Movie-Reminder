package edu.ktu.cinemind.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.ktu.cinemind.R;
import edu.ktu.cinemind.config.PropertyReader;
import edu.ktu.cinemind.entity.Image;

/**
 * The type View pager adapter.
 */
public class ViewPagerAdapter extends android.support.v4.view.PagerAdapter{

    /**
     * The Context.
     */
    private Context context;

    /**
     * The Images.
     */
    public static List<Image> Images;

    /**
     * Instantiates a new View pager adapter.
     *
     * @param context the context
     */
    public ViewPagerAdapter(Context context){
        this.context=context;
    }


    @Override
    public int getCount() {
        return Images.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            View view = inflater.inflate(R.layout.movieimagesitem, null);
            ImageView imageView = view.findViewById(R.id.movieImages);

            String imgPath = PropertyReader.getProperty("movie.poster.prefix", context) + Images.get(position).getFilePath();

            Picasso.with(context)
                    .load(imgPath)
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
        return null;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
