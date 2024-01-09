package com.example.easychat;

import static com.example.easychat.chatWin.receiverImg;
import static com.example.easychat.chatWin.senderImg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class messagesAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<msgModelClass> messagesAdapterArrayList;
    int ITEM_SEND = 1;
    int ITEM_RECIEVE = 2;


    public messagesAdapter(Context context, ArrayList<msgModelClass> messagesAdapterArrayList) {
        this.context = context;
        this.messagesAdapterArrayList = messagesAdapterArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_SEND){
            View view = LayoutInflater.from(context).inflate(R.layout.sender_layout,parent,false);
            return new senderViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.reciever_layout,parent,false);
            return new recieverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        msgModelClass messages = messagesAdapterArrayList.get(position);
        if (holder.getClass()==senderViewHolder.class){
            senderViewHolder viewHolder = (senderViewHolder) holder;
            viewHolder.msgText.setText(messages.getMessage());
            Picasso.get().load(senderImg).into(viewHolder.circleImageView);
        }
        else{
            recieverViewHolder viewHolder = (recieverViewHolder) holder;
            viewHolder.msgText.setText(messages.getMessage());
            Picasso.get().load(receiverImg).into(viewHolder.circleImageView);
        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        msgModelClass messages = messagesAdapterArrayList.get(position);
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderId())){
            return ITEM_SEND;
        }
        else{
            return ITEM_RECIEVE;
        }
    }

    class senderViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView msgText;
        public senderViewHolder(@NonNull View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.senderImage);
            msgText = itemView.findViewById(R.id.msgsendertyp);
        }
    }

    class recieverViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView msgText;
        public recieverViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.recieverImage);
            msgText = itemView.findViewById(R.id.recivertextset);
        }
    }


}
