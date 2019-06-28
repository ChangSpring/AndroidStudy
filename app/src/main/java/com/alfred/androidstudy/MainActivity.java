package com.alfred.androidstudy;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    //    @BindView(R.id.checkbox)
//    CheckBox checkBox;
    @BindView(R.id.button)
    Button button;

    @BindView(R.id.view_container)
    TopToBottomFinishLayout viewContainer;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showView();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerViewAdapter());
    }

    private void showView() {
        int viewHeight = viewContainer.getHeight();

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(viewContainer, "translationY", viewHeight, 0);
        objectAnimator.setDuration(2000);
        objectAnimator.start();

    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_layout, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

            myViewHolder.textView.setText("Hello World !!!");
        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

}
