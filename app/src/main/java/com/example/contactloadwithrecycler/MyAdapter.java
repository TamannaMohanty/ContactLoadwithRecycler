package com.example.contactloadwithrecycler;

import android.content.Context;
import android.content.Intent;
import android.hardware.lights.LightState;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.holder>  {


    Context context;
    List<ContactModel> contactModelList = new ArrayList<>();

   AlertDialog alertDialog;



    public MyAdapter(Context context, List<ContactModel> contactModelList) {
        this.context = context;
        this.contactModelList = contactModelList;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.singlerow,parent,false);//wont attach the child view to parent





        return new holder(view);
    }

    //Here we will get and set data from model class
    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
        ContactModel contactModel=contactModelList.get(i);


        holder.name.setText(contactModelList.get(i).getName());
        holder.number.setText(contactModelList.get(i).getNumber());
        holder.shapeableImageView.setImageURI(contactModelList.get(i).imageUri);
        //holder.shapeableImageView.setImageURI(Uri.parse(contactModelList.get(position).getImage()));

        if(contactModelList.get(i).getImage()!=null)
        {
            holder.shapeableImageView.setImageURI(Uri.parse(""+contactModelList.get(i).getImage()));
        }else
        {
           Log.i(null,""+contactModelList.get(i).getImage());
        }
    }

    @Override
    public int getItemCount() {


        return contactModelList.size();
    }

    //holder class holds the data of the layout file .
    class holder extends RecyclerView.ViewHolder{
        ShapeableImageView shapeableImageView;

        TextView name;
        TextView number;


        public holder(@NonNull View itemView) {

            super(itemView);
            shapeableImageView=itemView.findViewById(R.id.contact_image);
            name=itemView.findViewById(R.id.contact_name);
            number=itemView.findViewById(R.id.contact_number);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    View view = LayoutInflater.from(context).inflate(R.layout.contact_alert,null);
                    ShapeableImageView shapeableImageView1 = view.findViewById(R.id.alertProfile);
                    TextView alert_contact_name = view.findViewById(R.id.alert_name);
                    alert_contact_name.setText(name.getText().toString());


                    shapeableImageView1.setImageURI(Uri.parse(""+contactModelList.get(getAdapterPosition()).getImage()));


                    FloatingActionButton whatsappbtn = view.findViewById(R.id.whasappbtn);


                    FloatingActionButton sharebtn = view.findViewById(R.id.sharebtn);

                    builder.setView(view);
                    builder.setCancelable(false);
                    Button cancelButton = view.findViewById(R.id.alertCancel);

                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            alertDialog.dismiss();

                        }
                    });

                    //Share btn
                    sharebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                shareIntent.setType("text/plain");
                                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My Application Name");
                                String shareMessage = "\ni Hi Buddy";

                                v.getContext().startActivity(Intent.createChooser(shareIntent, "choose one"));

                            }catch (Exception e)
                            {
                                //e.toString();
                            }

                        }
                    });



                    //Whatsapp

                    whatsappbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            try {
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                String url = "https://api.whatsapp.com/send?phone="+contactModelList.get(getAdapterPosition()).getName()+"&text=" + URLEncoder.encode("Hi,Welcome to whatsapp","UTF-8");
                                i.setPackage("com.whatsapp");
                                i.setData(Uri.parse(url));


                                    v.getContext().startActivity(i);

                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }


                        }
                    });

                    alertDialog =  builder.create();
                    alertDialog.show();


                }
            });


        }

    }

}
