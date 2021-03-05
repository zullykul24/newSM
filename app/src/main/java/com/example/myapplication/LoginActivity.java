package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button signInTextBtn, signUpTextBtn;
    ConstraintLayout signInLayout, signUpLayout;


    protected void AnhXa(){
        username = (EditText) findViewById(R.id.usernameEditText);
        password = (EditText) findViewById(R.id.passwordEditText);

        signInTextBtn = (Button) findViewById(R.id.signInButton);
        signUpTextBtn = (Button) findViewById(R.id.signUpButton);
        signInLayout = (ConstraintLayout) findViewById(R.id.signInLayout);
        signUpLayout = (ConstraintLayout) findViewById(R.id.signUpLayout);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        AnhXa();

        final FragmentManager fragmentManager = getSupportFragmentManager();

        final FragmentSignIn fragmentSignIn = new FragmentSignIn();
        final FragmentSignUp fragmentSignUp= new FragmentSignUp();


        fragmentManager.beginTransaction().add(R.id.frameSign, fragmentSignIn, "signin").commit();






        signUpTextBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .replace(R.id.frameSign, new FragmentSignUp()).addToBackStack(null).commit();
                    signUpTextBtn.setTextColor(Color.parseColor("#FFFFFF"));
                    signInTextBtn.setTextColor(Color.parseColor("#786464"));
                }
        });

        signInTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.frameSign,new FragmentSignIn()).addToBackStack(null).commit();

                signInTextBtn.setTextColor(Color.parseColor("#FFFFFF"));
                signUpTextBtn.setTextColor(Color.parseColor("#786464"));
            }
        });



    }
/// prevent back to app after sign out
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}