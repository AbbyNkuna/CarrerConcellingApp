package com.example.carrerconcellingapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.carrerconcellingapp.Model.University;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class AddUnivsity extends AppCompatActivity {
    MaterialEditText Abb,Name,Req;
    Button Submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_univsity);
        init();
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(Abb.getText().toString().trim())){
                    Abb.setError("Please fill in");
                    return;
                }
                if (TextUtils.isEmpty(Name.getText().toString().trim())){
                    Name.setError("Please fill in");
                    return;
                }
                if (TextUtils.isEmpty(Req.getText().toString().trim())){
                    Req.setError("Please fill in");
                    return;
                }
                if (Integer.parseInt(Req.getText().toString()) <19){
                    Req.setError("Must Be Greater Than 19");
                    return;
                }
                University university = new University();
                university.setAbb(Abb.getText().toString().trim());
                university.setName(Name.getText().toString().trim());
                university.setMinReq(Req.getText().toString());
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Univen");
                reference.child(Abb.getText().toString()).setValue(university).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddUnivsity.this, "University Added SuccessFully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddUnivsity.this,AdminHome.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddUnivsity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void init() {
        Abb = findViewById(R.id.ABV);
        Name = findViewById(R.id.Name);
        Req = findViewById(R.id.Total);
        Submit= findViewById(R.id.Submit);
    }
}