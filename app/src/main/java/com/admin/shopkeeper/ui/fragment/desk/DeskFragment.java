package com.admin.shopkeeper.ui.fragment.desk;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseFragment;
import com.admin.shopkeeper.entity.RoomEntity;
import com.admin.shopkeeper.ui.fragment.table.TableFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeskFragment extends BaseFragment<DeskPresenter> implements IDeskView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    @BindView(R.id.viewPagerTab)
    SmartTabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;


    public static DeskFragment newInstance(String param1, String param2) {
        DeskFragment fragment = new DeskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_desk;
    }

    @Override
    protected void initPresenter() {
        presenter = new DeskPresenter(getActivity(), this);
        presenter.init();
    }

    @Override
    public void initView() {
        presenter.getTabelDB();
    }


    @Override
    public void showDB(List<RoomEntity> roomEntities) {
        FragmentPagerItems pages = new FragmentPagerItems(getActivity());
        for (int i = 0; i < roomEntities.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putString(TableFragment.ARG_PARAM1, roomEntities.get(i).getId());
            pages.add(FragmentPagerItem.of(
                    roomEntities.get(i).getName(),
                    TableFragment.class,
                    bundle
            ));
        }

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getActivity().getSupportFragmentManager(), pages);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(pages.size());
        mTabLayout.setViewPager(viewPager);

    }


}
