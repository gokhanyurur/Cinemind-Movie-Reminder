package edu.ktu.cinemind.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.ktu.cinemind.pages.genreslist;
import edu.ktu.cinemind.pages.mostanticipated;
import edu.ktu.cinemind.pages.soonpage;

/**
 * The type Pager adapter.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    /**
     * The number of tabs.
     */
    private int numberOfTabs;

    /**
     * Instantiates a new Pager adapter.
     *
     * @param fm        the fm
     * @param NumOfTabs the num of tabs
     */
    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.numberOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new genreslist();
            case 1:
                return new soonpage();
            case 2:
                return new mostanticipated();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}