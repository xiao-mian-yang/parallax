package com.itcast.cn.parallax;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.listview)
    parallaxListView listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //去掉蓝色的模糊的背景
        listview.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
        View headView = View.inflate(this, R.layout.layout_header, null);
        ImageView parallaxImage = (ImageView) headView.findViewById(R.id.parallaxImage);
        listview.addHeaderView(headView);
        listview.setParallaxImage(parallaxImage);
        listview.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Constants.NAMES));

    }
}
