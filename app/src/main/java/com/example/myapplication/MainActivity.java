package com.example.myapplication;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_UPDATE_CONTACT = 1;
    private static final int REQUEST_ADD_CONTACT = 2;
    public int position,idContact;
    private NguyenTienTung_Sqlite myDb;
    private ListView listViewContact;
    private ArrayList<Contact> contacts;
    private ArrayAdapter<Contact> adapter;
    ImageView addBtn;
    TextInputEditText searchTxt;
    String titleMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
        eventListener();
        registerForContextMenu(listViewContact);

    }
    @Override
    protected void onStart() {
        super.onStart();
        myDb.openDb();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myDb.closeDb();
    }

    private void eventListener() {
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,String.valueOf(contacts.size()),Toast.LENGTH_LONG).show();
            }
        });
        listViewContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                idContact=contacts.get(i).getId();
                position=i;
                titleMenu=contacts.get(i).getName();
                return false;
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, AddContactActivity.class);
                startActivityForResult(intent, REQUEST_ADD_CONTACT);
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);
        MenuItem editItem = menu.findItem(R.id.mnEdit);
        MenuItem deleteItem = menu.findItem(R.id.mnDelete);
        editItem.setTitle("Edit " + titleMenu);
        deleteItem.setTitle("Delete " + titleMenu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getItemId() ==  R.id.mnEdit) {
            Intent intent=new Intent(MainActivity.this, InformationDetailActivity.class);
            intent.putExtra("object", (Serializable) contacts.get(position));
            //startActivity(intent);
            startActivityForResult(intent, REQUEST_UPDATE_CONTACT);
            return true;
        }
        else if (item.getItemId() == R.id.mnDelete){
            showDeleteConfirmationDialog();
            return true;
        }
        else {
            return super.onContextItemSelected(item);
        }
    }

    private void initComponent() {
        myDb=new NguyenTienTung_Sqlite(this);
        contacts=new ArrayList<>();
        listViewContact=findViewById(R.id.listViewContact);
        addBtn=findViewById(R.id.addBtn);
        searchTxt=findViewById(R.id.searchTxt);
        displayData();

    }
    private void sortContactsByName() {
        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact contact1, Contact contact2) {
                return contact1.getName().compareToIgnoreCase(contact2.getName());
            }
        });
    }
    private void displayData(){
        contacts.clear();
        fetchData();
        sortContactsByName();
        adapter=new ContactAdapter(MainActivity.this, R.layout.contact_item, contacts);
        listViewContact.setAdapter(adapter);
        adapter.notifyDataSetChanged(); // Cập nhật dữ liệu trong Adapter
    }
    private  void fetchData(){
        Cursor cursor= myDb.DisplayAll();
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndexOrThrow(NguyenTienTung_Sqlite.getId()));
            String name=cursor.getString(cursor.getColumnIndexOrThrow(NguyenTienTung_Sqlite.getName()));
            String phone=cursor.getString(cursor.getColumnIndexOrThrow(NguyenTienTung_Sqlite.getPhone()));
            Contact c=new Contact(id,name,phone);
            contacts.add(c);
        }

    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(contacts.get(position).getName()+" Are you want to delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Xử lý khi người dùng đồng ý xóa
                        deleteContact(idContact);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        builder.create().show();
    }

    private void deleteContact(int id) {
        myDb.Delete(id);
        for (int i=0;i<contacts.size();i++){
            if(contacts.get(i).getId()==id){
                contacts.remove(i);
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_UPDATE_CONTACT && resultCode == RESULT_OK) {
            // Nếu dữ liệu đã được cập nhật trong InformationDetailActivity, làm mới danh sách và hiển thị lại
            displayData();
        }
        if(requestCode == REQUEST_ADD_CONTACT && resultCode == RESULT_OK){
            // If a new contact is added successfully, refresh the contact list
            displayData();
        }
    }



}