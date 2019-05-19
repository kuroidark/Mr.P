package io.github.ryuu.mrp.userinterface.tabs;


import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.github.ryuu.mrp.R;
import io.github.ryuu.mrp.authentication.AESUtil;
import io.github.ryuu.mrp.data.litepal.Account;
import io.github.ryuu.mrp.userinterface.Anime.CircularAnim;
import io.github.ryuu.mrp.userinterface.ViewAccount;

/**
 * A simple {@link Fragment} subclass.
 */
public class TagsFragment extends Fragment {

    //id列表
    private List<String> idList = new ArrayList<String>();

    private SwipeRefreshLayout swiperereshlayout ;

    private ChipGroup chipGroup;

    private RelativeLayout relativeLayout;


    public TagsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_tags, container, false);

        relativeLayout = view.findViewById(R.id.nothing);

        //ChipGroup的点击检测
        chipGroup = view.findViewById(R.id.chipGroup);

        idList.clear();

        //添加chip
        addChip(chipGroup);

        //是否显示无数据图示
        nothingHere();

        initView(view);

        return view;
    }

    private ArrayList query() {
        List<Account> accounts = LitePal.select("id","keyword").where("keyword != ?", "").find(Account.class);
        ArrayList arrayList = new ArrayList();
        for (Account account: accounts){
                arrayList.add(account.getKeyword());
                idList.add(account.getId()+"");

        }
        return arrayList;
    }

    private int queryColor(int id) {
        List<Account> accounts = LitePal.select("id","color").where("id = ?", id+"").find(Account.class);
        return accounts.get(0).getColor();
    }

    private void addChip(ChipGroup chipGroup) {
        for (int i = 0; i< query().size(); i++) {
            //添加chip
            final Chip chip = new Chip(getContext());
            chip.setChipText(AESUtil.decrypt(query().get(i).toString()));
//            chip.setCheckable(true);
            chip.setCheckedIcon(null);
            final String id = idList.get(i);
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CircularAnim.fullActivity(getActivity(), chip)
                            .colorOrImageRes(R.color.colorPrimary)
                            .deployReturnAnimator(new CircularAnim.OnAnimatorDeployListener() {
                        @Override
                        public void deployAnimator(Animator animator) {
                            animator.setDuration(500L);
                            animator.setInterpolator(new AccelerateInterpolator());
                        }
                    }).go(new CircularAnim.OnAnimationEndListener() {
                        @Override
                        public void onAnimationEnd() {
                            startActivity(new Intent(getContext(), ViewAccount.class).putExtra("idGet", id));
                        }
                    });
//                    Intent intent = new Intent(getContext(),ViewAccount.class);
//                    intent.putExtra("idGet", id);
//                    startActivity(intent);
                }
            });
            int color = queryColor(Integer.parseInt(id));
            chip.setTextColor(Color.rgb(255,255,255));
            switch (color) {
                case 0:
                    chip.setChipBackgroundColorResource(R.color.gay);
                    break;
                case 1:
                    chip.setChipBackgroundColorResource(R.color.pink);
                    break;
                case 2:
                    chip.setChipBackgroundColorResource(R.color.blue);
                    break;
                case 3:
                    chip.setChipBackgroundColorResource(R.color.green);
                    break;
                case 4:
                    chip.setChipBackgroundColorResource(R.color.yellow);
                    break;
                case 5:
                    chip.setChipBackgroundColorResource(R.color.orange);
                    break;
                case 6:
                    chip.setChipBackgroundColorResource(R.color.red);
                    break;
                    default:
                        break;
            }
            chipGroup.addView(chip);
        }
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

    //是否显示无数据图片
    private void nothingHere(){
        Log.d("列表大小",query().size()+"");
        if (query().size()==0) {
            Log.d("此处应该显示图示","true");
            relativeLayout.setVisibility(View.VISIBLE);
        } else {
            relativeLayout.setVisibility(View.GONE);
        }
    }

}
