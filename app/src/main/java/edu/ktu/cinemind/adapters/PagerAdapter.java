package edu.ktu.cinemind.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.ktu.cinemind.pages.genreslist;
import edu.ktu.cinemind.pages.mostanticipated;
import edu.ktu.cinemind.pages.soonpage;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                genreslist tab1 = new genreslist();
                return tab1;
            case 1:
                soonpage tab2 = new soonpage();
                return tab2;
            case 2:
                mostanticipated tab3 = new mostanticipated();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}