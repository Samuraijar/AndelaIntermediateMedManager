package com.example.android.andelaintermediatemedmanager;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.andelaintermediatemedmanager.data.MedData;
import com.example.android.andelaintermediatemedmanager.data.ScheduleDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NORMAL on 4/15/2018.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    List<MedData> scheduleArraList = new ArrayList<MedData>();
    Context context;

    public ScheduleAdapter(String details) {
        MedData mMedData = new MedData();
        mMedData.setMedDescription(details);
        scheduleArraList.add(mMedData);
    }

    public ScheduleAdapter(ArrayList<MedData> medManagerDataArrayList, Context context) {
        this.scheduleArraList = medManagerDataArrayList;
        this.context = context;
    }


    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        ScheduleViewHolder viewHolder = new ScheduleViewHolder(view, context);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, final int position) {

        final MedData medData = scheduleArraList.get(position);
        holder.scheduleDetails.setText(medData.getMedDescription());
        holder.scheduleNotes.setText(medData.getMedInstruction());
        String scheduleStatus = medData.getUsageStatus();
        if (scheduleStatus.matches("Complete")) {
            holder.scheduleDetails.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = medData.getScheduleId();
                ScheduleDbHelper scheduleDbHelper = new ScheduleDbHelper(v.getContext());
                Cursor cursor = scheduleDbHelper.deleteSchedule(id);
                if (cursor.getCount() == 0) {
                    Toast.makeText(v.getContext(), R.string.delete, Toast.LENGTH_SHORT).show();
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {

                            scheduleArraList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, scheduleArraList.size());
                            notifyDataSetChanged();

                        }
                    });
                } else {
                    Toast.makeText(v.getContext(), R.string.deletedelse, Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.ui_dialog);
                dialog.show();
                EditText scheduleDescription = (EditText) dialog.findViewById(R.id.input_medication_description);
                EditText scheduleNote = (EditText) dialog.findViewById(R.id.input_medication_notes);
                CheckBox checkBox = (CheckBox) dialog.findViewById(R.id.check_box);
                LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.linear_layout);
                TextView tv = (TextView) dialog.findViewById(R.id.notification);
                tv.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                if (medData.getMedDescription().matches("Complete")) {
                    checkBox.setChecked(true);
                }
                scheduleDescription.setText(medData.getMedDescription());
                scheduleNote.setText(medData.getMedInstruction());
                Button saveButton = (Button) dialog.findViewById(R.id.save_button);
                Button cancelButton = (Button) dialog.findViewById(R.id.save_cancel);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText scheduleDescription = (EditText) dialog.findViewById(R.id.input_medication_description);
                        EditText scheduleNote = (EditText) dialog.findViewById(R.id.input_medication_notes);
                        CheckBox checkBox = (CheckBox) dialog.findViewById(R.id.check_box);
                        if (scheduleDescription.getText().length() >= 2) {
                            MedData saveId = new MedData();
                            saveId.setScheduleId(medData.getScheduleId());
                            saveId.setMedDescription(scheduleDescription.getText().toString());
                            saveId.setMedInstruction(scheduleNote.getText().toString());
                            if (checkBox.isChecked()) {
                                saveId.setUsageStatus(String.valueOf(R.string.completed));
                            } else {
                                saveId.setUsageStatus(String.valueOf(R.string.incomplete));
                            }
                            ScheduleDbHelper helper = new ScheduleDbHelper(v.getContext());
                            Cursor cursor = helper.updateSchedule(saveId);
                            scheduleArraList.set(position, saveId);
                            if (cursor.getCount() == 0) {
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        notifyDataSetChanged();
                                    }
                                });
                                dialog.hide();
                            } else {
                                dialog.hide();
                            }

                        } else {
                            Toast.makeText(v.getContext(), R.string.makeschedule, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

    }

    @Override
    public int getItemCount() {
        return scheduleArraList.size();
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder {

        TextView scheduleDetails, scheduleNotes;
        ImageView edit, deleteButton;
        MedData medData;

        public ScheduleViewHolder(View itemView, Context context) {
            super(itemView);
            scheduleDetails = (TextView) itemView.findViewById(R.id.schedule_text_details);
            scheduleNotes = (TextView) itemView.findViewById(R.id.schedule_text_notes);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            deleteButton = (ImageView) itemView.findViewById(R.id.delete);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
