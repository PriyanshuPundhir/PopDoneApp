/*
* Copyright (C) 2016 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.todolist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.android.todolist.database.AppDatabase;
import com.example.android.todolist.database.TaskEntry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class AddTaskActivity extends AppCompatActivity {

    // Extra for the task ID to be received in the intent
    public static final String EXTRA_TASK_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    // Constants for priority
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;
    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_TASK_ID = -1;
    // Constant for logging
    private static final String TAG = AddTaskActivity.class.getSimpleName();
    // Fields for views
    EditText mEditText,mNumber,mHour,mName1,mName2;
    RadioGroup mRadioGroup;
    Button mButton;
    private int mTaskId = DEFAULT_TASK_ID;

    // Member variable for the Database
    private AppDatabase mDb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        initViews();

        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            mButton.setText(R.string.update_button);
            if (mTaskId == DEFAULT_TASK_ID) {
                // populate the UI
                mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);

                AddTaskViewModelFactory addTaskViewModelFactory=new AddTaskViewModelFactory(mDb,mTaskId);
                // for that use the factory created above AddTaskViewModel

                final AddTaskViewModel viewModel
                        = ViewModelProviders.of(this, addTaskViewModelFactory).get(AddTaskViewModel.class);

                viewModel.getMtask().observe(this, new Observer<TaskEntry>() {
                    @Override
                    public void onChanged(@Nullable TaskEntry taskEntry) {
                        viewModel.getMtask().removeObserver(this);
                        populateUI(taskEntry);
                    }
                });
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID, mTaskId);
        super.onSaveInstanceState(outState);
    }

    /**
     * initViews is called from onCreate to init the member variable views
     */
    private void initViews() {
        mEditText = findViewById(R.id.editTextTaskDescription);
        mRadioGroup = findViewById(R.id.radioGroup);
        mNumber=findViewById(R.id.editTextTime);
        mHour=findViewById(R.id.editTextMobNo);
        mButton = findViewById(R.id.saveButton);
        mName1=findViewById(R.id.editTextName1);
        mName2=findViewById(R.id.editTextName2);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }

    /**
     * populateUI would be called to populate the UI when in update mode
     *
     * @param task the taskEntry to populate the UI
     */
    private void populateUI(TaskEntry task) {
        if (task == null) {
            return;
        }

        mEditText.setText(task.getDescription());
        setPriorityInViews(task.getPriority());
    }

    /**
     * onSaveButtonClicked is called when the "save" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */
    private static class MyTimeTask extends TimerTask
    {
       String description;
       String mobileNumber;
       MyTimeTask(String description,String mobileNumber)
       {
           this.description=description;
           this.mobileNumber=mobileNumber;
       }
        public void run()
        {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(mobileNumber, null, "Please Return the"+description, null, null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }




    public void sendSMS(String mobileNumber,String description,int hourNotification)
    {
        //the Date and time at which you want to execute
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        long HOUR = 3600*1000;
        Date newDate = new Date(date.getTime() + hourNotification * HOUR);
        //Now create the time and schedule it
        Timer timer = new Timer();
        MyTimeTask obj=new MyTimeTask(description,mobileNumber);
        //Use this if you want to execute it once
        PendingIntent pendingIntent;
        AlarmManager manager;

        Intent alarmIntent = new Intent(this, AlarmReciever.class);
        alarmIntent.putExtra("number", mobileNumber);
        alarmIntent.putExtra("desc",description);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        int interval = 1000;
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        timer.schedule(obj, newDate);

    }


    public void onSaveButtonClicked() {
        String description = mEditText.getText().toString();
        int priority = getPriorityFromViews();
        Date date = new Date();
        String mobileNumber=mNumber.getText().toString();
        //int hourNotification=mHour.getTex;
        sendSMS(mobileNumber,description,1);
        final TaskEntry task = new TaskEntry(description,mName1.getText().toString(),mName2.getText().toString(),mobileNumber,priority,date);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mTaskId == DEFAULT_TASK_ID) {
                    // insert new task
                    mDb.taskDao().insertTask(task);
                } else {
                    //update task
                    task.setId(mTaskId);
                    mDb.taskDao().updateTask(task);
                }
                finish();
            }
        });
    }

    /**
     * getPriority is called whenever the selected priority needs to be retrieved
     */
    public int getPriorityFromViews() {
        int priority = 1;
        int checkedId = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.radButton1:
                priority = PRIORITY_HIGH;
                break;
            case R.id.radButton2:
                priority = PRIORITY_MEDIUM;
                break;
            case R.id.radButton3:
                priority = PRIORITY_LOW;
        }
        return priority;
    }

    /**
     * setPriority is called when we receive a task from MainActivity
     *
     * @param priority the priority value
     */
    public void setPriorityInViews(int priority) {
        switch (priority) {
            case PRIORITY_HIGH:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton1);
                break;
            case PRIORITY_MEDIUM:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton2);
                break;
            case PRIORITY_LOW:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton3);
        }
    }
}
