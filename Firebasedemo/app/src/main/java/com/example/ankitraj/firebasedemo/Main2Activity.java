package com.example.ankitraj.firebasedemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Main2Activity extends AppCompatActivity {
    EditText e2,e3;
    FirebaseAuth auth;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        e2=(EditText)findViewById(R.id.e2);
        e3=(EditText)findViewById(R.id.e3);
        auth=FirebaseAuth.getInstance();
    }

    public void login(View view) {
        String email=e2.getText().toString();
        String pass=e3.getText().toString();
        if(TextUtils.isEmpty(email))
        {
            e2.setError("Email Required");
            e2.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            e2.setError("Not Valid Email Supplied");
            e2.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(pass))
        {
            e3.setError("Password Required");
            e3.requestFocus();
            return;
        }
        if(pass.length() < 6)
        {
            e3.setError("Min 6 character required");
            e3.requestFocus();
            return;
        }
        pd=new ProgressDialog(this);
        pd.setTitle("Please Wait");
        pd.show();
        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pd.dismiss();

                if(task.isSuccessful())
                {
                    Toast.makeText(Main2Activity.this,"Login Successful",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Main2Activity.this,UserActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(Main2Activity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
