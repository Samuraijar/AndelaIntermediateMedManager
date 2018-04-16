package com.example.android.andelaintermediatemedmanager;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.andelaintermediatemedmanager.NotificationHandler.AlarmReceiver;
import com.example.android.andelaintermediatemedmanager.data.MedData;
import com.example.android.andelaintermediatemedmanager.data.ScheduleDbHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    FloatingActionButton addSchedule;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<MedData> medDatas = new ArrayList<>();
    ScheduleDbHelper mDbHelper;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        addSchedule = (FloatingActionButton) findViewById(R.id.imageButton);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        adapter = new ScheduleAdapter(medDatas, getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.accent), getResources().getColor(R.color.colorDivider));
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                refreshList();

            }
        });
        addSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.ui_dialog);
                dialog.show();
                Button saveButton = (Button) dialog.findViewById(R.id.save_button);
                Button cancelButton = (Button) dialog.findViewById(R.id.save_cancel);
                CheckBox checkBox = (CheckBox) dialog.findViewById(R.id.check_box);
                TextView scheduleStatus = (TextView) dialog.findViewById(R.id.status);
                checkBox.setVisibility(View.GONE);
                scheduleStatus.setVisibility(View.GONE);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText medicationDescription = (EditText) dialog.findViewById(R.id.input_medication_description);
                        EditText medicationInstruction = (EditText) dialog.findViewById(R.id.input_medication_notes);
                        if (medicationDescription.getText().length() >= 2) {
                            Spinner timeSpinner = (Spinner) dialog.findViewById(R.id.time_spinner);
                            EditText timeInput = (EditText) dialog.findViewById(R.id.input_medication_time);
                            if (timeSpinner.getSelectedItem().toString().matches("Days") && !(timeInput.getText().toString().matches(""))) {
                                int longtime = Integer.parseInt(timeInput.getText().toString());
                                long miliTime = longtime * 24 * 60 * 60 * 1000;
                                scheduleNotification(miliTime, medicationDescription.getText().toString());

                            } else if (timeSpinner.getSelectedItem().toString().matches("Minutes") && !(timeInput.getText().toString().matches(""))) {
                                int longtime = Integer.parseInt(timeInput.getText().toString());
                                long miliTime = longtime * 60 * 1000;
                                scheduleNotification(miliTime, medicationDescription.getText().toString());
                            } else if (timeSpinner.getSelectedItem().toString().matches("Hours") && !(timeInput.getText().toString().matches(""))) {
                                int longtime = Integer.parseInt(timeInput.getText().toString());
                                long miliTime = longtime * 60 * 60 * 1000;
                                scheduleNotification(miliTime, medicationDescription.getText().toString());

                            }

                            ContentValues cv = new ContentValues();
                            cv.put("MedDescription", medicationDescription.getText().toString());
                            cv.put("MedInstruction", medicationInstruction.getText().toString());
                            cv.put("UsageStatus", "Incomplete");
                            mDbHelper= new ScheduleDbHelper(getApplicationContext());
                            Boolean aBoolean = mDbHelper.insertInto(cv);
                            if (aBoolean) {
                                dialog.hide();
                                refreshList();

                            } else {
                                Toast.makeText(getApplicationContext(), "An error has occured, please try again", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(getApplicationContext(), "Schedule a medication", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    public void scheduleNotification (long time, String ScheduleDescription) {
        Calendar calendar = Calendar.getInstance();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        final int _id = (int) System.currentTimeMillis();
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        intent .putExtra("ScheduleTitle", ScheduleDescription);
        intent.putExtra("id", _id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, _id, intent, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis() + time, pendingIntent);
    }
    public void refreshList(){
        swipeRefreshLayout.setRefreshing(true);
        mDbHelper = new ScheduleDbHelper(getApplicationContext());
        Cursor result = mDbHelper.selectAllData();
        if (result.getCount() == 0) {
            medDatas.clear();
            adapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "No Schedule", Toast.LENGTH_SHORT).show();
        } else {
            medDatas.clear();
            adapter.notifyDataSetChanged();
            while (result.moveToNext()) {
                MedData managerData  = new MedData();
                managerData.setScheduleId(result.getInt(0));
                managerData.setMedDescription(result.getString(1));
                managerData.setMedInstruction(result.getString(2));
                managerData.setUsageStatus(result.getString(3));
                medDatas.add(managerData);
            }
            adapter.notifyDataSetChanged();
        }
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onRefresh() {
        refreshList();
    }



}
