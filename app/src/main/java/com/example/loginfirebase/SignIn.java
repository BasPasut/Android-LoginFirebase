package com.example.loginfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView email;
    private Button newPost,viewPost,location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        email = (TextView) findViewById(R.id.email);
        email.setText(mAuth.getCurrentUser().getEmail());

        newPost = (Button) findViewById(R.id.newpost);
        viewPost = (Button) findViewById(R.id.viewpost);
        location = (Button) findViewById(R.id.location);

        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            email.setText("Welcome " + user.getEmail());
        }

        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NewPost.class));
            }
        });

        viewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewPost.class));
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewLocation.class));
            }
        });

    }

    public void Logout(View view) {
        mAuth.signOut();
        Intent i = new Intent(SignIn.this, MainActivity.class);
        finish();
        startActivity(i);
    }
}