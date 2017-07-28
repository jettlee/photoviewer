package com.example.jettlee.photoviewer;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;



public class ImageAdapter extends BaseAdapter {

    private Context context;
    private List coll;

    public ImageAdapter(Context context, List coll){

        super();
        this.context = context;
        this.coll = coll;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowview = inflater.inflate(R.layout.item_photo, parent, false);
        ViewGroup layout = (ViewGroup) rowview.findViewById(R.id.rl_item_photo);
        ImageView imageView = (ImageView) rowview.findViewById(R.id.imageView);

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float dd = dm.density;
        float px = 25*dd;
        float screenWidth = dm.widthPixels;
        int newWidth = (int) (screenWidth-px)/4;

        layout.setLayoutParams(new GridView.LayoutParams(newWidth, newWidth));
        imageView.setId(position);


        /***Bitmap bm = MediaStore.Images.Thumbnails.getThumbnail(context.getApplicationContext()
                        .getContentResolver(), Long.parseLong((String) coll.get(position)),
                MediaStore.Images.Thumbnails.MICRO_KIND, null);
        ***/

        Glide.with(context).load(coll.get(position)).into(imageView);
        //imageView.setImageBitmap(bm);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);



        return rowview;
    }

    @Override
    public int getCount(){
        return coll.size();
    }

    @Override
    public Object getItem(int arg0){
        return coll.get(arg0);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

}
