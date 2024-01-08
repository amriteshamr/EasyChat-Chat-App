package com.example.easychat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class chatWin extends AppCompatActivity {

    String recImg, recUid, recName, senUid;

    CircleImageView profile;
    TextView recNamee;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_win);

        recName = getIntent().getStringExtra("nameee");
        recImg = getIntent().getStringExtra("recImg");
        recUid = getIntent().getStringExtra("uid");
        profile = findViewById(R.id.profileImg);

        recNamee = findViewById(R.id.receiverName);

        Picasso.get().load(recImg).into(profile);
        recNamee.setText(""+recName);



    }
}