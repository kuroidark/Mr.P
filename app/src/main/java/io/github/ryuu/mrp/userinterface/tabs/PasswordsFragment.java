package io.github.ryuu.mrp.userinterface.tabs;


import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import io.github.ryuu.mrp.R;
import io.github.ryuu.mrp.authentication.AESUtil;
import io.github.ryuu.mrp.data.RepeatPasswordCardList;
import io.github.ryuu.mrp.data.RepeatPasswordCardListAdapter;
import io.github.ryuu.mrp.data.litepal.Account;

/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordsFragment extends Fragment {

//    private ArrayList<String> passwordsList;

    //绑定Recycle
    private List<RepeatPasswordCardList> repeatList = new ArrayList<>();

    private SwipeRefreshLayout swiperereshlayout;

    private RecyclerView recyclerView;

    private List<RepeatPasswordCardList> lists;

    public PasswordsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_passwords, container, false);
        repeatList.clear();
        Log.d("container是空的？", "no");
        initList();
        initRecyclerView(view);
        initView(view);

        return view;
    }

    //查找重复密码
    private List isRepeat() {

        List<String> passwordsList = new ArrayList<String>();

        List<Account> accounts = LitePal.select("password").where("password != ?", AESUtil.encrypt("")).find(Account.class);
        for (Account account : accounts) {
            passwordsList.add(account.getPassword());
        }
        HashSet h = new HashSet(passwordsList);
        passwordsList.clear();
        passwordsList.addAll(h);
        return passwordsList;

    }

    //查找重复次数
    private int repeatTimes(String password) {
        List<Account> accounts = LitePal.where("password = ?", password).find(Account.class);
        return accounts.size();
    }

    //初始化列表
    private void initList() {
        lists = new ArrayList<RepeatPasswordCardList>();
        for (int i = 0; i < isRepeat().size(); i++) {
            String repeated = isRepeat().get(i).toString();
            RepeatPasswordCardList test = new RepeatPasswordCardList(AESUtil.decrypt(repeated), repeatTimes(repeated) + "");
            if (repeatTimes(repeated) > 1)
                lists.add(test);
            Log.d("去重复后的密码", repeated);
        }
        for (RepeatPasswordCardList list : lists) {
            repeatList.add(list);
        }

//        RepeatPasswordCardList test = new RepeatPasswordCardList("Admin", "3");
//        repeatList.add(test);
    }

    //绑定Recycle
    private void initRecyclerView(View view) {

        recyclerView = view.findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        RepeatPasswordCardListAdapter adapter = new RepeatPasswordCardListAdapter(repeatList);
        recyclerView.setAdapter(adapter);

    }

//    //下拉刷新
    private void initView(final View view) {
        swiperereshlayout = (SwipeRefreshLayout) view.findViewById(R.id.swipelayout);
        //给swipeRefreshLayout绑定刷新监听
        swiperereshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置2秒的时间来执行以下事件
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Snackbar.make(view, "已更新", Snackbar.LENGTH_SHORT).show();
                        swiperereshlayout.setRefreshing(false);
                    }
                }, 500);
            }
        });
    }

}
