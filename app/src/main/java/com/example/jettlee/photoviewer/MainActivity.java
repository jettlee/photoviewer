package com.example.jettlee.photoviewer;

import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> imagePaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = (GridView) findViewById(R.id.photo_grid);
        imagePaths = new ArrayList<String>();
        List<String> thumb_id = new ArrayList<String>();
        getImageIdandPaths(imagePaths, thumb_id);

        ImageAdapter imageAdapter = new ImageAdapter(MainActivity.this, imagePaths);
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

        TextView photoCount = (TextView) findViewById(R.id.photo_count);
        photoCount.setText(imagePaths.size()+" photos");

    }

    private void getImageIdandPaths(List<String> filepath, List<String> id){
        try{
            final String[] projection = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
            Log.d("cursor", "before cursor created");
            Cursor imagecursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, null, null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");

            Log.d("cursor", "after cursor created");
            if(imagecursor!=null){
                imagecursor.moveToFirst();
                while(imagecursor.moveToNext()){
                    int dataColumnIndex = imagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    int dataIdIndex = imagecursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
                    String picPath = imagecursor.getString(dataColumnIndex);
                    int picId = imagecursor.getInt(dataIdIndex);
                    if(picPath!=null){
                        filepath.add(picPath);
                        id.add(picId+"");
                    }
                    if(filepath.size()==5) break;
                }
            }
            imagecursor.close();
        }catch(Exception e){
            e.printStackTrace();
            Log.d("Exception", "BOO");
        }
    }


}
