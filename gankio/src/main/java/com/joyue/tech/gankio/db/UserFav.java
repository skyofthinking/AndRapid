package com.joyue.tech.gankio.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * @author JiangYH
 */
@Table(database = AppDatabase.class)
public class UserFav extends BaseModel {

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    @Unique
    public String vin;

    @Column
    public String make;

    @Column
    public String model;

    @Column
    public int year;

}