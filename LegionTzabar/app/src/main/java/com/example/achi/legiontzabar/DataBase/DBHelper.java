package com.example.achi.legiontzabar.DataBase;

/**
 * Created by kiri on 04/02/2018.
 */

public class DBHelper {

    public static final String DATABASE_NAME = "appDB.db";
    public static final int DATABASE_VESTION = 1;

    public static final int SOLDIERS_TABLE_WIDTH = 15;

    public static final String SOLDIERS_TABLE = "soldiers_table";
    public static final String SOLDIERS_TABLE_COL_MID = "mid";
    public static final String SOLDIERS_TABLE_COL_PID = "pid";
    public static final String SOLDIERS_TABLE_COL_FIRST_NAME = "first_name";
    public static final String SOLDIERS_TABLE_COL_LAST_NAME = "last_name";
    public static final String SOLDIERS_TABLE_COL_GENDER = "gender";
    public static final String SOLDIERS_TABLE_COL_PHONE_NUMBER = "phone_number";
    public static final String SOLDIERS_TABLE_COL_EMAIL = "email";
    public static final String SOLDIERS_TABLE_COL_ADDRESS = "address";
    public static final String SOLDIERS_TABLE_COL_SERVICE_DAYS = "service_days";
    public static final String SOLDIERS_TABLE_COL_RANK = "rank";
    public static final String SOLDIERS_TABLE_COL_OCCUPATION = "occupation";
    public static final String SOLDIERS_TABLE_COL_IN_MATZEVA = "in_matzeva";
    public static final String SOLDIERS_TABLE_COL_COMPANY = "company";
    public static final String SOLDIERS_TABLE_COL_MILITARY_OCCUPATION = "military_occupation";
    public static final String SOLDIERS_TABLE_COL_IMAGE = "soldier_image";


    public static final int QUALIFICATION_TABLE_WIDTH = 5;

    public static final String QUALIFICATION_TABLE = "course_table";
    public static final String QUALIFICATION_TABLE_COL_MID = "mid";
    public static final String QUALIFICATION_TABLE_COL_QID = "qid";
    public static final String QUALIFICATION_TABLE_COL_NAME = "name";
    public static final String QUALIFICATION_TABLE_COL_START_DATE = "start_date";
    public static final String QUALIFICATION_TABLE_COL_DURATION = "duration";

    public static final String EVENTS_TABLE = "events_table";
    public static final String EVENTS_TABLE_ID = "id";
    public static final String EVENTS_TABLE_KIND = "kind";
    public static final String EVENTS_TABLE_START_DATE = "start_date";
    public static final String EVENTS_TABLE_PERIOD = "period";


    public static final String FILE_TABLE = "file_table";
    public static final String FILE_TABLE_EVENT_ID = "eid";
    public static final String FILE_TABLE_FILE1_BLOB = "path1";
    public static final String FILE_TABLE_FILE2_BLOB = "path2";
    public static final String FILE_TABLE_FILE3_BLOB = "path3";


    public static final String PARTICIPANTS_TABLE = "participants_table";
    public static final String PARTICIPANTS_TABLE_EVENT_ID = "event_id";
    public static final String PARTICIPANTS_TABLE_MILITARY_ID = "military_id";








}
