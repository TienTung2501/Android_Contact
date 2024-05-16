package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class AddContactActivity extends AppCompatActivity {

    TextInputEditText idTxt,nameTxt,phoneTxt;
    Button btnAdd,btnBack;
    private static final int REQUEST_ADD_CONTACT = 2;
    NguyenTienTung_Sqlite mydb;
    @Override
    protected void onStart() {
        super.onStart();
        mydb.openDb();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mydb.closeDb();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getIntentExtra();
        initComponent();
        eventListener();
    }

    private void eventListener() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int _id= Integer.parseInt(idTxt.getText().toString());
                String _name= nameTxt.getText().toString();
                String _phone=phoneTxt.getText().toString();
                mydb.Insert(_id,_name,_phone);
                Toast.makeText(AddContactActivity.this,"Update Suceess: "+String.valueOf(_id)+_name+_phone,Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initComponent() {
        mydb=new NguyenTienTung_Sqlite(this);
        btnAdd=findViewById(R.id.addBtn);
        btnBack=findViewById(R.id.backBtn);
        idTxt=findViewById(R.id.editTextId);
        nameTxt=findViewById(R.id.editTextName);
        phoneTxt=findViewById(R.id.editTextPhone);
    }

    private void getIntentExtra() {
        Intent intent = getIntent();// cần khai báo biến object ở trên
        //object=(Contact) intent.getSerializableExtra("object");
    }
}