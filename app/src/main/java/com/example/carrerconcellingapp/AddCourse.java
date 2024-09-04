package com.example.carrerconcellingapp;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.carrerconcellingapp.Model.Course;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rengwuxian.materialedittext.MaterialEditText;

import io.paperdb.Paper;

public class AddCourse extends AppCompatActivity {
    MaterialSpinner math,mAps,engAps,other,otherAps;
    MaterialEditText totalAps,code,name,faculty,info;
    Button choose,submit;
    private static int VIDEO_REQUEST = 101;
    private Uri videoUri = null;
    FirebaseStorage storage;
    StorageReference storageReference;
    ProgressDialog mDialog2;
    int x =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        init();
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("video/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Video"),VIDEO_REQUEST);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (math.getSelectedIndex() == 0){
                    Toast.makeText(AddCourse.this, "Please Select the fist Subject", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (other.getSelectedIndex() == 0){
                    Toast.makeText(AddCourse.this, "Please Select One Other Subject", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (math.getSelectedIndex() == 1 && mAps.getSelectedIndex()+1 < 4){
                    Toast.makeText(AddCourse.this, "Mathematics Requires atleast level 3", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (math.getSelectedIndex() == 2 && mAps.getSelectedIndex()+1 < 5){
                    Toast.makeText(AddCourse.this, "Mathematics Literacy Requires atleast level 4", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (other.getSelectedIndex() == 1 && otherAps.getSelectedIndex()+1 < 3){
                    Toast.makeText(AddCourse.this, "Accounting Requires atleast level 3", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (otherAps.getSelectedIndex() == 2 && otherAps.getSelectedIndex()+1 < 3){
                    Toast.makeText(AddCourse.this, "Physical Science Requires atleast level 3", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (engAps.getSelectedIndex()+1 < 4){
                    Toast.makeText(AddCourse.this, "English Requires atleast level 4", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(totalAps.getText().toString()) > 32){
                    Toast.makeText(AddCourse.this, "APS Must Not Be More That 32", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(code.getText().toString().trim())){
                    code.setError("Please fill fn");
                    return;
                }
                if (TextUtils.isEmpty(name.getText().toString().trim())){
                    name.setError("Please fill fn");
                    return;
                }  if (TextUtils.isEmpty(faculty.getText().toString().trim())){
                    faculty.setError("Please fill fn");
                    return;
                }
                if (TextUtils.isEmpty(info.getText().toString().trim())){
                    info.setError("Please fill fn");
                    return;
                }


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference Courses= database.getReference("Courses");
                Courses.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child(code.getText().toString().toUpperCase()).exists()){
                            if (x==0){
                                Toast.makeText(AddCourse.this, "Course Already Added", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            x=1;
                            UploadVideo();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIDEO_REQUEST && resultCode == RESULT_OK){
            assert data != null;
            videoUri = data.getData();
            assert videoUri != null;
            choose.setText("Video Selected");
        }else {
            Toast.makeText(this, "Please Choose Video", Toast.LENGTH_SHORT).show();
        }
    }
    private void init() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mDialog2 = new ProgressDialog(AddCourse.this);
        math = findViewById(R.id.math);
        mAps = findViewById(R.id.mathaps);
        engAps = findViewById(R.id.englishAps);
        other = findViewById(R.id.other);
        otherAps = findViewById(R.id.otherAps);
        totalAps = findViewById(R.id.Total);
        code = findViewById(R.id.Code);
        name = findViewById(R.id.Name);
        faculty = findViewById(R.id.Fucalty);
        info =findViewById(R.id.Info);
        math.setItems("","Mathematics","Mathematical Literacy");
        mAps.setItems("0","1","2","3","4","5","6","7");
        engAps.setItems("0","1","2","3","4","5","6","7");
        otherAps.setItems("0","1","2","3","4","5","6","7");
        other.setItems("","Accounting","Physical Science");
        submit = findViewById(R.id.Fliter);
        choose = findViewById(R.id.Choose);
    }
    private void UploadVideo(){

        mDialog2.setMessage("Uploading...");
        mDialog2.setCanceledOnTouchOutside(false);
        mDialog2.show();
        final String imageName = String.valueOf(System.currentTimeMillis());
        final StorageReference videofolder = storageReference.child("Video/"+imageName+".mp4");
        videofolder.putFile(videoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                videofolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(final Uri uri) {
                        Paper.init(AddCourse.this);
                        String maths  = "";
                        String others = "";
                        if (math.getSelectedIndex() == 1){
                            maths = "Mathematics";
                        }else {
                            maths = "Mathematical Literacy";
                        }
                        if (other.getSelectedIndex()==1){
                            others= "Accounting";
                        }else{
                            others = "Physical Science";
                        }
                        Course course = new Course();
                        course.setAps(totalAps.getText().toString());
                        course.setCode(code.getText().toString());
                        course.setFaculty(faculty.getText().toString());
                        course.setInfomation(info.getText().toString());
                        course.setMark1(String.valueOf(mAps.getSelectedIndex()));
                        course.setMark2(String.valueOf(engAps.getSelectedIndex()));
                        course.setSubject1(maths);
                        course.setSubject2("English");
                        course.setVideo(uri.toString());
                        course.setName(name.getText().toString());
                        course.setUniversity(Paper.book().read("Result").toString());
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference Courses= database.getReference("Courses");
                        Courses.child(code.getText().toString().toUpperCase()).setValue(course).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                mDialog2.dismiss();
                                Toast.makeText(AddCourse.this, "Course Added SuccessFully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getBaseContext(),AdminHome.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                mDialog2.dismiss();
                                Toast.makeText(AddCourse.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mDialog2.dismiss();
                Toast.makeText(AddCourse.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100)*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount();
                mDialog2.setMessage("Uploaded "+progress + "%");
            }
        });
    }
}
