package com.example.consultasmedicas.utils;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.IBinder;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.Toast;

import com.example.consultasmedicas.model.Treatment.Treatment;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

import androidx.annotation.Nullable;

public class CalendarService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();

        AdminSQLite admin = new AdminSQLite(this,"treatments",null,1);


        FirebaseFirestore.getInstance().collection("treatments").whereEqualTo("patientAppUserId", 1)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        SQLiteDatabase db = admin.getWritableDatabase();
                        for (DocumentChange documentChange : value.getDocumentChanges()){
                            boolean exists=false;
                            if (documentChange.getType() == DocumentChange.Type.ADDED){
                                Cursor cursor = db.rawQuery("SELECT * FROM treatments",null);
                                while(cursor.moveToNext()) {
                                    if(cursor.getString(0).equals(documentChange.getDocument().getId())){
                                        exists=true;
                                        Log.e("TREATMENT_IDs", "SQLITE: "+cursor.getString(0)+" = "+documentChange.getDocument().getId());
                                    }
                                }
                                cursor.close();

                                if(!exists){
                                    ContentValues values = new ContentValues();
                                    values.put("treatmentId",documentChange.getDocument().getId());
                                    db.insert("treatments",null,values);

                                    Log.e("DOC ADDED", documentChange.getDocument().toObject(Treatment.class).getDescription());
                                    createCalendarEvent(documentChange.getDocument().toObject(Treatment.class));
                                }
                            }
                            if(documentChange.getType() == DocumentChange.Type.REMOVED){
                                db.delete("treatments","treatmentId='"+documentChange.getDocument().getId()+"'",null);
                                Log.e("ON_DELETE", "Eliminado");
                            }
                        }
                        db.close();
                    }
                });
       Log.e("SERVICIO","EL SERVICIO FUE CREADO");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createCalendarEvent(Treatment treatment) {

        Calendar startTime = Calendar.getInstance();
        startTime.setTime(treatment.getStartTimestamp());
        int recurrence = treatment.getRecurrence();
        int interval = treatment.getInterval()/3600000;
        String description = treatment.getDescription();

        //Crear evento
        long calID = 2;
        long startMillis = 0;
        long endMillis = 0;
        //Calendar beginTime = Calendar.getInstance();
        //beginTime.set(2020, 11, 1, 21, 10);
        startMillis = startTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2020, 11, 1, 22, 00);
        endMillis = endTime.getTimeInMillis();


        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        //values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.DURATION,"PT"+(recurrence*interval)+"H");
        //values.put(CalendarContract.Events.RRULE,"COUNT="+recurrence);
        values.put(CalendarContract.Events.TITLE, "Tratamiento");
        values.put(CalendarContract.Events.DESCRIPTION, description);
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/La_Paz");
        Uri u = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values);

        // get the event ID that is the last element in the Uri
        long eventID = Long.parseLong(u.getLastPathSegment());

        Log.d("CALENDAR", "eventID: "+eventID);
        // ... do something with event ID

        //Fin Crear Evento
    }

}
