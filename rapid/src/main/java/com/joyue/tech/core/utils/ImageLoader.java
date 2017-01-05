package com.joyue.tech.core.utils;

import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * @author JiangYH
 * @desc 图片加载
 */
public class ImageLoader {

    public static void with(ImageView imageView, String imageUrl, int defImageView, boolean circle, boolean centerCrop) {
        DrawableRequestBuilder drawableRequestBuilder = Glide.with(imageView.getContext()).load(imageUrl);
        drawableRequestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE);
        drawableRequestBuilder.placeholder(defImageView);
        drawableRequestBuilder.error(defImageView);

        if (centerCrop) {
            drawableRequestBuilder.centerCrop();
        }

        if (circle) {
            drawableRequestBuilder.transform(new GlideCircleTransform(imageView.getContext()));
        }

        drawableRequestBuilder.into(imageView);
    }

    public static void with(ImageView imageView, String imageUrl, int defImageView, boolean centerCrop) {
        with(imageView, imageUrl, defImageView, false, centerCrop);
    }

    public static void with(ImageView imageView, String imageUrl) {
        DrawableRequestBuilder drawableRequestBuilder = Glide.with(imageView.getContext()).load(imageUrl);
        drawableRequestBuilder.diskCacheStrategy(DiskCacheStrategy.ALL);
        // drawableRequestBuilder.centerCrop();
        drawableRequestBuilder.into(imageView);
    }

}
