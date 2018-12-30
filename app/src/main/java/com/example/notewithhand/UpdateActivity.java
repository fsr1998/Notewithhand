package com.example.notewithhand;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {
    String note,time;
    EditText editText;
    MYSQLiteOpenHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Intent intent=getIntent();;
        String tmp = intent.getStringExtra("id");
        int id = Integer.valueOf(tmp);
        dbHelper = new MYSQLiteOpenHelper(this,"NoteBook.db",null,1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("notebook",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            int i=0;
            do{
                if(i == id){
                    note = cursor.getString(cursor.getColumnIndex("note"));
                    time = cursor.getString(cursor.getColumnIndex("time"));
                    break;
                }
                i++;
            }while(cursor.moveToNext());
        }
        editText=(EditText)findViewById(R.id.edit_text);
        editText.setText(note);
        Button btnfinish= (Button) findViewById(R.id.finish_button);
        Button btnforgo=(Button) findViewById(R.id.forgo_button);
        btnfinish.setOnClickListener(this);
        btnforgo.setOnClickListener(this);
        cursor.close();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.finish_button:
                dbHelper = new MYSQLiteOpenHelper(this,"NoteBook.db",null,1);
                note= editText.getText().toString();
                SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
                Date curDate =  new Date(System.currentTimeMillis());
                String time1= formatter.format(curDate);
                Intent intent=new Intent(UpdateActivity.this,MainActivity.class);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                ContentValues values = new ContentValues();
                values.put("note",note);
                values.put("time",time1);
                db.update("notebook",values,"time = ?",new String[]{time});
                dbHelper.close();
                startActivity(intent);
                break;
            case R.id.forgo_button:
                dbHelper.close();
                Intent intent1=new Intent(UpdateActivity.this,MainActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }
}
