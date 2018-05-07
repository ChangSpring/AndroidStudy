package com.alfred.androidstudy.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by alfred on 2017/12/11.
 */

@Table(database = AlfredDatabase.class)
public class Student extends BaseModel {
    @PrimaryKey(autoincrement = true)
    public long id;
    @Column
    public String name;
}
