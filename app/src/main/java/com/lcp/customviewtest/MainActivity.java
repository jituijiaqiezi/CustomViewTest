package com.lcp.customviewtest;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import org.joda.time.DateTime;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private String TAG= MainActivity.class.getSimpleName();
    DateTime dateTime=DateTime.now();
    MyCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        init();
    }
    private void init(){
        calendarView=(MyCalendarView)findViewById(R.id.calendar);
    }
    public void selectDate(View view){
        int year=dateTime.getYear();
        int month=dateTime.getMonthOfYear();
        int dayOfMonth=dateTime.getDayOfMonth();
        int hour=dateTime.getHourOfDay();
        int minute=dateTime.getMinuteOfHour();
        int second=dateTime.getSecondOfMinute();
        Log.i(TAG,"Date现在是:"+year+" 年 "+month+" 月 "+dayOfMonth+" 日 "+hour+" 时 "+minute+" 分 "+second+" 秒 ");
        DatePickerDialog dialog=new DatePickerDialog(this,this,year,month-1,dayOfMonth);
        dialog.show();
    }

    public void selectTime(View view){
        int year=dateTime.getYear();
        int month=dateTime.getMonthOfYear();
        int dayOfMonth=dateTime.getDayOfMonth();
        int hour=dateTime.getHourOfDay();
        int minute=dateTime.getMinuteOfHour();
        int second=dateTime.getSecondOfMinute();
        Log.i(TAG,"Date现在是:"+year+" 年 "+month+" 月 "+dayOfMonth+" 日 "+hour+" 时 "+minute+" 分 "+second+" 秒 ");
        TimePickerDialog dialog=new TimePickerDialog(this,this,hour,minute,true);
        dialog.show();
    }
    public void selectDateTime(View view){
        /*int year=dateTime.getYear();
        int month=dateTime.getMonthOfYear();
        int dayOfMonth=dateTime.getDayOfMonth();
        int hour=dateTime.getHourOfDay();
        int minute=dateTime.getMinuteOfHour();
        int second=dateTime.getSecondOfMinute();
        Log.i(TAG,"Date现在是:"+year+" 年 "+month+" 月 "+dayOfMonth+" 日 "+hour+" 时 "+minute+" 分 "+second+" 秒 ");
        TimePickerDialog dialog=new TimePickerDialog(this,this,hour,minute,true);
        dialog.showAnimation();*/
    }
    public void selectNumber(View view){
        int year=dateTime.getYear();
        int month=dateTime.getMonthOfYear();
        int dayOfMonth=dateTime.getDayOfMonth();
        int hour=dateTime.getHourOfDay();
        int minute=dateTime.getMinuteOfHour();
        int second=dateTime.getSecondOfMinute();
        Log.i(TAG,"Date现在是:"+year+" 年 "+month+" 月 "+dayOfMonth+" 日 "+hour+" 时 "+minute+" 分 "+second+" 秒 ");
        TimePickerDialog dialog=new TimePickerDialog(this,this,hour,minute,false);
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.i(TAG,"选中日期是:"+year+" 年 "+(month+1)+" 月 "+dayOfMonth+" 日 ");
        dateTime=new DateTime(year,month+1,dayOfMonth,0,0);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.i(TAG,hourOfDay+" 时 "+minute+" 分 ");
    }
}
