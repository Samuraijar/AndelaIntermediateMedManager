package com.example.android.andelaintermediatemedmanager;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.andelaintermediatemedmanager.data.MedData;
import com.example.android.andelaintermediatemedmanager.data.ScheduleDbHelper;

import java.util.List;

import static android.R.attr.handle;

/**
 * Created by NORMAL on 4/17/2018.
 */

public class SearchableActivity extends AppCompatActivity {
    private ListView listView;
    ScheduleDbHelper scheduleDbHelper;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        handleIntent(getIntent());
    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    private void doSearch(String query) {
        Cursor c = scheduleDbHelper.searchWord(query, null);
        SearchAdapter mSearchAdapter = new SearchAdapter(SearchableActivity.this, c);
        listView.setAdapter(mSearchAdapter);
        if (c!=null) {
            mSearchAdapter.swapCursor(c);
        }
    }

}
