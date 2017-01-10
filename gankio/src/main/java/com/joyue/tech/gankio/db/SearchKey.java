package com.joyue.tech.gankio.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * @author JiangYH
 */
@Table(database = AppDatabase.class)
public class SearchKey extends BaseModel {

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public String key; // 搜索关键字

    @Column
    public String count; //  搜索次数

}