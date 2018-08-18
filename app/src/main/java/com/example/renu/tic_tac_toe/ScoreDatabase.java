package com.example.renu.tic_tac_toe;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.HashMap;

/**
 * Created by renu on 11/08/16.
 */


public class ScoreDatabase extends ContentProvider {

    static final String PROVIDER_NAME = "com.example.renu.tic_tac_toe";
    static final String URL = "content://" + PROVIDER_NAME + "/students";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String NAME = "name";

    private static HashMap<String, String> STUDENTS_PROJECTION_MAP;

    static final int STUDENTS = 1;

    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "students", STUDENTS);

    }

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "College";
    static final String STUDENTS_TABLE_NAME = "students";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + STUDENTS_TABLE_NAME + " ( name TEXT NOT NULL);";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + STUDENTS_TABLE_NAME);
            onCreate(db);
        }

    }
        @Override
        public boolean onCreate() {
            Context context = getContext();
            DatabaseHelper dbHelper = new DatabaseHelper(context);

            db = dbHelper.getWritableDatabase();
            return (db == null)? false:true;

        }

        @Nullable
        @Override
        public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
            qb.setTables(STUDENTS_TABLE_NAME);

            switch (uriMatcher.match(uri)) {
                case STUDENTS:
                    qb.setProjectionMap(STUDENTS_PROJECTION_MAP);
                    break;



                default:
                    throw new IllegalArgumentException("Unknown URI " + uri);
            }
            if (sortOrder == null || sortOrder == ""){
                sortOrder = NAME;
            }
            Cursor c = qb.query(db,	projection,	selection, selectionArgs,null, null, sortOrder);
            c.setNotificationUri(getContext().getContentResolver(), uri);
            return c;
        }

        @Nullable
        @Override
        public String getType(Uri uri) {
            switch (uriMatcher.match(uri)){

                case STUDENTS:
                    return "vnd.android.cursor.dir/vnd.example.students";



                default:
                    throw new IllegalArgumentException("Unsupported URI: " + uri);
            }
        }

        @Nullable
        @Override
        public Uri insert(Uri uri, ContentValues values) {

            long rowID = db.insert(	STUDENTS_TABLE_NAME, "", values);


            if (rowID > 0)
            {
                Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
                getContext().getContentResolver().notifyChange(_uri, null);
                return _uri;
            }
            try {
                throw new SQLException("Failed to add a record into " + uri);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return uri;
        }

        @Override
        public int delete(Uri uri, String selection, String[] selectionArgs) {
            int count = 0;

            switch (uriMatcher.match(uri)){
                case STUDENTS:
                    count = db.delete(STUDENTS_TABLE_NAME, selection, selectionArgs);
                    break;



                default:
                    throw new IllegalArgumentException("Unknown URI " + uri);
            }

            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }

        @Override
        public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
            int count = 0;

            switch (uriMatcher.match(uri)){
                case STUDENTS:
                    count = db.update(STUDENTS_TABLE_NAME, values, selection, selectionArgs);
                    break;



                default:
                    throw new IllegalArgumentException("Unknown URI " + uri );
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
    }

