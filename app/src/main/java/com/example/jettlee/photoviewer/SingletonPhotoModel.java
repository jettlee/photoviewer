package com.example.jettlee.photoviewer;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;


public class SingletonPhotoModel{
    private List<String> imagepath;
    private List<String> thumb_ids;
    private static SingletonPhotoModel instance;
    private Context context;

    public static SingletonPhotoModel getInstance(){
        if(instance==null){
            instance = new SingletonPhotoModel();
        }
        return instance;
    }

    private SingletonPhotoModel(){
        this.context = App.getContext();
        imagepath = new ArrayList<String>();
        thumb_ids = new ArrayList<String>();
        getImageIdandPaths(imagepath, thumb_ids);
    }

    /***public static void init(Context mContext){
        context = mContext;
    }***/

    public List<String> getImagePathArray(){
        return imagepath;
    }

    public List<String> getThumb_idsArray(){
        return thumb_ids;
    }

    public void getImageIdandPaths(List<String> filepath, List<String> id){
        try{
            final String[] projection = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
            Log.d("cursor", "before cursor created");

            Cursor imagecursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
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
                }
            }
            imagecursor.close();
        }catch(Exception e){
            e.printStackTrace();
            Log.d("Exception", "BOO");
        }
    }
}

