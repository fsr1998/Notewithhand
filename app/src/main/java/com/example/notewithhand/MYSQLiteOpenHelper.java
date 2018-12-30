package com.example.notewithhand;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MYSQLiteOpenHelper extends SQLiteOpenHelper {
    public static final String CREATE_NoteBook = "create table notebook ("
            +"note text,"
            +"time text)";
    private Context mContext;
    public MYSQLiteOpenHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory,int version){
        super(context, name, factory, version);
        mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_NoteBook);
        Toast.makeText(mContext,"Create succeeded",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
    }
}
