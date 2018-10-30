package com.admin.shopkeeper.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zeng on 2016/11/7.
 * MainActivity ViewPager adapter
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    private List<Fragment> mFragments;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> mFragments) {
        super(fm);

        this.mFragments = mFragments;
    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }
}
