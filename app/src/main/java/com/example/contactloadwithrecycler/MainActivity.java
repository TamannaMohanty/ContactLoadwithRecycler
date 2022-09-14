package com.example.contactloadwithrecycler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickInterface{

    //Button loadcontactBtn;
    RecyclerView rcv;
    ContactModel contactModel;
    MyAdapter myAdapter;
    List<ContactModel> contactModelList = new ArrayList<>();

    List<ContactModel> filterList = new ArrayList<>();

    Cursor cursor;
    ContentResolver contentResolver;
    SearchView searchView;

    AlertDialog alertDialog;
   TextView countitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       // loadcontactBtn = findViewById(R.id.loadContacts);
        rcv = findViewById(R.id.rclView);
        searchView = findViewById(R.id.searchview);
       countitem = findViewById(R.id.count);




        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 101);

        } else {
            readAllContacts();

            searchView.setQueryHint("Search among"+" "+contactModelList.size()+" "+"contact(s)");






        }





      searchView.clearFocus();




        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {





                filterList.clear();


                if (s.toString().isEmpty()) {
                    countitem.setVisibility(View.GONE);

                    rcv.setAdapter(new MyAdapter(getApplicationContext(), contactModelList));
                    myAdapter.notifyDataSetChanged();




                } else {

                    Filter(s.toString());


                }

                return true;
            }
        });

        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(this, contactModelList);
        rcv.setAdapter(myAdapter);





       /* findViewById(R.id.loadContacts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                permissionMethod();
            }*/

         /*   private void permissionMethod() {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 101);

                } else {
                    readAllContacts();

                    countitem.setText("Total Count"+contactModelList.size());





                }

            }
        });*/


    }




    private void Filter(String text) {
           countitem.setVisibility(View.VISIBLE);

        for (ContactModel post : contactModelList) {

            if (post.getName().toLowerCase().contains(text.toLowerCase())) {

                filterList.add(post);
            }
        }


        rcv.setAdapter(new MyAdapter(getApplicationContext(), filterList));


        myAdapter.notifyDataSetChanged();

        countitem.setText(" " + filterList.size() + " " + "CONTACTS FOUND");


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 101:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readAllContacts();
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private void readAllContacts() {


          contactModelList.clear();

        contentResolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; //Uniform Resource identifiers
        String[] projections = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Photo.PHOTO_URI};
        String selection = null; //Selection for row wise Search
        String[] args = null;
        String order = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " asc";

        cursor = contentResolver.query(uri, projections, selection, args, order);

        if (cursor.getCount() > 0 && cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                @SuppressLint("Range") String photo = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI));
                // Log.d("IMAGE",""+photo);
                // Log.d("IMAGE",""+photo);

                contactModel = new ContactModel(name, number, photo);
                contactModelList.add(contactModel);



                myAdapter = new MyAdapter(getApplicationContext(), contactModelList);
                rcv.setAdapter(myAdapter);





                /*arrayList.add(""+name+"\n"+number);
                arrayAdapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,arrayList);
                cList.setAdapter(arrayAdapter);*/

            }


        } else {
            Toast.makeText(MainActivity.this, "No Contacts Found in  Your Device", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onItemClick(int position)
    {

    }
    }




