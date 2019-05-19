package io.github.ryuu.mrp.data;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import io.github.ryuu.mrp.R;
import io.github.ryuu.mrp.userinterface.BindsRepeatsList;

public class BindCardListAdapter extends RecyclerView.Adapter<BindCardListAdapter.ViewHolder> {

    private List<BindCardList> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {

//        View clickView;
        TextView type;
        TextView value;
        TextView times;
        Button viewButton;
        MaterialCardView card;

        public ViewHolder(View view) {
            super(view);
//            clickView = view;
            type = view.findViewById(R.id.bind_type);
            value = view.findViewById(R.id.bind_value);
            times = view.findViewById(R.id.bind_times);
            viewButton = view.findViewById(R.id.button_view);
            card = view.findViewById(R.id.card_see);
        }

    }

    public BindCardListAdapter(List<BindCardList> cardLists) {
        mList = cardLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bind_card_list, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                BindCardList list = mList.get(position);
                Intent intent = new Intent(view.getContext(), BindsRepeatsList.class);
                if (!"".equals(list.getPhone())){
                    intent.putExtra("phone", list.getPhone());
                    Log.d("手机号",list.getPhone());
                }
                if (!"".equals(list.getMail())){
                    intent.putExtra("mail", list.getMail());
                }
                if (!"".equals(list.getWechat())){
                    intent.putExtra("wechat", list.getWechat());
                }
                if (!"".equals(list.getQq())){
                    intent.putExtra("qq", list.getQq());
                }
                view.getContext().startActivity(intent);
            }
        });
        holder.viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                BindCardList list = mList.get(position);
                Intent intent = new Intent(view.getContext(), BindsRepeatsList.class);
                if (!"".equals(list.getPhone())){
                    intent.putExtra("phone", list.getPhone());
                }
                if (!"".equals(list.getMail())){
                    intent.putExtra("mail", list.getMail());
                }
                if (!"".equals(list.getWechat())){
                    intent.putExtra("wechat", list.getWechat());
                }
                if (!"".equals(list.getQq())){
                    intent.putExtra("qq", list.getQq());
                }
                view.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BindCardList list = mList.get(position);
        if (!"".equals(list.getPhone())){
            holder.type.setText("手机");
            holder.value.setText(list.getPhone());
            holder.times.setText(list.getTimes());
        }
        if (!"".equals(list.getMail())){
            holder.type.setText("邮箱");
            holder.value.setText(list.getMail());
            holder.times.setText(list.getTimes());
        }
        if (!"".equals(list.getQq())){
            holder.type.setText("QQ");
            holder.value.setText(list.getQq());
            holder.times.setText(list.getTimes());
        }
        if (!"".equals(list.getWechat())){
            holder.type.setText("微信");
            holder.value.setText(list.getWechat());
            holder.times.setText(list.getTimes());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
