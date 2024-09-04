package com.example.carrerconcellingapp;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.carrerconcellingapp.Interface.ItemClickListener;
import com.example.carrerconcellingapp.Model.Course;
import com.example.carrerconcellingapp.Model.University;
import com.example.carrerconcellingapp.ViewHolder.CourseViewHolder;
import com.example.carrerconcellingapp.ViewHolder.UniViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

import io.paperdb.Paper;

public class AdminHome extends AppCompatActivity {
    RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<University, UniViewHolder> adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference Courses= database.getReference("Univen");
    FloatingActionButton add,Jobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        init();
        insertvalues();
        add = findViewById(R.id.add);
        Jobs = findViewById(R.id.Jobs);
        Jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this,Jobs.class));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),AddUnivsity.class));
            }
        });
    }

    private void insertvalues() {
        adapter = new FirebaseRecyclerAdapter<University, UniViewHolder>(University.class,R.layout.universitylayout,UniViewHolder.class,Courses) {
            @Override
            protected void populateViewHolder(UniViewHolder uniViewHolder, University university, int i) {
                uniViewHolder.name.setText(university.getName().toUpperCase(Locale.ROOT));
                uniViewHolder.Req.setText("The Minimum Requirement Is : "+ university.getMinReq());
                uniViewHolder.Delete.setVisibility(View.VISIBLE);
                uniViewHolder.Delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Courses.child(adapter.getRef(i).getKey()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(AdminHome.this, "University Deleted", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                uniViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int Position, Boolean isLongClick) {
                        Paper.init(AdminHome.this);
                        Paper.book().write("Result",adapter.getRef(Position).getKey());
                        startActivity(new Intent(AdminHome.this,CourseList.class));
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void init() {
        recyclerView = findViewById(R.id.recycleadd);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

}
