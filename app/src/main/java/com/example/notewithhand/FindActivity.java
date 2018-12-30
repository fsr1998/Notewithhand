package com.example.notewithhand;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindActivity extends AppCompatActivity  {
    private EditText editText;
    private List<String> list= new ArrayList();//存数据
    final Map m = new HashMap();
    ListView listView=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        Button btnsearch= (Button) findViewById(R.id.search_button);
        Button btnback=(Button) findViewById(R.id.back_button);
        editText = (EditText) findViewById(R.id.edit_text);
        final MYSQLiteOpenHelper dbHelper = new MYSQLiteOpenHelper(this,"NoteBook.db",null,1);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                FindActivity.this,android.R.layout.simple_list_item_1,list);
        listView = (ListView) findViewById(R.id.list_item1);
        listView.setAdapter(adapter);
        //点击事件-更改
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i=new Intent(FindActivity.this,UpdateActivity.class);
                i.putExtra("id",String.valueOf(m.get(position)));
                startActivity(i);//启动第二个activity并把i传递过去
            }
        });
        //按钮-查询
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i1=0,i2=0;
                String note,time,note1;
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.query("notebook",null,null,null,null,null,null);
                //输入信息
                if (cursor.moveToFirst()){
                    list.clear();
                    m.clear();
                    do{//筛选出符合搜索的笔记
                        note = cursor.getString(cursor.getColumnIndex("note"));
                        note1 = editText.getText().toString();
                        if(approximate(note,note1)){//第一个是note，第二个是搜索填入的信息
                            time = cursor.getString(cursor.getColumnIndex("time"));
                            if(note.length() > 36 ){
                                note = note.substring(0,36);
                            }
                            list.add(note+"\n"+time);
                            m.put(i2,i1);
                            i2++;
                        }
                        i1++;
                    }while(cursor.moveToNext());
                }
                cursor.close();
                adapter.notifyDataSetChanged();
                Toast.makeText(FindActivity.this,"查询完成",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //按钮-返回
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(FindActivity.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }
    static public boolean approximate(String s1,String s2){//第一个s1是note，第二个字符串是搜索填入的信息
        if(s1.length() < s2.length()){
            return false;
        }
        if(s1.indexOf(s2)!=-1){
            return true;
        }else {
            return false;
        }
    }
}

