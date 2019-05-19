package io.github.ryuu.mrp.userinterface.tabs;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import io.github.ryuu.mrp.R;
import io.github.ryuu.mrp.authentication.AESUtil;
import io.github.ryuu.mrp.data.BindCardList;
import io.github.ryuu.mrp.data.BindCardListAdapter;
import io.github.ryuu.mrp.data.litepal.Account;

public class BindsFragment extends Fragment {

    //RecycleView Part1
    private List<BindCardList> bindCardLists = new ArrayList<>();

    private SwipeRefreshLayout swiperereshlayout ;

    public BindsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_binds, container, false);

        bindCardLists.clear();
        initView(view);
        //RecycleView Part2
        initLists();
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        BindCardListAdapter adapter = new BindCardListAdapter(bindCardLists);
        recyclerView.setAdapter(adapter);

        return view;
    }

    //RecycleView Part3
    private void initLists() {
        //查找手机字段不为空的手机字段
        List<Account> accountsWithPhone = LitePal.select("phone").where("phone != ?", AESUtil.encrypt("")).find(Account.class);
        //查找邮箱字段不为空的邮箱字段
        List<Account> accountsWithMail = LitePal.select("mail").where("mail != ?", AESUtil.encrypt("")).find(Account.class);
        //查找QQ字段不为空的QQ字段
        List<Account> accountsWithQq = LitePal.select("qq").where("qq != ?", AESUtil.encrypt("")).find(Account.class);
        //查找微信字段不为空的微信字段
        List<Account> accountsWithWechat = LitePal.select("wechat").where("wechat != ?", AESUtil.encrypt("")).find(Account.class);

        //添加手机号到RecycleView
        List<String> phones = new ArrayList<>();
        for (Account account: accountsWithPhone) {
            phones.add(account.getPhone());
        }
        for (int i = 0; i < killRepeat(phones).size(); i++) {
            List<Account> phone = LitePal.select("id").where("phone = ?", killRepeat(phones).get(i).toString()).find(Account.class);
            BindCardList list = new BindCardList(AESUtil.decrypt(killRepeat(phones).get(i).toString()), "", "", "", phone.size()+"");
            bindCardLists.add(list);
        }

        List<String> mails = new ArrayList<>();
        for (Account account: accountsWithMail) {
            mails.add(account.getMail());
        }
        for (int i = 0; i < killRepeat(mails).size(); i++) {
            List<Account> mail = LitePal.select("id").where("mail = ?", killRepeat(mails).get(i).toString()).find(Account.class);
            BindCardList list = new BindCardList("", AESUtil.decrypt(killRepeat(mails).get(i).toString()), "", "", mail.size()+"");
            bindCardLists.add(list);
        }

        List<String> qqs = new ArrayList<>();
        for (Account account: accountsWithQq) {
            qqs.add(account.getQq());
        }
        for (int i = 0; i < killRepeat(qqs).size(); i++) {
            List<Account> qq = LitePal.select("id").where("qq = ?", killRepeat(qqs).get(i).toString()).find(Account.class);
            BindCardList list = new BindCardList("", "", AESUtil.decrypt(killRepeat(qqs).get(i).toString()), "", qq.size()+"");
            bindCardLists.add(list);
        }

        List<String> wechats = new ArrayList<>();
        for (Account account: accountsWithWechat) {
            wechats.add(account.getWechat());
        }
        for (int i = 0; i < killRepeat(wechats).size(); i++) {
            List<Account> wechat = LitePal.select("id").where("wechat = ?", killRepeat(wechats).get(i).toString()).find(Account.class);
            BindCardList list = new BindCardList("", "", "", AESUtil.decrypt(killRepeat(wechats).get(i).toString()), wechat.size()+"");
            bindCardLists.add(list);
        }

    }

    //去掉字段重复内容
    private List killRepeat(List<String> list) {
        Set set = new HashSet();
        List newList = new  ArrayList();
        for (String cd:list) {
            if (set.add(cd)) {
                newList.add(cd);
            }
        }
        return newList;
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
