package com.nostra13.example.changhong;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.changhong_practice.imageloaderforfile.core.ImageLoader;
import com.nostra13.example.universalimageloader.R;

import java.util.List;

/**
 * Created by changhong on 14-3-28.
 */
public class HomeAdapter extends BaseAdapter {

    List<Aulbum> mAll;
    Activity mcontext;
    ImageLoader loader;
    public HomeAdapter(List<Aulbum> all,Activity context) {
        mAll = all;
        mcontext = context;
        loader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return mAll.size();
    }

    @Override
    public Object getItem(int position) {
        return mAll.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View view = convertView;
        if (view == null) {
            view = mcontext.getLayoutInflater().inflate(R.layout.changhong_homegrid_item,
                    parent, false);
            holder = new ViewHolder();
            assert view != null;
            holder.imageView = (ImageView) view.findViewById(R.id.image);
            holder.title = (TextView) view.
                    findViewById(R.id.title);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Image image = mAll.get(position).firstImage;
        loader.displayImage(image.path,
                holder.imageView, null, false);
        holder.title.setText(image.displayTitle);
        return view;
    }

    class ViewHolder {
        ImageView imageView;
        TextView title;
    }
}
