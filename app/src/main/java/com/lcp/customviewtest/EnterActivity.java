package com.lcp.customviewtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import custom.calendar.CalendarActivity;
import custom.scroller.CurtainActivity;

public class EnterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
    }
    public void clickCalendar(View view){
        startActivity(new Intent(this, CalendarActivity.class));
    }
    public void clickOther(View view){
        startActivity(new Intent(this, CurtainActivity.class));
    }
}
