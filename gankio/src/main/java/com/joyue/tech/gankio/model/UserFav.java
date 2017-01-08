package com.joyue.tech.gankio.model;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;

import javax.annotation.Nullable;

/**
 * @author JiangYH
 */
@AutoValue
public abstract class UserFav implements UserFavModel {
    public static final Factory<UserFav> FACTORY = new Factory<>(new Creator<UserFav>() {

        @Override
        public UserFav create(@Nullable Long id, @NonNull String name) {
            return new AutoValue_UserFav(id, name);
        }
    });

    public static final RowMapper<UserFav> SELECT_ALL_MAPPER = FACTORY.get_allMapper();
}