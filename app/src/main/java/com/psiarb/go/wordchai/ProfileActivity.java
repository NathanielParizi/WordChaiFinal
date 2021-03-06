package com.psiarb.go.wordchai;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {


    private ImageView mProfileImageView;
    private TextView mName;
    private TextView mStatus;
    private TextView mLocation;
    private TextView mAge;
    private TextView mGender;
    private TextView mLang;
    private TextView mInterests;
    private DatabaseReference mFriendReqDatabase;


    private Button mProfileSendReqBtn;


    private ProgressDialog mProgressDialog;

    private DatabaseReference mUsersDatabase;
    private FirebaseUser mCurrent_user;


    private String mCurrent_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



//Firebase
        final String user_id = getIntent().getStringExtra("user_id");
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        mFriendReqDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_req");
        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();

        mProfileImageView = (ImageView) findViewById(R.id.profile_image);
        mName = (TextView) findViewById(R.id.Name);
        mStatus = (TextView) findViewById(R.id.Status);
        mLocation = (TextView) findViewById(R.id.Location);
        mAge = (TextView) findViewById(R.id.Age);
        mGender = (TextView) findViewById(R.id.Gender);
        mLang = (TextView) findViewById(R.id.lang);
        mInterests = (TextView) findViewById(R.id.Interests);
        


        mCurrent_state = "not friends";

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading User Data");
        mProgressDialog.setMessage("Please wait while we load your information!");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();




        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String displayName = dataSnapshot.child("name").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                String location = dataSnapshot.child("location").getValue().toString();
                String age = dataSnapshot.child("age").getValue().toString();
                String lang = dataSnapshot.child("lang").getValue().toString();
                String gender = dataSnapshot.child("gender").getValue().toString();
                String interests = dataSnapshot.child("interests").getValue().toString();

                mName.setText(displayName);
                mStatus.setText(status);
                mLocation.setText("From: " + location);
                mAge.setText("Age: " + age);
                mGender.setText(gender);
                mLang.setText(lang);
                mInterests.setText("My interests: " + interests);

                Picasso.get().load(image).placeholder(R.drawable.defaultprofileuser).into(mProfileImageView);

                mProgressDialog.dismiss();


            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }




}