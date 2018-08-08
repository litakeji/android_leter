package com.example.administrator.myapplication.Adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.example.administrator.myapplication.Etity.User;
import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class UserAdapter extends ArrayAdapter<User.Data> {
    List<User.Data> objects;
    private static final String TAG = "ContentAdapter";


    private SparseBooleanArray cb_result;


    public UserAdapter(Context context, int resource, List<User.Data> objects) {
        super(context, resource, objects);
        this.objects = objects;


        cb_result = new SparseBooleanArray();
        for (int i = 0; i < objects.size(); i++) {
            cb_result.put(i, false);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(getContext(), R.layout.item_user1, null);
            viewHolder.tv_item0 = (TextView) convertView.findViewById(R.id.textView);
//            viewHolder.tv_item1 = (TextView) convertView.findViewById(R.id.textView1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.tv_item0.setText(objects.get(position).getName());
//        viewHolder.tv_item1.setText(objects.get(position).getId()+"");
        return convertView;
    }


    @Override
    public int getCount() {
        return objects.size();

    }

    class ViewHolder {
        TextView tv_item0,tv_item1;
    }



    public void refresh(List<User.Data> dds) {
        objects.clear();
        objects.addAll(dds);
        notifyDataSetChanged();
        for (int i = 0; i < dds.size(); i++) {
            cb_result.put(i, false);
        }
    }

    /**
     * 1---全选，2---全不选3---反选
     *
     * @param state
     */
    public void setCheckBoxState(int state) {
        if (cb_result != null && cb_result.size() != 0) {
            int size = cb_result.size();
            switch (state) {
                case 1:
                    for (int i = 0; i < size; i++) {
                        cb_result.put(i, true);
                    }
                    break;
                case 2:
                    for (int i = 0; i < size; i++) {
                        cb_result.put(i, false);
                    }
                    break;
                case 3:
                    for (int i = 0; i < size; i++) {
                        Boolean flag = cb_result.valueAt(i);
                        if (flag != null) {
                            cb_result.put(i, !flag);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        notifyDataSetChanged();
    }


    /**
     * 获取选中结果
     *
     */
    public List<User.Data> getCbsResult() {
        List<User.Data> users = new ArrayList<User.Data>();
        int size = cb_result.size();
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                if (cb_result.valueAt(i)) {
                    users.add(objects.get(cb_result.keyAt(i)));
                }
            }
        }
        return users;
    }
}
