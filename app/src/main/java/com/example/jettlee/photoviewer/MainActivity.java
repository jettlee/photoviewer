package com.example.jettlee.photoviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> imagePaths;
    private SingletonPhotoModel singletonPhotoModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = (GridView) findViewById(R.id.photo_grid);

        singletonPhotoModel = SingletonPhotoModel.getInstance();
        imagePaths = singletonPhotoModel.getImagePathArray();

        ImageAdapter imageAdapter = new ImageAdapter(MainActivity.this);
        gridview.setAdapter(imageAdapter);
        imageAdapter.notifyDataSetChanged();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LightBoxActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });

        TextView photoCount = (TextView) findViewById(R.id.photo_count);
        photoCount.setText(imagePaths.size()+" photos");

    }

}
