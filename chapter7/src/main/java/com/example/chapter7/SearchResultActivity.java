package com.example.chapter7;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

@SuppressLint("SetTextI18n")
public class SearchResultActivity extends AppCompatActivity {
    private static final String TAG="SearchResultActivity";
    private TextView tv_search_result;

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar tl_result=findViewById(R.id.tl_result);
        tl_result.setBackgroundResource(R.color.blue_light);
        tl_result.setLogo(R.drawable.ic_app);
        tl_result.setTitle("搜索结果页面");
        tl_result.setNavigationIcon(R.drawable.ic_back);

        setSupportActionBar(tl_result);
        tv_search_result=findViewById(R.id.tv_search_result);
        doSearchQuery(getIntent());
    }

    private void doSearchQuery(Intent intent) {
        if(intent!=null){
            if(Intent.ACTION_SEARCH.equals(intent.getAction())){
                Bundle bundle=intent.getBundleExtra(SearchManager.APP_DATA);
                String value=bundle.getString("hi");
                String queryString=intent.getStringExtra(SearchManager.QUERY);
                tv_search_result.setText("您输入的搜索文字是："+queryString+", 额外信息："+value);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_null, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
