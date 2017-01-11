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
    public long count; // 搜索次数

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}