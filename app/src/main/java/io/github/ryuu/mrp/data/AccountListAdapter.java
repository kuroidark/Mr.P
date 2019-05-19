package io.github.ryuu.mrp.data;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import io.github.ryuu.mrp.R;
import io.github.ryuu.mrp.userinterface.ViewAccount;

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.ViewHolder> {

    private List<AccountList> mAccountList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View accountListView;
        TextView psdOrBinds;
        TextView nickname;
        TextView info;

        public ViewHolder(View view) {
            super(view);
            accountListView = view;
            psdOrBinds = view.findViewById(R.id.psd_or_binds);
            nickname = view.findViewById(R.id.nickname);
            info = view.findViewById(R.id.keywords);
        }
    }

    public AccountListAdapter(List<AccountList> accountLists) {
        mAccountList = accountLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accounts_item,
                parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.accountListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                AccountList account = mAccountList.get(position);
                Intent intent = new Intent(view.getContext(), ViewAccount.class);
                intent.putExtra("idGet", account.getId());
                view.getContext().startActivity(intent);
                Log.d("获取到id", account.getId());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AccountList accountList = mAccountList.get(position);
        holder.psdOrBinds.setText(accountList.getPsdOrBinds());
        holder.nickname.setText(accountList.getNickname());
        holder.info.setText(accountList.getInfo());
    }

    @Override
    public int getItemCount() {
        return mAccountList.size();
    }
}
