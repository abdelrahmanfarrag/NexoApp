package com.example.mana.nexo_app.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mana.nexo_app.Interfaces.msgView;
import com.example.mana.nexo_app.Models.MsgData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.ChatPresenter;
import com.example.mana.nexo_app.utils.GetTimeAgo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MANA on 2/5/2018.
 */

public class Message_Adapter extends RecyclerView.Adapter<Message_Adapter.MessageHolder>{
    View v;
    Context ctx;
    List<MsgData_Model>list;
    msgView inter=null;
    public Message_Adapter(Context ctx,List<MsgData_Model>list)
    {
        this.list=list;
        this.ctx=ctx;
    }


    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v= LayoutInflater.from(ctx).inflate(R.layout.singlemsg,parent,false);
        return new MessageHolder(v);
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        ChatPresenter presenter=new ChatPresenter(ctx,inter);
        MsgData_Model model=list.get(position);
        holder.msg.setText(model.getMSG());
        GetTimeAgo timeAgo = new GetTimeAgo();
        long msg_added =model.getSENT();
        String msg_at =timeAgo.getTimeAgo(msg_added,ctx);
        holder.time.setText(msg_at);
        presenter.changeParams(model.getID(),holder.userImage,holder.time,holder.msg);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MessageHolder extends RecyclerView.ViewHolder
    {
        private TextView msg,time;
        private CircleImageView userImage;
        private RelativeLayout mycard;

        public MessageHolder(View itemView) {
            super(itemView);
            msg=itemView.findViewById(R.id.msgcontent);
            time=itemView.findViewById(R.id.userchat_AGO);
            userImage=itemView.findViewById(R.id.userchat_image);
            mycard=itemView.findViewById(R.id.contentv);
        }
    }

}
