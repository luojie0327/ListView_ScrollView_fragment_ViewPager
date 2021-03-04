package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.myapplication.FragAdapter;
import com.example.myapplication.R;
import com.example.myapplication.fragment.Fragment0;
import com.example.myapplication.fragment.Fragment1;
import com.example.myapplication.fragment.Fragment2;
import com.example.myapplication.fragment.Fragment3;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager vp = (ViewPager) findViewById(R.id.viewpager);
        //构造适配器
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new Fragment0());
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(adapter);
    }
}
