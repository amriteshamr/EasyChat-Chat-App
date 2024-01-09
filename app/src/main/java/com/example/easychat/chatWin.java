package com.example.easychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class chatWin extends AppCompatActivity {

    String recImg, recUid, recName, senUid;

    CircleImageView profile;
    TextView recNamee;
    CardView sendBtn;
    EditText textMsg;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    public static String senderImg;
    public static String receiverImg;
    String senderRoom, recieverRoom;
    RecyclerView messageAdapter;
    ArrayList<msgModelClass> messagesArrayList;

    messagesAdapter msgAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_win);

        messageAdapter = findViewById(R.id.msgAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messageAdapter.setLayoutManager(linearLayoutManager);
        msgAdapter = new messagesAdapter(chatWin.this, messagesArrayList);
        messageAdapter.setAdapter(msgAdapter);


        recName = getIntent().getStringExtra("nameee");
        recImg = getIntent().getStringExtra("recImg");
        recUid = getIntent().getStringExtra("uid");

        messagesArrayList = new ArrayList<>();

        sendBtn = findViewById(R.id.sendbtn);
        textMsg = findViewById(R.id.textMsg);


        profile = findViewById(R.id.profileImg);
        recNamee = findViewById(R.id.receiverName);

        Picasso.get().load(recImg).into(profile);
        recNamee.setText(""+recName);

        DatabaseReference reference = database.getReference().child("user").child(firebaseAuth.getUid());
        DatabaseReference chatReference = database.getReference().child("user").child(senderRoom).child("messages");

        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    msgModelClass messages = dataSnapshot.getValue(msgModelClass.class);
                    messagesArrayList.add(messages);
                }

                msgAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                senderImg = snapshot.child("profilepic").getValue().toString();
                receiverImg = recImg;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        senUid = firebaseAuth.getUid();
        senderRoom = senUid+recUid;
        recieverRoom = recUid+senUid;


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = textMsg.getText().toString();
                if(message.isEmpty()){
                    Toast.makeText(chatWin.this, "Type some message first!", Toast.LENGTH_SHORT).show();

                }
                textMsg.setText("");
                Date date = new Date();
                msgModelClass messagess = new msgModelClass(message,senUid,date.getTime());
                database = FirebaseDatabase.getInstance();
                database.getReference().child("chats").child("senderRoom").child("messages").push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        database.getReference().child("chats").child("recieverRoom").child("messages").push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                });

            }
        });



    }
}