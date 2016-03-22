package com.example.syamkrishnanck.memorygamesample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by syamkrishnanck on 3/17/16.
 */
public class ImageListAdapter extends BaseAdapter {

    List<String> imageUrlList;
    LayoutInflater layoutInflater;
    Context context;


    public ImageListAdapter(Context context, List<String> customizedListView) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageUrlList = customizedListView;
    }

    @Override
    public int getCount() {
        if (imageUrlList != null)
            return imageUrlList.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder listViewHolder;
        if (convertView == null) {
            listViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.image_holder_layout, parent, false);
            listViewHolder.appIcon = (ImageView) convertView.findViewById(R.id.app_icon);
            convertView.setTag(listViewHolder);
        } else {
            listViewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context.getApplicationContext()).load(imageUrlList.get(position)).centerCrop().centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(listViewHolder.appIcon);
        return convertView;
    }

    static class ViewHolder {
        ImageView appIcon;
    }
}
