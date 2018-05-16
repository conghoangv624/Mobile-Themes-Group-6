package com.example.group6.dulichdoday;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group6.dulichdoday.Models.Tour;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailTourActivity extends AppCompatActivity {

    Intent intent;
    Bundle bundle;
    private TextView tvDatTour;
    private TextView tvCode,tvDescip,tvPrice,tvAdd,tvNoidung;
    private ImageView img;

    private DatabaseReference mData;
    private ArrayList<Tour> arrTour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_tour_layout);
        mData = FirebaseDatabase.getInstance().getReference();

        tvDatTour = (TextView) findViewById(R.id.tvDatTour);

        tvCode = (TextView) findViewById(R.id.tvCodeDetail);
        tvAdd = (TextView) findViewById(R.id.tvAddDetail);
        tvDescip = (TextView) findViewById(R.id.tvDesciption);
        tvNoidung = (TextView) findViewById(R.id.tvNoiDungTour);
        tvPrice = (TextView) findViewById(R.id.tvPriceDetail);
        img = (ImageView) findViewById(R.id.imgDetail);

        final Dialog dialog = new Dialog(DetailTourActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_accept);
        loadData();
        tvDatTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                TextView tvDat = dialog.findViewById(R.id.tvDat);
                tvDat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mData.child("TourDat").push().setValue(new Tour("",tvCode.getText().toString(),tvAdd.getText().toString(),tvDescip.getText().toString(),tvPrice.getText().toString(),"",tvNoidung.getText().toString()));
                        dialog.dismiss();
                        Toast.makeText(DetailTourActivity.this, "Đặt thành công", Toast.LENGTH_SHORT).show();
                        finish();
                        // Intent intent = new Intent(DetailTourActivity.this,MainLayoutActivity.class);
                        //startActivity(intent);
                    }
                });
            }
        });
        //


    }

    private void loadData() {
        // Goi qua tu adaptertour
        intent = getIntent();
        bundle = intent.getBundleExtra("bundle");
        bundle.getString("tour");
        mData.child("Tour").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final Tour tour = dataSnapshot.getValue(Tour.class);
                if (tour.getCodeTour().equalsIgnoreCase(bundle.getString("tour"))) {
                    Picasso.with(DetailTourActivity.this).load(tour.getImgProduct()).into(img);
                    tvCode.setText(tour.getCodeTour());
                    tvAdd.setText(tour.getAddTour());
                    tvDescip.setText(tour.getDiscripTour());
                    tvNoidung.setText(tour.getNoidung());
                    tvPrice.setText(tour.getPriceTour());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
