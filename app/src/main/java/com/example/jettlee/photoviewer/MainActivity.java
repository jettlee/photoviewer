package com.example.jettlee.photoviewer;

import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView gridview;
    private List<String> imagePaths;
    private ImageAdapter imageAdapter;
    private TextView photoCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridview = (GridView) findViewById(R.id.photo_grid);
        imagePaths = getImagePaths();

        imageAdapter = new ImageAdapter(MainActivity.this, imagePaths);
        gridview.setAdapter(imageAdapter);
        imageAdapter.notifyDataSetChanged();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String lightbox_filepath = imagePaths.get(position);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LightBoxActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("filepath", lightbox_filepath);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        photoCount = (TextView) findViewById(R.id.photo_count);
        photoCount.setText(imagePaths.size()+" photos");

    }

    private List<String> getImagePaths(){
        List<String> imagePathList = new ArrayList<String>();
        try{
            final String[] projection = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
            final String orderBy = MediaStore.Images.Media.DATE_ADDED;

            Cursor imagecursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, null, null, orderBy+" DESC");

            if(imagecursor!=null){
                while(imagecursor.moveToNext()){
                    int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    String picPath = imagecursor.getString(dataColumnIndex);
                    if(picPath!=null){
                        imagePathList.add(picPath);
                    }
                }
            }
            imagecursor.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return imagePathList;
    }


}
