package com.joyue.tech.core.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * @author JiangYH
 * @desc 图片加载
 */
public class ImageLoader {

    public static void with(ImageView imageView, String imageUrl, int defImageView, boolean circle) {
    	if (circle) {
    		Glide.with(imageView.getContext())
                .load(imageUrl)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(defImageView)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .error(defImageView)
                .into(imageView);
    	} else {
    		Glide.with(imageView.getContext())
                .load(imageUrl)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(defImageView)
                .error(defImageView)
                .into(imageView);    		
    	}
    }
    
    public static void with(ImageView imageView, String imageUrl, int defImageView) {
    	with(imageView, imageUrl, defImageView, false);
    }

    public static void with(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

}
