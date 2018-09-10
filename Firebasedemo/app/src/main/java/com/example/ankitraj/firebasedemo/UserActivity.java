package com.example.ankitraj.firebasedemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class UserActivity extends AppCompatActivity {
    FirebaseAuth Auth;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference reference=firebaseDatabase.getReference();
    DatabaseReference child=reference.child("Users");
    TextView tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Auth=FirebaseAuth.getInstance();
        tv2=(TextView)findViewById(R.id.tv2);

        if(Auth.getCurrentUser()!=null)
        {
            displayName();
        }
    }

    private void displayName() {
        child.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot ds=dataSnapshot.child(Auth.getCurrentUser().getUid());

                Person p=ds.getValue(Person.class);

                String name=p.getName();
                tv2.setText("\t"+name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void add(View view) {
        Toast.makeText(UserActivity.this,"Hello",Toast.LENGTH_LONG).show();
        AlertDialog.Builder ad=new AlertDialog.Builder(this);
        View v=getLayoutInflater().inflate(R.layout.my_layout,null);

        final EditText ed1=v.findViewById(R.id.ed1);

        ad.setView(v);

        ad.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String info=ed1.getText().toString();
                String uid=Auth.getCurrentUser().getUid();
                child.child(uid).child("name").setValue(tv2.getText().toString()+"\n"+info);
                dialogInterface.cancel();
            }
        });
        ad.show();
    }

    public void logout(View view) {
        Auth.signOut();
        startActivity(new Intent(UserActivity.this,MainActivity.class));
        finish();
    }
}
