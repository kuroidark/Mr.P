package io.github.ryuu.mrp.data;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.github.ryuu.mrp.R;
import io.github.ryuu.mrp.userinterface.BindsRepeatsList;

public class RepeatPasswordCardListAdapter extends RecyclerView.Adapter<RepeatPasswordCardListAdapter.ViewHolder> {

    private List<RepeatPasswordCardList> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        View repeatView;
        TextView password;
        TextView times;
        Button viewButton;

        public ViewHolder(View view) {
            super(view);
            repeatView = view;
            password = view.findViewById(R.id.password);
            times = view.findViewById(R.id.repeat_times);
            viewButton = view.findViewById(R.id.view_button);
        }
    }

    public RepeatPasswordCardListAdapter(List<RepeatPasswordCardList> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repeat_password_card_list, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.repeatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                RepeatPasswordCardList repeatPasswordCardList = mList.get(position);
                Intent intent = new Intent(v.getContext(), BindsRepeatsList.class);
                intent.putExtra("password", repeatPasswordCardList.getPassword());
                Log.d("密码：", repeatPasswordCardList.getPassword());
                v.getContext().startActivity(intent);
            }
        });
        holder.viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                RepeatPasswordCardList repeatPasswordCardList = mList.get(position);
                Intent intent = new Intent(v.getContext(), BindsRepeatsList.class);
                intent.putExtra("password", repeatPasswordCardList.getPassword());
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RepeatPasswordCardList list = mList.get(position);
        holder.password.setText(list.getPassword());
        holder.times.setText(list.getRepeatTimes());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
