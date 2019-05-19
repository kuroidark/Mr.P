package io.github.ryuu.mrp.userinterface;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.google.android.material.textfield.TextInputEditText;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import io.github.ryuu.mrp.R;
import io.github.ryuu.mrp.authentication.AESUtil;
import io.github.ryuu.mrp.data.litepal.Account;

public class AccountEdit extends AppCompatActivity {

    private TextInputEditText nickName;
    private TextInputEditText loginName;
    private TextInputEditText password;
    private TextInputEditText appName;
    private TextInputEditText phone;
    private TextInputEditText mail;
    private TextInputEditText qq;
    private TextInputEditText wechat;
    private TextInputEditText keyword;
    private boolean edit = false;
    private int id;
    private Spinner spinner;
    private List<Map<String, Object>> data;
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_edit);

//        LitePal.aesKey("Admin_8020010");

        nickName = findViewById(R.id.nickName);
        loginName = findViewById(R.id.loginName);
        password = findViewById(R.id.password);
        appName = findViewById(R.id.appName);
        phone = findViewById(R.id.phone);
        mail = findViewById(R.id.mail);
        qq = findViewById(R.id.qq);
        wechat = findViewById(R.id.wechat);
        keyword = findViewById(R.id.keyword);
        spinner = findViewById(R.id.spinner);


        //Spinner数据源
        data = new ArrayList<>();
        //创建SimpleAdapter适配器
        //上下文，数据源，item子布局，键值对
        final SimpleAdapter s_adapter = new SimpleAdapter(this, getDat(), R.layout.tag_color, new String[]{"image"}, new int[]{R.id.tag_img});
        //控件与适配器绑定
        spinner.setAdapter(s_adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(AccountEdit.this,s_adapter.getItemId(position)+"",Toast.LENGTH_SHORT).show();
                color = (int) s_adapter.getItemId(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Toolbar
        Toolbar toolbar = findViewById(R.id.edit_toolbar);
        setSupportActionBar(toolbar);
        //Toolbar自定义菜单
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null ) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }

        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);
        if (id != -1){
            edit = true;
            querry(id);
        }
    }

    //Spinner数据
    private List<Map<String, Object>> getDat() {
        Map<String, Object> map = new HashMap<>();
        map.put("image",R.drawable.gray);
        data.add(map);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("image",R.drawable.pink);
        data.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("image",R.drawable.blue);
        data.add(map2);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("image",R.drawable.green);
        data.add(map3);
        Map<String, Object> map4 = new HashMap<>();
        map4.put("image",R.drawable.yellow);
        data.add(map4);
        Map<String, Object> map5 = new HashMap<>();
        map5.put("image",R.drawable.orange);
        data.add(map5);
        Map<String, Object> map6 = new HashMap<>();
        map6.put("image",R.drawable.red);
        data.add(map6);
        return data;
    }

    //绑定Toolbar菜单项
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_toolbar, menu);
        return true;
    }

    //菜单项点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.edit_confirm:
                //判断必填项是否填写
                if (appName.getText().toString().equals("")||nickName.getText().toString().equals("")||keyword.getText().toString().equals("")){
                    if (appName.getText().toString().equals(""))
                        appName.setError("这是必要的");
                    if (nickName.getText().toString().equals(""))
                        nickName.setError("这是必要的");
                    if (keyword.getText().toString().equals(""))
                        keyword.setError("这是必要的");
                }else{
                    Log.d("appname", appName.getText().toString()+" 是这么多");
                    //保存数据库操作
                    if (edit == false){
                        addAccount();
                    }

                    else if (edit == true){
                        editAccount();
                    }
                    finish();
                }
                break;
            default:
        }
        return true;
    }

    private void addAccount() {
        Account account = new Account();
        account.setAppName(AESUtil.encrypt(appName.getText().toString()));
        account.setKeyword(AESUtil.encrypt(keyword.getText().toString()));
        account.setLoginName(AESUtil.encrypt(loginName.getText().toString()));
        account.setMail(AESUtil.encrypt(mail.getText().toString()));
        account.setNickName(AESUtil.encrypt(nickName.getText().toString()));
        account.setPassword(AESUtil.encrypt(password.getText().toString()));
        account.setPhone(AESUtil.encrypt(phone.getText().toString()));
        account.setQq(AESUtil.encrypt(qq.getText().toString()));
        account.setWechat(AESUtil.encrypt(wechat.getText().toString()));
        account.setColor(color);
        account.save();
    }

    private void editAccount() {
        Account account = new Account();
        account.setAppName(AESUtil.encrypt(appName.getText().toString()));
        account.setKeyword(AESUtil.encrypt(keyword.getText().toString()));
        account.setLoginName(AESUtil.encrypt(loginName.getText().toString()));
        account.setMail(AESUtil.encrypt(mail.getText().toString()));
        account.setNickName(AESUtil.encrypt(nickName.getText().toString()));
        account.setPassword(AESUtil.encrypt(password.getText().toString()));
        account.setPhone(AESUtil.encrypt(phone.getText().toString()));
        account.setQq(AESUtil.encrypt(qq.getText().toString()));
        account.setWechat(AESUtil.encrypt(wechat.getText().toString()));
        account.setColor(color);
        account.updateAll("id = ?", id+"");
    }

    private void querry(int id) {
        List<Account> accounts = LitePal.where("id = ?", id+"").find(Account.class);
        for (Account account: accounts) {
            appName.setText(AESUtil.decrypt(account.getAppName()));
            nickName.setText(AESUtil.decrypt(account.getNickName()));
            loginName.setText(AESUtil.decrypt(account.getLoginName()));
            password.setText(AESUtil.decrypt(account.getPassword()));
            qq.setText(AESUtil.decrypt(account.getQq()));
            wechat.setText(AESUtil.decrypt(account.getWechat()));
            mail.setText(AESUtil.decrypt(account.getMail()));
            phone.setText(AESUtil.decrypt(account.getPhone()));
            keyword.setText(AESUtil.decrypt(account.getKeyword()));
            setSpinnerItemSelectedByValue(spinner, account.getColor());
        }
    }

    //根据值设置spinner选中
    private void setSpinnerItemSelectedByValue(Spinner spinner, int value) {
        SpinnerAdapter adapter = spinner.getAdapter();
        int k = adapter.getCount();
        for (int i = 0; i < k; i++) {
            if (value==(int) adapter.getItemId(i)) {
                spinner.setSelection(i);
                break;
            }
        }
    }
}
