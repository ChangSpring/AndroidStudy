package com.alfred.androidstudy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.button)
    Button button;

    private static final String TAG = MainActivity.class.getSimpleName();


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"onCreate");

        ButterKnife.bind(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PdfPreviewActivity.class));
//                hello();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"OnStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestory");
    }

    private void hello() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();

//                Looper.prepare();
//                Handler handler = new Handler() {
//                    @Override
//                    public void handleMessage(Message msg) {
//                        super.handleMessage(msg);
//                        Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();
//
//                    }
//                };
//                handler.sendEmptyMessage(1);
//                Looper.loop();
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");


//        handler.sendEmptyMessageDelayed(0, 3000);
    }
}
