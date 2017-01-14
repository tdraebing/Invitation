package org.driven_by_data.invitation;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addEventToCalendar(View view){
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2017, 5, 9, 20, 0);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2017, 5, 10, 5, 0);

        Intent intentAddEvent = new Intent(Intent.ACTION_INSERT);
        intentAddEvent.setType("vnd.android.cursor.item/event");
        intentAddEvent.putExtra(CalendarContract.Events.TITLE, "Thomas' Birthday Party");
        intentAddEvent.putExtra(CalendarContract.Events.EVENT_TIMEZONE, "Europe/Berlin");
        intentAddEvent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                beginTime.getTimeInMillis());
        intentAddEvent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                endTime.getTimeInMillis());
        intentAddEvent.putExtra(CalendarContract.Events.EVENT_LOCATION,
                "Schlosshof 1, 69117 Heidelberg, Germany");
        intentAddEvent.putExtra(CalendarContract.Events.DESCRIPTION,
                "The most awesome Party EVER!");
        intentAddEvent.putExtra(CalendarContract.Events.ACCESS_LEVEL, 2);
        intentAddEvent.putExtra(CalendarContract.Events.AVAILABILITY, 0);
        startActivity(intentAddEvent);
    }
}
