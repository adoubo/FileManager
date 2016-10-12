package com.caoweixin.filemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.caoweixin.filemanager.R;
import com.caoweixin.filemanager.bean.ItemInfo;

import java.util.List;

/**
 * ListView的适配器
 *
 * Created by caoweixin on 2016/10/11.
 */

public class ListViewAdapter extends BaseAdapter {
    private static final String TAG = "FolderManager.ListViewAdapter";

    private Context context;
    private List<ItemInfo> listItems;

    public ListViewAdapter(Context context, List<ItemInfo> listItems) {
        // TODO Auto-generated constructor stub
        this.context=context;
        this.listItems = listItems;
    }

    // 在此适配器中所代表的数据集的条目数
    @Override
    public int getCount() {
        return listItems.size();
    }

    // 获取数据集中与指定索引对应的数据项
    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    // 获取在列表中与指定索引对应的ID
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 获取一个在数据集中指定索引的视图来显示数据
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        // 如果缓存convertView为空，则需要创建View
        if (convertView == null) {
            viewHolder = new ViewHolder();
            // 根据自定义的list_item布局加载布局
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            viewHolder.title=(TextView) convertView.findViewById(R.id.title);
            viewHolder.time=(TextView) convertView.findViewById(R.id.time);
            viewHolder.img=(ImageView) convertView.findViewById(R.id.img);
            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面取出Tag
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        convert(viewHolder, position);
        return convertView;
    }

    private void convert(ViewHolder holder, int position) {
        ItemInfo itemInfo = listItems.get(position);
        holder.title.setText(itemInfo.getTitle());
        holder.time.setText(itemInfo.getTime());
        holder.img.setImageResource(itemInfo.getImgId());
    }
    // ViewHolder静态类
    private class ViewHolder {
        TextView title;
        TextView time;
        ImageView img;
    }
}
