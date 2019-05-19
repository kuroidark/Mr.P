package io.github.ryuu.mrp.userinterface;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import org.litepal.LitePal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import io.github.ryuu.mrp.R;
import io.github.ryuu.mrp.data.DataFile;
import io.github.ryuu.mrp.userinterface.Anime.CircularAnim;
import io.github.ryuu.mrp.userinterface.tabs.BindsFragment;
import io.github.ryuu.mrp.userinterface.tabs.PasswordsFragment;
import io.github.ryuu.mrp.userinterface.tabs.TagsFragment;


public class Home extends AppCompatActivity {

    //TabLayout的一部分
    ViewPager mViewPager;
    TagsFragment mTags;
    PasswordsFragment mPasswords;
    BindsFragment mBinds;
    PagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;
    private Bundle saved;

    //实例一个滑动页面
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        saved = savedInstanceState;
        mDrawerLayout = findViewById(R.id.drawer_layout);
//        menu_add = findViewById(R.id.account_add);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Toolbar自定义菜单
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        //初始化TabLayout
        //while (isFront == false) {
            initView(savedInstanceState);

        //}

    }

    //绑定Toolbar菜单项
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
//        menu_add = menu.findItem(R.id.account_add);
        return true;
    }

    //菜单项点击事件
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START, true);
                break;
            case R.id.account_add:
                LitePal.getDatabase();
                //添加账号操作
                CircularAnim.fullActivity(Home.this, mTabLayout)
                        .colorOrImageRes(R.color.colorPrimary)
                        .duration(400L)
                        .go(new CircularAnim.OnAnimationEndListener() {
                            @Override
                            public void onAnimationEnd() {
                                startActivity(new Intent(Home.this, AccountEdit.class));
                            }
                        });
//                Intent intent = new Intent(this, AccountEdit.class);
//                startActivity(intent);

                break;
            case R.id.data_import:
                //导入数据库
//                DataFile.verifyStoragePermissions(this);
//                if (DataFile.delete()==true) {
//
//                } else {
//                    Log.d("信息","直接就删除失败了呗");
//                }
                if (DataFile.input()==true) {
                    Snackbar.make(getWindow().getDecorView(),"已恢复",Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(getWindow().getDecorView(),"恢复失败", Snackbar.LENGTH_SHORT).setAction("详细", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Snackbar.make(getWindow().getDecorView(),"请将备份文件夹置于存储根目录",Snackbar.LENGTH_LONG).show();
                        }
                    }).show();
                }
                break;
            case R.id.data_export:
                //导出数据库
//                DataFile.verifyStoragePermissions(this);
                if (DataFile.save()==true) {
                    Snackbar.make(getWindow().getDecorView(),"已导出到存储根目录/Mrp/",Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(getWindow().getDecorView(),"请在允许使用权限后重新导出",Snackbar.LENGTH_SHORT).show();
                }
                break;
            default:
        }
        return true;
    }

    //Tablayout的另一部分
    public void initView(Bundle savedInstanceState) {

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout = (TabLayout) findViewById(R.id.tab);

        if (savedInstanceState == null) {
            mTags = new TagsFragment();
            mPasswords = new PasswordsFragment();
            mBinds = new BindsFragment();
        }

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener
                (mViewPager));
        mPagerAdapter.notifyDataSetChanged();
    }

    //TabLayout的另一部分
    public class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return mTags;
            } else if (position == 1) {
                return mPasswords;
            } else if (position == 2) {
                return mBinds;
            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("是否在前台：","否");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mTabLayout.setupWithViewPager(mViewPager);
        initView(saved);
        Log.d("是否在前台：","是");
    }

}
