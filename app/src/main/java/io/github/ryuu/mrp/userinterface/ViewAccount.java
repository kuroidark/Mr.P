package io.github.ryuu.mrp.userinterface;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import io.github.ryuu.mrp.R;
import io.github.ryuu.mrp.authentication.AESUtil;
import io.github.ryuu.mrp.data.litepal.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.litepal.LitePal;

import java.util.List;

public class ViewAccount extends AppCompatActivity {

    private TextView appName;
    private TextView nickName;
    private TextView loginName;
    private TextView password;
    private TextView qq;
    private TextView wechat;
    private TextView mail;
    private TextView phone;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account);
        appName = findViewById(R.id.appName);
        nickName = findViewById(R.id.nickName);
        loginName = findViewById(R.id.loginName);
        password = findViewById(R.id.password);
        qq = findViewById(R.id.qq);
        wechat = findViewById(R.id.wechat);
        mail = findViewById(R.id.mail);
        phone = findViewById(R.id.phone);

        id = init();

        //Toolbar
        Toolbar toolbar = findViewById(R.id.view_toolbar);
        setSupportActionBar(toolbar);
        //Toolbar自定义菜单
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null ) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
    }

    //绑定Toolbar菜单项
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_account, menu);
        return true;
    }

    //菜单项点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.account_edit:
                //打开编辑页
                Intent intent = new Intent(this, AccountEdit.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
            case R.id.account_delete:
                //删除这个账号
                LitePal.deleteAll(Account.class, "id = ?", id+"");
                finish();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        id = init();
    }

    private int init() {
        Intent intent = getIntent();
        int id = 0;
        String idGet = intent.getStringExtra("idGet");
        if (!"".equals(idGet)) {
            List<Account> accounts = LitePal.where("id = ?", idGet).find(Account.class);
            for (Account account: accounts) {
                if (account.getAppName()==""){
                    appName.setVisibility(View.GONE);
                }
                if (account.getNickName()==""){
                    nickName.setVisibility(View.GONE);
                }
                if (account.getLoginName()==""){
                    loginName.setVisibility(View.GONE);
                }
                if (account.getPassword()==""){
                    password.setVisibility(View.GONE);
                }
                if (account.getQq().equals("")){
                    qq.setVisibility(View.GONE);
                }
                if (account.getWechat().equals("")){
                    wechat.setVisibility(View.GONE);
                }
                if (account.getMail().equals("")){
                    mail.setVisibility(View.GONE);
                }
                if (account.getPhone().equals("")){
                    phone.setVisibility(View.GONE);
                }

                appName.setText(AESUtil.decrypt(account.getAppName()));
                nickName.setText(AESUtil.decrypt(account.getNickName()));
                loginName.setText(AESUtil.decrypt(account.getLoginName()));
                password.setText(AESUtil.decrypt(account.getPassword()));
                qq.setText(AESUtil.decrypt(account.getQq()));
                wechat.setText(AESUtil.decrypt(account.getWechat()));
                mail.setText(AESUtil.decrypt(account.getMail()));
                phone.setText(AESUtil.decrypt(account.getPhone()));
                id = account.getId();
            }
        }

        return id;
    }

}
