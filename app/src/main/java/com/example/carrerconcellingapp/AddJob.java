package com.example.carrerconcellingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrerconcellingapp.Model.Job;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddJob extends AppCompatActivity {
    public TextView Fac,Info;
    Button Submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        init();
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(Fac.getText().toString().trim())){
                    Fac.setError("Please fill in");
                    return;
                }
                if (TextUtils.isEmpty(Info.getText().toString().trim())){
                    Info.setError("Please fill in");
                    return;
                }
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Jobs");
                Job job = new Job();
                job.setFac(Fac.getText().toString());
                job.setInfo(Info.getText().toString());
                reference.child(String.valueOf(System.currentTimeMillis())).setValue(job).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddJob.this, "Job Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddJob.this,AdminHome.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddJob.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void init() {
        Fac = findViewById(R.id.Fac);
        Info = findViewById(R.id.Info);
        Submit = findViewById(R.id.Submit);
    }
}