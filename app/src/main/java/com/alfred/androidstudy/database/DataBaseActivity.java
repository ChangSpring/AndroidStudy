package com.alfred.androidstudy.database;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alfred.androidstudy.R;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DataBaseActivity extends AppCompatActivity {
    @BindView(R.id.textview)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);

        ButterKnife.bind(this);

//        Student student = new Student();
//        student.name = "Alfred";
//        student.save();
//
//        student.name = "zhangsan";
//        student.save();

//        List<Student> studentList = SQLite.select()
//                .from(Student.class)
//                .orderBy(Student_Table.id, true)
//                .queryList();
//
//        SQLite.update(Student.class)
//                .set(Student_Table.name.eq("Alfred2"))
//                .where(Student_Table.name.eq("hello"))
//                .execute();
//
//        StringBuilder builder = new StringBuilder();
//        for (Student student1 : studentList) {
//            builder.append("id = " + student1.id + " name = " + student1.name + "\n");
//        }
//
//        mTextView.setText(builder);

        Teacher teacher = new Teacher();
        teacher.name = "lisi";
        teacher.save();

        mTextView.setText(SQLite.select().from(Teacher.class).querySingle().name);

    }
}
