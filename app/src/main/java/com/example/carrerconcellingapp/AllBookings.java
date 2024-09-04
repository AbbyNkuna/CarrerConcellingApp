package com.example.carrerconcellingapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.carrerconcellingapp.Interface.ItemClickListener;
import com.example.carrerconcellingapp.Model.Booking;
import com.example.carrerconcellingapp.Model.Course;
import com.example.carrerconcellingapp.Model.User;
import com.example.carrerconcellingapp.ViewHolder.BookingViewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;

public class AllBookings extends AppCompatActivity {
    RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Booking, BookingViewholder> adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Bookings");
    FloatingActionButton add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_bookings);
        init();
        Paper.init(this);
        insertvalues(Paper.book().read("CODE").toString());

    }

    private void insertvalues(final String userID) {
        adapter = new FirebaseRecyclerAdapter<Booking, BookingViewholder>(Booking.class,R.layout.bookingview,BookingViewholder.class,reference1.orderByChild("course").equalTo(userID)) {
            @Override
            protected void populateViewHolder(final BookingViewholder bookingViewholder, final Booking booking, int i) {
                DatabaseReference Courses= database.getReference("Courses");
                Courses.addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Course course = snapshot.child(booking.getCourse()).getValue(Course.class);
                        if (course!=null){
                            DatabaseReference users= database.getReference("Users");
                            users.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User user = snapshot.child(booking.getUserID()).getValue(User.class);
                                    if (user!=null){
                                        bookingViewholder.name.setText("Personal Informatiom : "+user.getName()+ " "+user.getSurname());
                                        bookingViewholder.code.setText("Contact Number : "+user.getContact());
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                            if (!booking.getDate().equals("")){
                                bookingViewholder.date.setText("Meeting Date : "+booking.getDate() + "  Time :"+booking.getTime());
                                bookingViewholder.link.setText("Meeting Link : "+booking.getLink());

                            }else{
                                bookingViewholder.link.setText("Meeting Link : No Link Submitted Yet");
                                bookingViewholder.date.setText("Meeting Date : Date Not Submitted Yet");
                            }
                            bookingViewholder.approval.setText("Approval : "+booking.getApproval());
                            if(booking.getApproval().equals("Yes")){
                                bookingViewholder.Submit.setText("Start Meeting");
                                Calendar c = Calendar.getInstance();
                                String currentDate = String.valueOf(c.get(Calendar.DAY_OF_MONTH)+"/"+String.valueOf(c.get(Calendar.MONTH)+1))+"/"+String.valueOf(c.get(Calendar.YEAR));
                                final String Calenderdate = adapter.getItem(i).getDate();
                                if (getDaysBetweenDates(currentDate,Calenderdate) <0){
                                    bookingViewholder.Submit.setVisibility(View.GONE);
                                    bookingViewholder.date.setText("Meeting Date : Date has Passed");
                                }

                            }

                        }
                        bookingViewholder.Submit.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onClick(View v) {
                                if (adapter.getItem(i).getApproval().equals("Not Yet")){
                                    Paper.init(AllBookings.this);
                                    Paper.book().write("BookKey", Objects.requireNonNull(adapter.getRef(i).getKey()));
                                    startActivity(new Intent(getBaseContext(),DateChossen.class));
                                }
                                if (adapter.getItem(i).getApproval().equals("Yes")){
                                    Calendar c = Calendar.getInstance();
                                    String currentDate = String.valueOf(c.get(Calendar.DAY_OF_MONTH)+"/"+String.valueOf(c.get(Calendar.MONTH)+1))+"/"+String.valueOf(c.get(Calendar.YEAR));
                                    final String Calenderdate = adapter.getItem(i).getDate();

                                    SimpleDateFormat formattter = new SimpleDateFormat("HHmm");
                                    Date date = new Date();
                                    String Time = String.valueOf(formattter.format(date));
                                    String[] BookTime= adapter.getItem(i).getTime().split(":");
                                    String Hour =  BookTime[0];
                                    String Minutes = BookTime[1];
                                    String TotalTime=Hour+Minutes;
                                    URL serverURL;


                                    try {
                                        serverURL = new URL("https://meet.jit.si");
                                        JitsiMeetConferenceOptions defaultOptions =
                                                new JitsiMeetConferenceOptions.Builder()
                                                        .setServerURL(serverURL)

                                                        .build();
                                        JitsiMeet.setDefaultConferenceOptions(defaultOptions);
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }
                                    JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                                            .setRoom("0844186854")
                                            .build();

                                    JitsiMeetActivity.launch(AllBookings.this, options);
                                }


                            }
                        });
                        bookingViewholder.setItemClickListener(new ItemClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onClick(View view, int Position, Boolean isLongClick) {


                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    private void init() {
        recyclerView = findViewById(R.id.recycleadd);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static long getDaysBetweenDates(String start, String end){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date startDate;
        Date endDate;
        long numberOfDays = 0;
        try {
            startDate = dateFormat.parse(start);
            endDate =dateFormat.parse(end);
            numberOfDays = getUnitBetweenDates(startDate,endDate, TimeUnit.DAYS);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return numberOfDays;

    }
    private static  long getUnitBetweenDates(Date startDate, Date endDate, TimeUnit unit){
        long timeDiff = endDate.getTime() - startDate.getTime();
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS);
    }
}
