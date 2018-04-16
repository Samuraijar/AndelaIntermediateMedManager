package com.example.android.andelaintermediatemedmanager.data;

import android.provider.BaseColumns;

/**
 * Created by NORMAL on 4/15/2018.
 */

public class ScheduleContract {
    public static final class ScheduleEntry implements BaseColumns {
        public static final String TABLE_NAME = "MedManager";
        public static final String COLUMN_MED_DESCRIPTION = "MedDescription";
        public static final String COLUMN_MED_INSTRUCTION = "MedInstruction";
        public static final String COLUMN_USAGE_STATUS = "UsageStatus";
        public static final String COLUMN_TIMESTAMP = "TimeStamp";
    }
}
