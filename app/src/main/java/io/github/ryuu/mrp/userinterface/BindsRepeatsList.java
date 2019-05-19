package io.github.ryuu.mrp.userinterface;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.github.ryuu.mrp.R;
import io.github.ryuu.mrp.authentication.AESUtil;
import io.github.ryuu.mrp.data.AccountList;
import io.github.ryuu.mrp.data.AccountListAdapter;
import io.github.ryuu.mrp.data.litepal.Account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class BindsRepeatsList extends AppCompatActivity {

    //RecycleView数据列表part1
    private List<AccountList> accountLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binds_repeats_list);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.edit_toolbar);
        setSupportActionBar(toolbar);
        //Toolbar自定义菜单
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }

        //RecycleView数据列表part2
        initAccountList();
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        AccountListAdapter adapter = new AccountListAdapter(accountLists);
        recyclerView.setAdapter(adapter);
    }

    //RecycleView数据列表part3
    private void initAccountList() {
        Intent intent = getIntent();
        String password = intent.getStringExtra("password");
        String phone = intent.getStringExtra("phone");
        String mail = intent.getStringExtra("mail");
        String wechat = intent.getStringExtra("wechat");
        String qq = intent.getStringExtra("qq");


        List<Account> bindedphone;
        if (password!=null) {
            bindedphone = LitePal.select("nickname", "keyword").where("password = ?",
                AESUtil.encrypt(password)).find(Account.class);
            for (Account account : bindedphone) {
                AccountList list = new AccountList(password, AESUtil.decrypt(account.getNickName()), AESUtil.decrypt(account
                        .getKeyword()), account.getId() + "");
                accountLists.add(list);
            }
        }
        if (wechat!=null) {
            bindedphone = LitePal.select("nickname", "keyword").where("wechat = ?",
                    AESUtil.encrypt(wechat)).find(Account.class);
            for (Account account : bindedphone) {
                AccountList list = new AccountList(wechat, AESUtil.decrypt(account.getNickName()), AESUtil.decrypt(account
                        .getKeyword()), account.getId() + "");
                accountLists.add(list);
            }
        }
        if (phone!=null) {
            bindedphone = LitePal.select("nickname", "keyword").where("phone = ?",
                    AESUtil.encrypt(phone)).find(Account.class);
            for (Account account : bindedphone) {
                AccountList list = new AccountList(phone, AESUtil.decrypt(account.getNickName()), AESUtil.decrypt(account
                        .getKeyword()), account.getId() + "");
                accountLists.add(list);
            }
        }
        if (mail!=null) {
            bindedphone = LitePal.select("nickname", "keyword").where("mail = ?",
                    AESUtil.encrypt(mail)).find(Account.class);
            Log.d("找到绑定邮箱的数量", bindedphone.size()+"");
            for (Account account : bindedphone) {
                AccountList list = new AccountList(mail, AESUtil.decrypt(account.getNickName()), AESUtil.decrypt(account
                        .getKeyword()), account.getId() + "");
                if (!"".equals(list.getPsdOrBinds())){
                    accountLists.add(list);
                }

                Log.d("邮箱", mail+"@邮箱");
            }
        }
        if (qq!=null) {
            bindedphone = LitePal.select("nickname", "keyword").where("qq = ?",
                    AESUtil.encrypt(qq)).find(Account.class);
            for (Account account : bindedphone) {
                AccountList list = new AccountList(qq, AESUtil.decrypt(account.getNickName()), AESUtil.decrypt(account
                        .getKeyword()), account.getId() + "");
                accountLists.add(list);
            }
        }

    }


    //菜单项点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }

}
