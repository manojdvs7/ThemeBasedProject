package com.example.plantsclassification;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseOperations {
    private DatabaseReference mDatabase;
    private Context mContext;
    public int result;
    public LatLng[] lists;
    public Plant plant;
    DatBaseOperations(Context context)
    {
        mContext=context;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    public void addToDataBase(final String plantName, final double latitude, final double longitude)
    {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(plantName)) {
                    //int count=Integer.parseInt( snapshot.child(plantName).child("count").getValue().toString());
                    String lat=snapshot.child(plantName).child("latitudes").getValue().toString()+","+latitude;
                    String lon=snapshot.child(plantName).child("longitudes").getValue().toString()+","+longitude;
                    mDatabase.child(plantName).child("latitudes").setValue(lat);
                    mDatabase.child(plantName).child("longitudes").setValue(lon);
   /*                 mDatabase.child("/").child(plantName).child("latitude"+(count+1)).setValue(latitude);
                    mDatabase.child("/").child(plantName).child("longitude"+(count+1)).setValue(longitude);
                    mDatabase.child("/").child(plantName).child("count").setValue(count+1);*/
                }
                else {
                    //mDatabase.child("/").child(plantName).child("count").setValue(1);
                    mDatabase.child("/").child(plantName).child("latitudes").setValue(latitude);
                    mDatabase.child("/").child(plantName).child("longitudes").setValue(longitude);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mContext, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getLatitudesAndLongitudes(final String plantName)
    {

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(plantName)) {
                    getData(snapshot,plantName);
                    Toast.makeText(mContext, this.getClass().toString(), Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(mContext, "No plant found", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mContext, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getData(DataSnapshot snapshot,String plantName)
    {
        String latitudes=snapshot.child(plantName).child("latitudes").getValue().toString();
        String longitudes=snapshot.child(plantName).child("longitudes").getValue().toString();

        //Toast.makeText(mContext, plant., Toast.LENGTH_SHORT).show();

        DatBaseOperations.this.plant = new Plant(latitudes,longitudes);
    }

    public Plant getPlantData()
    {
        //Toast.makeText(mContext, plant.latitudes, Toast.LENGTH_SHORT).show();
        return plant;
    }
}
