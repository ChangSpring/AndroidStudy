package com.alfred.androidstudy.design;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.alfred.androidstudy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alfred on 2017/4/25.
 */

public class ToolBarActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        ButterKnife.bind(this);

//        mToolbar.setNavigationIcon(android.R.drawable.checkbox_off_background);
//        mToolbar.setLogo(R.mipmap.ic_launcher);
//        mToolbar.setTitle("title");
//        mToolbar.setSubtitle("subTitle");

        mToolbar.inflateMenu(R.menu.base_toolbar_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_search) {
                    Toast.makeText(ToolBarActivity.this, "search", Toast.LENGTH_SHORT).show();
                } else if (menuItemId == R.id.action_notification) {
                    Toast.makeText(ToolBarActivity.this, "notification", Toast.LENGTH_SHORT).show();
                } else if (menuItemId == R.id.action_item1) {
                    Toast.makeText(ToolBarActivity.this, "item1", Toast.LENGTH_SHORT).show();
                } else if (menuItemId == R.id.action_item2) {
                    Toast.makeText(ToolBarActivity.this, "item2", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });
    }
}
