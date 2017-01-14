package org.driven_by_data.invitation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private class Event{
        Calendar beginTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        String timeZone;
        String place;
        String description;
        String title;
    }

    public static Locale deviceLocale;
    public static Event birthdayEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deviceLocale = Locale.getDefault();

        birthdayEvent = new Event();
        birthdayEvent.beginTime.set(2017, 5, 9, 20, 0, 0);
        birthdayEvent.endTime.set(2017, 5, 10, 5, 0, 0);
        birthdayEvent.timeZone = "Europe/Berlin";
        birthdayEvent.place = "Schlossgarten 1, Heidelberg, Germany";
        birthdayEvent.description = "The most awesome Party EVER!";
        birthdayEvent.title = "Thomas' Birthday Party";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0x1)
            alertSuccessEventSave();
        else
            alertErrorEventSave();
    }

    public void alertSuccessEventSave(){
        new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("See you at the Party!")
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .show();
    }

    public void alertErrorEventSave(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", deviceLocale);

        String message = String.format("Seems like something went wrong with saving the date. " +
                "But here are the event informations: \n\n" +
                "Start time: %1$s \n" +
                "End Time: %2$s \n" +
                "Time Zone: %3$s \n" +
                "Place: %4$s",
                dateFormat.format(birthdayEvent.beginTime.getTime()),
                dateFormat.format(birthdayEvent.endTime.getTime()),
                birthdayEvent.timeZone,
                birthdayEvent.place);
        new AlertDialog.Builder(this)
                .setTitle("Failed to save date")
                .setMessage(message)
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .show();
    }

    public void addEventToCalendar(View view){
        Intent intentAddEvent = new Intent(Intent.ACTION_INSERT);
        intentAddEvent.setType("vnd.android.cursor.item/event");
        intentAddEvent.putExtra(CalendarContract.Events.TITLE, birthdayEvent.title);
        intentAddEvent.putExtra(CalendarContract.Events.EVENT_TIMEZONE, birthdayEvent.timeZone);
        intentAddEvent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                birthdayEvent.beginTime.getTimeInMillis());
        intentAddEvent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                birthdayEvent.endTime.getTimeInMillis());
        intentAddEvent.putExtra(CalendarContract.Events.EVENT_LOCATION,
                birthdayEvent.place);
        intentAddEvent.putExtra(CalendarContract.Events.DESCRIPTION,
                birthdayEvent.description);
        intentAddEvent.putExtra(CalendarContract.Events.ACCESS_LEVEL, 2);
        intentAddEvent.putExtra(CalendarContract.Events.AVAILABILITY, 0);
        startActivityForResult(intentAddEvent, 0x1);
    }
}
