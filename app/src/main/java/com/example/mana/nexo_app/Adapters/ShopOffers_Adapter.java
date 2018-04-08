package com.example.mana.nexo_app.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mana.nexo_app.Models.ShopData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.Shop_Presneter;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MANA on 3/4/2018.
 */

public class ShopOffers_Adapter extends RecyclerView.Adapter<ShopOffers_Adapter.ShopeOffers_HOlder>{
    Context ctx;
    List<ShopData_Model> mdel;
    View v;
public ShopOffers_Adapter(Context ctx,List<ShopData_Model> mdel)
{
this.ctx=ctx;
this.mdel=mdel;
}


    @Override
    public ShopeOffers_HOlder onCreateViewHolder(ViewGroup parent, int viewType) {
    v = LayoutInflater.from(ctx).inflate(R.layout.single_shop_item,parent,false);
    return new ShopeOffers_HOlder(v);
    }

    @Override
    public void onBindViewHolder(final ShopeOffers_HOlder holder, int position) {
    final ShopData_Model MOdel = mdel.get(position);
    holder.userName.setText(MOdel.getNAME());
        Picasso.with(ctx).load(MOdel.getITEM_IMAGE()).into(holder.shopImage);
        Picasso.with(ctx).load(MOdel.getIMAGE()).into(holder.userImage);
        holder.userName.setText(MOdel.getNAME());
        holder.location.setText("  "+MOdel.getLOCATION());
        holder.Offer.setText("   "+MOdel.getPRICE()+" EGP");
        holder.merchaniseContent.setText(MOdel.getDETAILS());
        final Shop_Presneter presneter = new Shop_Presneter(ctx,null);
        long time = MOdel.getTIME();
        presneter.TransformTime(holder.TiemAgo,time);
        holder.menuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(ctx,holder.menuImage);
                popupMenu.getMenuInflater().inflate(R.menu.context_menu,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId()==R.id.send_mssg)
                        {
                            presneter.intentToMsg(ctx,MOdel.getUSER_ID());
                        }
                        return true;
                    }

                });
                popupMenu.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mdel.size();
    }

    public class  ShopeOffers_HOlder extends RecyclerView.ViewHolder
    {
      private CircleImageView userImage;
      private ImageView shopImage,menuImage;
      private TextView userName,merchaniseContent,TiemAgo,Offer,location;
        public ShopeOffers_HOlder(View itemView) {
            super(itemView);
            userImage= itemView.findViewById(R.id.user_shop_image);
            userName = itemView.findViewById(R.id.user_shop_name);
            merchaniseContent = itemView.findViewById(R.id.shop_post_name);
            TiemAgo = itemView.findViewById(R.id.shop_post_time);
            Offer = itemView.findViewById(R.id.shop_post_offer);
            location = itemView.findViewById(R.id.shop_offer_location);
            shopImage=itemView.findViewById(R.id.shop_post_image);
            menuImage= itemView.findViewById(R.id.shop_item_menu);
        }
    }
}
