package com.road.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.road.ui.fragment.Test1Fragment;
import com.road.ui.fragment.Test2Fragment;
import com.road.ui.fragment.Test3Fragment;

import java.util.List;

/**
 * Created by zhou on 2016/6/1.
 */
public class HomeFragmentAdapter extends FragmentStatePagerAdapter {

    private List<String> mTitles;

    public HomeFragmentAdapter(FragmentManager fm, List<String> mTitles) {
        super(fm);
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = Test1Fragment.newInstance();
                break;
            case 1:
                fragment = Test2Fragment.newInstance();
                break;
            case 2:
                fragment = Test3Fragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    /**
     * 联动 TabLayout 上的 Title
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

}
