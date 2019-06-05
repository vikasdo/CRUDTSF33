package com.example.donth.crudtsf;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

        public class showpages extends AppCompatActivity {




            ViewPager mViewPager;
            TabLayout mTabLayout;
            List<Fragment> mFragmentList = new ArrayList<>();
            List<String> mTitleFragmentList = new ArrayList<>();
            private Session s;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_showpages);
                mViewPager=(ViewPager)findViewById(R.id.viewpager);
                Session  s= new Session(getApplicationContext());

                prepareDataResource();
                mTabLayout = findViewById(R.id.tablayout_id);
                CustomAdapter adapter = new CustomAdapter(getSupportFragmentManager(), mFragmentList, mTitleFragmentList);
                mViewPager.setAdapter(adapter);
                mTabLayout.setupWithViewPager(mViewPager);
            }

            private void prepareDataResource() {
                addData(new personaldetails(), "Personal Details");
                addData(new educationfrag(), "Educational Details");
                addData(new profdetfr(), "Professional Details");
            }

            private void addData(Fragment fragment, String title) {
                mFragmentList.add(fragment);
                mTitleFragmentList.add(title);
            }


        }

