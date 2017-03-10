package com.alfred.androidstudy.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alfred.androidstudy.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alfred on 2017/2/17.
 */

public class ScrollListViewAdaper extends BaseAdapter {
    private Context mContext;
    private List<DataHolder> dataList = new ArrayList<>();

    public ScrollListViewAdaper(Context context) {
        this.mContext = context;
        for (int i = 0; i < 20; i++) {
            DataHolder holder = new DataHolder();
            holder.title = "第" + i + "项";
            dataList.add(holder);
        }
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_scroll_list_view, parent, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DataHolder dataHolder = dataList.get(position);
        viewHolder.titleTv.setText(dataHolder.title);

        dataHolder.rootLl = (ScrollItemView) convertView.findViewById(R.id.root_item);
        viewHolder.rootLl.scrollTo(0, 0);
        return convertView;
    }

    public class ViewHolder {
        @Bind(R.id.root_item)
        LinearLayout rootLl;
        @Bind(R.id.title_tv)
        TextView titleTv;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public class DataHolder {
        public String title;
        public ScrollItemView rootLl;
    }
}
