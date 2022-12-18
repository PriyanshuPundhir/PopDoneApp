package com.example.android.todolist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

public class AlarmReciever extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String mobileNumber=intent.getStringExtra("number");
        String description=intent.getStringExtra("desc");
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(mobileNumber, null, "Please Return the"+description, null, null);
            Toast.makeText(context, "try", Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(context, "Catch", Toast.LENGTH_SHORT).show();
        }
    //    Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();

    }
}
