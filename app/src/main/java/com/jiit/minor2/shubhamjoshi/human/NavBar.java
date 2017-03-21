package com.jiit.minor2.shubhamjoshi.human;

import android.animation.Animator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.jiit.minor2.shubhamjoshi.human.Adapters.GetIp;
import com.jiit.minor2.shubhamjoshi.human.App.QRCode;
import com.jiit.minor2.shubhamjoshi.human.CustomDrawer.FullDrawerLayout;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.hendraanggrian.circularrevealanimator.CircularRevealAnimator;

import static android.content.Context.MODE_PRIVATE;

public class NavBar extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FullDrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public void setUp(FullDrawerLayout drawerLayout,Toolbar toolbar)
    {
        mDrawerLayout= drawerLayout;
        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.open,R.string.close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
    }
    public NavBar() {
        // Required empty public constructor
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
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v =  inflater.inflate(R.layout.fragment_nav_bar, container, false);
        ImageView image = (ImageView) v.findViewById(R.id.ip_tag);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),QRCode.class));
               // CircularRevealAnimator.of(getActivity()).startActivity(new Intent(container.getContext(), GetIp.class), R.id.ip_tag);

            }
        });
        CircleImageView fb = (CircleImageView)v.findViewById(R.id.userImg);

        SharedPreferences prefs = this.getActivity().getSharedPreferences("EMAIL", MODE_PRIVATE);
        String pImage = prefs.getString("ProfileImage", null);
        Log.e("Profile image ",pImage);
        if (pImage!= null) {
            Picasso.with(v.getContext()).load(pImage).into(fb);
        }



       return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CircularRevealAnimator.of(getActivity()).reveal(R.id.head,0,0);
    }
}
