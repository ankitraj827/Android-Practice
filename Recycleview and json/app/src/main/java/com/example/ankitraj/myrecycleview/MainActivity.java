package com.example.ankitraj.myrecycleview;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String URL_DATA="https://api.myjson.com/bins/aknt0";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=(RecyclerView)findViewById(R.id.rev);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems=new ArrayList<>();
        
        loadrecyclerViewData();
    }

    private void loadrecyclerViewData() {
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Please wait Loading Data..");
        pd.show();

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd.dismiss();
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("student");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String name=jsonObject1.getString("name");
                        String mob=jsonObject1.getString("mob");
                        String desc="fdkjbdk";
                        ListItem item=new ListItem(name,mob,desc);
                        listItems.add(item);
                    }
                    adapter=new MyAdapter(listItems,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }

                catch (JSONException e) {
                    pd.dismiss();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(MainActivity.this,""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void addinfo(View view) {
        AlertDialog.Builder ad=new AlertDialog.Builder(this);
        final View v=getLayoutInflater().inflate(R.layout.my,null);
        ad.setView(v);
        ad.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText ed1=(EditText)v.findViewById(R.id.ed1);
                EditText ed2=(EditText)v.findViewById(R.id.ed2);
                EditText ed3=(EditText)v.findViewById(R.id.ed3);
                String name=ed1.getText().toString();
                String mob=ed2.getText().toString();
                String desc=ed3.getText().toString();
                ListItem listItem=new ListItem(name,mob,desc);
                listItems.add(listItem);
                adapter=new MyAdapter(listItems,getApplicationContext());
                recyclerView.setAdapter(adapter);
            }
        });
        ad.show();
    }
}
