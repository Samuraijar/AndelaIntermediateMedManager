package com.example.android.andelaintermediatemedmanager;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.andelaintermediatemedmanager.data.ScheduleContract;

import java.util.List;

import static android.R.id.list;

/**
 * Created by NORMAL on 4/18/2018.
 */

public class SearchAdapter extends CursorAdapter {
    private LayoutInflater layoutInflater;



    public SearchAdapter(Context context, Cursor c) {
        super(context, c);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_list,parent,false);
        ViewHolder holder = new ViewHolder();
        holder.word = (TextView) view.findViewById(R.id.list_item_search);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.word.setText(cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleEntry.COLUMN_MED_DESCRIPTION)));

    }

    static class ViewHolder {
        TextView word;
    }
}
