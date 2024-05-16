package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {

    public ContactAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public ContactAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v= convertView;
        if(v==null){
            LayoutInflater vi=LayoutInflater.from(getContext());
            v=vi.inflate(R.layout.contact_item,null);// lấy từ layout ta thiết kế

        }
        Contact sv=getItem(position);
        if(sv!= null){

            TextView id= v.findViewById(R.id.idContact);
            TextView name=v.findViewById(R.id.nameContact);
            TextView phone=v.findViewById(R.id.phoneContact);
            id.setText(String.valueOf(sv.getId()));
            name.setText(String.valueOf(sv.getName()));
            phone.setText(String.valueOf(sv.getPhone()));
        }
        return v;
    }

}
