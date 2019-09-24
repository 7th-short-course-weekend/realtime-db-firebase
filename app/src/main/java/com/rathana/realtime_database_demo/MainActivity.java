package com.rathana.realtime_database_demo;


import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathana.realtime_database_demo.model.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    static final String MESSAGE_NODE = "messages";
    DatabaseReference messageRef = database.getReference(MESSAGE_NODE);

    EditText etMessage;
    TextView tvMessage;
    Button btnSend;

    List<Message> messages=new ArrayList<>();

    //setup recycler View

    RecyclerView rvMessage;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMessage = findViewById(R.id.etMessage);
        tvMessage = findViewById(R.id.message);
        btnSend=findViewById(R.id.btnSend);


        //setup UI

        rvMessage=findViewById(R.id.rvMessage);
        rvMessage.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter=new MessageAdapter(messages);
        rvMessage.setAdapter(messageAdapter);

        //write

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message=new Message();
                message.setMessage(etMessage.getText().toString());
                message.setUserId("123");
                message.setDateCreated(System.currentTimeMillis());
                //messageRef.setValue(message);
                saveMessage(messageRef,message);
            }
        });

        //read
//        messageRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                //String message = (String) dataSnapshot.getValue();
////
////                Message message = dataSnapshot.getValue(Message.class);
////                if(message!=null)
////                    tvMessage.setText(message.getMessage()!=null?message.getMessage():"");
////
//
//                Iterator<DataSnapshot> data = dataSnapshot.getChildren().iterator();
//                //dataSnapshot = data.next();
//                while (data.hasNext()){
//                    Message message= data.next().getValue(Message.class);
//                    messages.add(message);
//                }
//
//                Log.e("MainActivity", "onDataChange: "+messages );
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        messageRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> data = dataSnapshot.getChildren().iterator();
                //dataSnapshot = data.next();

                while (data.hasNext()){
                    Message message= data.next().getValue(Message.class);
                    messages.add(message);
                    messageAdapter.addItems(messages);
                }

                Log.e("messages size", "onDataChange: "+messages.size() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void saveMessage(DatabaseReference ref,Message message){

        String key = ref.child(MESSAGE_NODE).push().getKey();
        Map<String ,Object> updateValues=new HashMap<>();
        updateValues.put(key,message.toMap());
        ref.updateChildren(updateValues);

    }
}
