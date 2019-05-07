package com.example.space.socket;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;


public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    BottomNavigationBar bottomNavigationBar;
    private static final String TAG = "MainActivity";
    private FragmentTransaction transaction;
    private Main_Page_Fragment main_page_fragment;
    private Login_Fragment login_fragment;
    private Login_Success_Fragment login_success_fragment;


    private Fragment mFragment;//当前显示的Fragment

    private void initFragment() {
        main_page_fragment=new Main_Page_Fragment();
        login_fragment=new Login_Fragment();
        login_success_fragment=new Login_Success_Fragment();
        login_fragment.setLogin_success_fragment(login_success_fragment);
        login_success_fragment.setLogin_fragment(login_fragment);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.framelayout, main_page_fragment).commit();
        mFragment = main_page_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        frameLayout = (FrameLayout) findViewById(R.id.framelayout);

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_launcher_background, "首页").setActiveColor(Color.BLACK).setInactiveIconResource(R.drawable.ic_launcher_foreground))
                .addItem(new BottomNavigationItem(R.drawable.ic_launcher_background, "资讯").setActiveColor(Color.WHITE).setInactiveIconResource(R.drawable.ic_launcher_foreground))
                .addItem(new BottomNavigationItem(R.drawable.ic_launcher_background, "我的").setActiveColor(Color.GRAY).setInactiveIconResource(R.drawable.ic_launcher_foreground))
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener()
        {
            public void onTabSelected(int position)
            {
                //未选中->选中
                switch (position)
                {
                    case 0:
                    {
                        switchFragment(main_page_fragment);
                        break;
                    }
                    case 1:
                    {
                        break;
                    }
                    case 2:
                    {
                        if(login_fragment.getLogin())
                        {
                            switchFragment(login_success_fragment);
                            if(login_success_fragment.getExit())
                            {
                                login_fragment.setLogin(false);
                            }
                        }
                        else
                        {
                            switchFragment(login_fragment);
                        }
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(int position)
            {
                //选中->未选中
            }

            @Override
            public void onTabReselected(int position)
            {
                //选中->选中
            }
        });
        initFragment();

    }


    public void switchFragment(Fragment fragment)
    {
        //判断当前显示的Fragment是不是切换的Fragment
        if(mFragment != fragment)
        {
            //判断切换的Fragment是否已经添加过
            if (!fragment.isAdded())
            {
                //如果没有，则先把当前的Fragment隐藏，把切换的Fragment添加上
                getSupportFragmentManager().beginTransaction().hide(mFragment)
                        .add(R.id.framelayout,fragment).commit();
            }
            else
            {
                //如果已经添加过，则先把当前的Fragment隐藏，把切换的Fragment显示出来
                getSupportFragmentManager().beginTransaction().hide(mFragment).show(fragment).commit();
            }
            mFragment = fragment;
        }
    }
}
