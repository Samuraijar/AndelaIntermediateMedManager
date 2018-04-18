package com.example.android.andelaintermediatemedmanager.data;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by NORMAL on 4/15/2018.
 */

public class MedData {

    int ScheduleId;
    String MedDescription, MedInstruction, UsageStatus;



    public int getScheduleId(){
        return ScheduleId;
    }
    public void setScheduleId(int scheduleId) {
        ScheduleId = scheduleId;
    }


    public String getMedDescription(){
        return MedDescription;
    }

    public void setMedDescription(String medDescription) {
        MedDescription = medDescription;
    }

    public String getMedInstruction() {
        return MedInstruction;
    }

    public void setMedInstruction(String medInstruction) {
        MedInstruction = medInstruction;
    }

    public String getUsageStatus() {
        return UsageStatus;
    }

    public void setUsageStatus (String usageStatus) {
        UsageStatus = usageStatus;
    }

    @Override
    public String toString() {
        return "ScheduleId {id-" + ScheduleId + ", MedDescription-" + MedDescription + ", MedInstruction-" +
                MedInstruction + ", UsageStatus-" + UsageStatus + "}";
    }

}
