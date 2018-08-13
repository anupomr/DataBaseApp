package com.example.anupo.dbappfortest;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FDBActivity extends AppCompatActivity {
    private TaskManager taskManager;
    private EditText txtId, txtTaskName, txtTaskAssigned,txtTaskDescription ;
    private Button btnAdd, btnShow, btnEdit, btnDelete;
    private TextView resultTV;
    private final static String TABLE_NAME = "AndroidTask";
    Spinner spinner;
    private static final String tableCreatorString =
            "CREATE TABLE "+ TABLE_NAME + " (taskId integer primary key, taskName text,asaignTo text,taskDescription text);";

    private static final String tableCreatorStringTwo =
            "CREATE TABLE "+ TABLE_NAME + " (taskId integer primary key, taskName text,taskDescription text);";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fdb);
        View v=getCurrentFocus();

        //
        txtId = (EditText) findViewById(R.id.txtTaskId);
        txtTaskName = (EditText) findViewById(R.id.txtTaskName);
        txtTaskAssigned=(EditText)findViewById(R.id.txtTaskAssigned);
        txtTaskDescription = (EditText) findViewById(R.id.txtTaskDescription);
        resultTV=(TextView)findViewById(R.id.result);
        spinner = (Spinner) findViewById(R.id.spn_OS);

        //
        btnAdd = (Button)findViewById(R.id.addTask);
        btnShow = (Button)findViewById(R.id.showTask);
        btnEdit = (Button)findViewById(R.id.editTask);
        //
        // initialize the tables
        try {
            taskManager = new TaskManager(this);
            //create the table
            taskManager.dbInitialize(TABLE_NAME, tableCreatorString);
            addTask(v);

        }
        catch(Exception exception)
        {
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("Error: ",exception.getMessage());
        }

    }
    public void showTask(View v)
    {
        if(txtId.getText().equals(""))
        {Toast.makeText(this,"Fields are Required",Toast.LENGTH_LONG).show();
            return;
        }else {
        try {
            Task task = taskManager.getTaskById(txtId.getText().toString(), "taskId");
            txtTaskName.setText(task.getTaskName());
            txtTaskAssigned.setText(task.getAsaignTo());
            txtTaskDescription.setText(task.getTaskDescription());
        }
        catch (Exception exception)
        {
            Toast.makeText(FDBActivity.this,
                    exception.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("Error: ",exception.getMessage());
        }
        }
    }
    /*

    */
    public  void showAllTask(View v){
        Cursor cursor=taskManager.getAllItem();

        if (cursor.getCount()==0) {
            //Message
            return;
        }
            StringBuffer buffer=new StringBuffer();

            while(cursor.moveToNext()){
                buffer.append("Task Id    : "+cursor.getString(0)+"\n");
                buffer.append("Task Name  : "+cursor.getString(1)+"\n");
                buffer.append("Assign To  : "+cursor.getString(2)+"\n");
                buffer.append("Description: "+cursor.getString(3)+"\n\n");
            }

        resultTV.setText(buffer.toString());
    }
    //
    public void addTask(View v)
    {
        //read values
        int taskId = Integer.parseInt(txtId.getText().toString());
        String taskName = txtTaskName.getText().toString();
        String asaignTo=txtTaskAssigned.getText().toString();
        String taskDescription = txtTaskDescription.getText().toString();
        //initialize ContentValues object with the new task
        ContentValues contentValues = new ContentValues();
        contentValues.put("taskId",1001);
        contentValues.put("taskName","hhhhh");
        contentValues.put("asaignTo","Krishna");
        contentValues.put("taskDescription","Final");
        //
        try {
            taskManager.addRow(contentValues);
        }
        catch(Exception exception)
        {
            //
            Toast.makeText(FDBActivity.this,
                    exception.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("Error: ",exception.getMessage());

        }
    }
    public void editTask(View v)
    {
        int taskId = Integer.parseInt(txtId.getText().toString());

        String taskName = txtTaskName.getText().toString();
        String asaignTo=txtTaskAssigned.getText().toString();
        String taskDescription = txtTaskDescription.getText().toString();
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put("taskId",taskId);
            contentValues.put("taskName",taskName);
            contentValues.put("asaignTo",asaignTo);
            contentValues.put("taskDescription",taskDescription);
            //edit the row
            boolean b=taskManager.editRow(taskId, "taskId", contentValues);
        }
        catch(Exception exception)
        {
            Toast.makeText(FDBActivity.this,
                    exception.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("Error: ",exception.getMessage());
        }
    }


//Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.curd,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        View v=getCurrentFocus();
        switch (item.getItemId())
        {
            case R.id.home:
                break;
            case R.id.add:
                showSpinnerTask(v);
                break;
            case R.id.edit:
                break;
            case R.id.show:
                //showTask(v);
                showAllTask(v);
                break;
            case R.id.del:
                break;
            case R.id.exit:
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                break;
        }
        return  true;
    }

    //End Menu
    // Message box

    //Spinner
    public  void showSpinnerTask(View v)
    {
        Cursor cursor=taskManager.getAllItem();
        String result="";
        final String[] mySpinnerArray;
        List<String> list =new ArrayList<String>();

        if (cursor.moveToFirst()){
            list.add(cursor.getString(cursor.getColumnIndex("taskId")));
            while(cursor.moveToNext()){
                list.add(cursor.getString(cursor.getColumnIndex("taskId")));
            }
        }
        cursor.close();
        for (String element : list) {
             //mySpinnerArray=element;
        }
        //resultTV.setText(result);

       // final String[] mySpinnerArray = getResources().getStringArray(R.array.os_values);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        spinner.setAdapter(adapter);

    }

}
