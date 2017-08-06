package com.example.jettlee.photoviewer;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;


public class ViewPagerAdapter extends PagerAdapter{
    private List<String> mPaths;
    private Context mContext;
    private LayoutInflater inflater;

    public ViewPagerAdapter(Context c){
        this.mContext = c;
        this.mPaths = SingletonPhotoModel.getInstance().getImagePathArray();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        ImageView iv;
        if(container==null){
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_photo, null);
            iv = (ImageView) view.findViewById(R.id.imageView);
        }else{
            iv = new ImageView(mContext);
        }
        Glide.with(mContext).load(mPaths.get(position)).into(iv);
        container.addView(iv,0);
        return iv;
    }

    @Override
    public int getCount(){
        return mPaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj){
        return view == (View) obj;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((View) object);
    }
}
