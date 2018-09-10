package com.example.ankitraj.firebasedemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase root1=FirebaseDatabase.getInstance();
    DatabaseReference root=root1.getReference();
    DatabaseReference child5=root.child("Users");
    DatabaseReference child1=root.child("name");
    DatabaseReference child2=child1.child("first name");
    DatabaseReference child3=child1.child("last name");
    ProgressDialog progressDialog;
    FirebaseAuth auth;

    DatabaseReference root3=root1.getReference();
    static int count=2;
    EditText e1,e2,e3,e4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1=(EditText)findViewById(R.id.e1);
        e2=(EditText)findViewById(R.id.e2);
        e3=(EditText)findViewById(R.id.e3);
        e4=(EditText)findViewById(R.id.e4);
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!=null)
        {
            startActivity(new Intent(MainActivity.this,UserActivity.class));
            finish();
        }
        //tv1=(TextView)findViewById(R.id.tv1)
    }

    public void dothis(View view) {
        System.out.println("Hello");
        child2.setValue("ankit");
        child3.setValue("raj");
    }

    public void dothis2(View view) {
        DatabaseReference child4=root3.child("user"+count);
        count++;
        String name=e1.getText().toString();
        String email=e2.getText().toString();
        String password=e3.getText().toString();
        Person p=new Person(name,email,password);
        child4.setValue(p);

    }

    public void dothis3(View view) {
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String name=dataSnapshot.getValue(String.class);
                Log.d("TAG",""+dataSnapshot.getKey());
                Log.d("TAG",""+dataSnapshot.getValue());

                Iterable<DataSnapshot> ir=dataSnapshot.getChildren();
                StringBuffer sb=new StringBuffer();
                for(DataSnapshot child:ir)
                {
                    Person p=child.getValue(Person.class);
                    String s=p.getName()+" "+p.getEmail()+" "+p.getPassword();
                    //sb.append(s+"\n");
                }
                //e1.setText(name);
               // tv1.setText(sb);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Toast.makeText(MainActivity.this," "+dataSnapshot.getKey()+" "+dataSnapshot.getValue(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void Register(View view) {
        final String name=e1.getText().toString();
        String email=e2.getText().toString();
        final String pass=e3.getText().toString();
        String cp=e4.getText().toString();
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
        if(!pass.equals(cp))
        {
            Toast.makeText(MainActivity.this,"Password and conform password does not match",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.show();
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful())
                {
                    String userid=auth.getCurrentUser().getUid();

                    Person person=new Person(name);
                    child5.child(userid).child("name").setValue(name);
                    Toast.makeText(MainActivity.this,"User Account created",Toast.LENGTH_LONG).show();
                    Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this,UserActivity.class));
                    finish();
                }
                else
                {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(MainActivity.this,"User already exists",Toast.LENGTH_LONG).show();
                    }
                    else
                    Toast.makeText(MainActivity.this,""+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void already(View view) {
        Intent it=new Intent(MainActivity.this,Main2Activity.class);
        startActivity(it);
    }
}
