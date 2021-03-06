package com.example.productbrowser;


import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import backend.Database;

import java.sql.Time;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    Database myDatabase;
    EditText editDate, editSugar, editRecord;
    Button addData,viewData, viewDayData;
    CalendarView calendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDatabase = new Database(this);
        editDate = findViewById(R.id.editText1);
        editSugar = findViewById(R.id.editText2);
        editRecord = findViewById(R.id.editText3);
        addData = findViewById(R.id.button);
        viewData = findViewById(R.id.button2);
        calendarView = findViewById(R.id.calendarView);
        viewDayData = findViewById(R.id.button3);
        calendar();
        addDataInDatabase();
        viewAllData();
        viewConcreteData();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void addDataInDatabase(){
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = myDatabase.insertData(
                        editDate.getText().toString(),
                        Float.parseFloat(editSugar.getText().toString()),
                        editRecord.getText().toString());
                if (isInserted == true){
                    Toast.makeText(MainActivity.this,"Insert Complete",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void viewAllData(){
        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = myDatabase.getAllData();
                 if (cursor.getCount() == 0){
                     showMessage("Error","Not found");
                     return;
                 }
                 StringBuffer buffer = new StringBuffer();
                 while (cursor.moveToNext()){
                     buffer.append("ID :" + cursor.getString(0) + "\n");
                     buffer.append("Date :" + cursor.getString(1) + "\n");
                     buffer.append("Sugar :" + cursor.getString(2) + "\n");
                     buffer.append("Record :" + cursor.getString(3) + "\n\n");
                 }
                 showMessage("Successful",buffer.toString());
            }
        });
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void calendar(){
        Date date = new Date();
        calendarView.setDate(date.getTime());
    }
    public void viewConcreteData(){
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                editDate.setText(dayOfMonth+"/"+month+"/"+year%100);
                viewDayData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            Cursor cursor = myDatabase.getDayData(editDate.getText().toString());
                            if (cursor.getCount() == 0){
                                showMessage("Error","Not found");
                                return;
                            }
                            StringBuffer buffer = new StringBuffer();
                            while (cursor.moveToNext()){
                                buffer.append("ID :" + cursor.getString(0) + "\n");
                                buffer.append("Date :" + cursor.getString(1) + "\n");
                                buffer.append("Sugar :" + cursor.getString(2) + "\n");
                                buffer.append("Record :" + cursor.getString(3) + "\n\n");
                            }
                            showMessage("Successful",buffer.toString());
                    }
                });
            }
        });
    }
}
