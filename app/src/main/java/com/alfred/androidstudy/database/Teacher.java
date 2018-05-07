package com.alfred.androidstudy.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by alfred on 2017/12/11.
 */

@Table(database = AlfredDatabase.class)
public class Teacher extends BaseModel {
    @PrimaryKey(autoincrement = true)
    public long id;
    @Column
    public String name;
    @Column
    public int age;

    @Migration(version = 3,database = AlfredDatabase.class)
    public static class Migration3 extends AlterTableMigration<Teacher>{

        public Migration3(Class<Teacher> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.INTEGER,"age");
        }

        @Override
        public void onPostMigrate() {
            super.onPostMigrate();
        }
    }
}
