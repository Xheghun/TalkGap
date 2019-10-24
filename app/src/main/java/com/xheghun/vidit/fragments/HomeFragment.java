package com.xheghun.vidit.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.xheghun.vidit.R;
import com.xheghun.vidit.adapter.TabAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tabLayout = view.findViewById(R.id.home_tab_layout);
        viewPager = view.findViewById(R.id.home_viewpager);

        adapter = new TabAdapter(getActivity().getSupportFragmentManager(),0);
        adapter.addFragment(new ChatsFragment(),"Chats");
        adapter.addFragment(new NewsFeedFragment(),"News Feed");
        adapter.addFragment(new CameraFragment(),"Record");
/*
        int[] tabIcons = {
                R.drawable.ic_chat,
                R.drawable.ic_earth,
                R.drawable.ic_camera
        };*/

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(1);
      /*  for (int i = 0; i < tabIcons.length; i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
        }*/
        return view;
    }

}
