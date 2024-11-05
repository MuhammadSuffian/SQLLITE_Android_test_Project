package com.example.lab5;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button insert;
    Button delete;
    Button update;
    Button view;
    Button viewAll;
    EditText rollno;
    EditText name;
    EditText marks;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseLogic();
        initliztion();
        buttonLogic();
    }
    void databaseLogic(){
        db=openOrCreateDatabase("StudnetDatabase", Context.MODE_PRIVATE,null);
//        db.delete("student",null,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno VARCHAR, name VARCHAR, marks VARCHAR)");
    }
    void initliztion(){
        insert=findViewById(R.id.btn_insert);
        delete=findViewById(R.id.btn_delete);
        view=findViewById(R.id.btn_view);
        viewAll=findViewById(R.id.btn_view_all);
        update=findViewById(R.id.btn_update);
        rollno=findViewById(R.id.tv_roll_no_input);
        name=findViewById(R.id.tv_name_input);
        marks=findViewById(R.id.tv_marks_input);
    }
    void buttonLogic(){
        insert.setOnClickListener(b->{
            if(rollno.getText().toString().isEmpty() || name.getText().toString().isEmpty() || marks.getText().toString().isEmpty()){
                Toast.makeText(MainActivity.this,"Please Record in all fields",Toast.LENGTH_SHORT).show();
        }
            else{
                    try{
                        db.execSQL("INSERT INTO student VALUES('" + rollno.getText().toString() + "','" + name.getText().toString() + "','" + marks.getText().toString() + "')");
                        Toast.makeText(MainActivity.this,"Rcords added Successfully",Toast.LENGTH_SHORT).show();
                        rollno.setText("");
                        name.setText("");
                        marks.setText("");
                    }
                    catch (Exception e){
                        Toast.makeText(MainActivity.this,"Rcords added Failed",Toast.LENGTH_SHORT).show();

                    }
                }
        });
        delete.setOnClickListener(b->{
            if(rollno.getText().toString().isEmpty() ){
                Toast.makeText(MainActivity.this,"Please Enter Roll Number",Toast.LENGTH_SHORT).show();
            }
            else{
                try{
                    Cursor c=db.rawQuery("SELECT * from student where rollno="+rollno.getText().toString(),null);
                    if(c.moveToFirst()){
                        db.execSQL("DELETE FROM student Where rollno="+rollno.getText().toString());
                        Toast.makeText(MainActivity.this,"Rcords Deleted Successfully",Toast.LENGTH_SHORT).show();
                        rollno.setText("");
                        name.setText("");
                        marks.setText("");
                    }
                    else{
                        Toast.makeText(MainActivity.this,"Rcords Deleted Failed",Toast.LENGTH_SHORT).show();

                    }

                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this,"Exception:"+e,Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.setOnClickListener(b->{
            if(rollno.getText().toString().isEmpty() ){
                Toast.makeText(MainActivity.this,"Please Enter Roll Number",Toast.LENGTH_SHORT).show();
            }
            else{
                try{
                    Cursor c=db.rawQuery("SELECT * from student where rollno="+rollno.getText().toString(),null);
                    if(c.moveToFirst()){
                        name.setText(c.getString(1));
                        marks.setText(c.getString(2));
                    }
                    else{
                        Toast.makeText(MainActivity.this,"Rcords not found",Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this,"Exception:"+e,Toast.LENGTH_SHORT).show();
                }
            }
        });
        update.setOnClickListener(b->{
            if(rollno.getText().toString().isEmpty() || name.getText().toString().isEmpty() || marks.getText().toString().isEmpty()){
                Toast.makeText(MainActivity.this,"Please fill All fields",Toast.LENGTH_SHORT).show();
            }
            else{
                try{
                    Cursor c=db.rawQuery("SELECT * from student where rollno="+rollno.getText().toString(),null);
                    if(c.moveToFirst()){
                      db.execSQL("Update students set name='"+name.getText().toString()+"',marks='"+marks.getText().toString()+"' where rollno="+rollno.getText().toString());
                    }
                    else{
                        Toast.makeText(MainActivity.this,"Rcords not updated",Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this,"Exception:"+e,Toast.LENGTH_SHORT).show();
                }
            }
        });
}
    }