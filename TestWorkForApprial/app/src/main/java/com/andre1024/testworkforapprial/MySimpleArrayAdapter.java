package com.andre1024.testworkforapprial;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
/**
 * Created by An on 29.10.2015.
 */
public class MySimpleArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private ArrayList<ModelFriend> friendArrayList;
    private final ArrayList<ModelFriend> originalFriendArrayList = new ArrayList<ModelFriend>(); // Cписок, который будет хранить всегда, всеx найденых друзей

    public MySimpleArrayAdapter(Context context, ArrayList<ModelFriend> list) {
        super(context, 0);
        this.context = context;
        this.friendArrayList = list;
        originalFriendArrayList.addAll(list);
    }

    static class ViewHolder {
        public LinearLayout layout;
        public TextView fullName;
        public ImageView avatar;
    }

    @Override
    public int getCount() {
        return friendArrayList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        View rowView = convertView;

        if (rowView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            rowView = inflater.inflate(R.layout.main_row, null, true);
            holder.fullName = (TextView) rowView.findViewById(R.id.textView);
            holder.layout = (LinearLayout) rowView.findViewById(R.id.linearLayout);
            holder.avatar = (ImageView) rowView.findViewById(R.id.imageView);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        holder.fullName.setText(friendArrayList.get(position).getFullName());

        Glide.with(context)
                .load(friendArrayList.get(position).getPhoto_50())
                .transform(new TransformCircle(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.avatar);
        return rowView;
    }

    /**
     *  Поиск.
     *  Ищем вхождения query в каждом элементе из списка, если такое есть,
     *  значит добавляем для нового отображения.
     *  Так формируем новый список для вывода.
     * @param query строка для поиска
     */
    public void filterData(String query) {
        query = query.toLowerCase();
        friendArrayList.clear();
        if (query.isEmpty()) {
            friendArrayList.addAll(originalFriendArrayList);
        } else {
            for (ModelFriend f : originalFriendArrayList) {
                if (f.getFullName().toLowerCase().contains(query)) {
                    friendArrayList.add(f);
                }
            }
        }
        notifyDataSetChanged();
    }
}