package com.santosh.hajirkhata;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1beta1.StructuredQuery;

public class loginclass {
    private FirebaseFirestore data= FirebaseFirestore.getInstance();
    private DocumentReference admin=data.collection("admin").document("1");
    private DocumentReference state=data.collection("state").document("1");
    private DocumentReference faculty;
    String states,cusername,cpassword;
    private Context context;
    Intent intent= new Intent();
    public loginclass(Context context){
        this.context=context;
    }
    public void checkstate(){
        state.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
            states=documentSnapshot.getString("state");
            if(states.equals("ina")){
                intent.setClass(context,admindashboard.class);
                context.startActivity(intent);
            }
            }
        });

    }
}
