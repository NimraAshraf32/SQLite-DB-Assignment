package com.example.sqlitedb_assignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName, editSubject,editMarks,editId;
    Button btnAdd,btnViewAll,btnUpdate,btndelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb=new DatabaseHelper(this);
        editName=findViewById(R.id.editTextTextPersonName);
        editSubject=findViewById(R.id.editTextTextPersonName2);
        editMarks= findViewById(R.id.editTextTextPersonName3);
        editId=findViewById(R.id.editTextTextPersonName4);
        btnAdd=findViewById(R.id.button);
        btnViewAll=findViewById(R.id.button2);
        btnUpdate=findViewById(R.id.button3);
        btndelete=findViewById(R.id.button5);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }
    public void DeleteData()
    {
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedRows = myDb.deleteData(editId.getText().toString());
                if(deletedRows > 0)
                {
                    Toast.makeText(MainActivity.this, "DATA DELETED" ,Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(MainActivity.this, "DATA  NOT DELETED" ,Toast.LENGTH_LONG).show();
            }


        });
    }
    public void UpdateData()
    {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate= myDb.updateData(editId.getText().toString(),editName.getText().toString(), editSubject.getText().toString(),editMarks.getText().toString());
            if (isUpdate==true)
            {
                Toast.makeText(MainActivity.this, "DATA UPDATED" ,Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(MainActivity.this, "DATA  NOT UPDATED" ,Toast.LENGTH_LONG).show();
            }

        });
    }
    public void AddData()
    {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              boolean isInserted=  myDb.insertData(editName.getText().toString() , editSubject.getText().toString() , editMarks.getText().toString());
            if(isInserted==true)
            {
                Toast.makeText(MainActivity.this, "DATA INSERTED" ,Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(MainActivity.this, "DATA  NOT INSERTED" ,Toast.LENGTH_LONG).show();

            }
        });
    }
    public void viewAll()
    {
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getAllData();
                if(res.getCount()==0)
                {//show message
                    showMessage("Error","Nothing found");
                    return;
                }
                StringBuffer buffer= new StringBuffer();
                while(res.moveToNext())
                {
                    buffer.append("ID : "+res.getString(0)+ "\n");
                    buffer.append("Name : "+res.getString(1)+ "\n");
                    buffer.append("Subject : "+res.getString(2)+ "\n");
                    buffer.append("Marks : "+res.getString(3)+ "\n");
                }
                //SHOW ALL DATA

                showMessage("Data",buffer.toString());
            }
        });
    }
    public void showMessage(String title ,String Message)
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}