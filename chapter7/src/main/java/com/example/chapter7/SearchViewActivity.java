package com.example.chapter7;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter7.util.DateUtil;
import com.example.chapter7.util.MenuUtil;

import org.w3c.dom.Text;


public class SearchViewActivity extends AppCompatActivity {

    private final static String TAG="SearchViewActivity";
    private TextView tv_desc;
    private SearchView.SearchAutoComplete sac_key;
    private String[] hintArray={"iphone","iphone8","iphone8 plus","iphone7","iphone7 plus"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        Toolbar tl_head=findViewById(R.id.tl_head);
        tl_head.setTitle("搜索框页面");
        setSupportActionBar(tl_head);
        tv_desc=findViewById(R.id.tv_desc);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        MenuUtil.setOverflowIconVisible(featureId,menu);
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        initSearchView(menu);

        return true;

    }

    private void initSearchView(Menu menu) {
        MenuItem menuItem=menu.findItem(R.id.menu_search);
        SearchView searchView=(SearchView) menuItem.getActionView();
        searchView.setIconifiedByDefault(getIntent().getBooleanExtra("collapse",true));


        searchView.setSubmitButtonEnabled(true);
        SearchManager sm= (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        ComponentName cn=new ComponentName(this,SearchResultActivity.class);
        SearchableInfo info=sm.getSearchableInfo(cn);
        if(info==null){
            Log.d(TAG,"Fail to get SearchResultActvity");
            return;
        }


        searchView.setSearchableInfo(info);
        sac_key=searchView.findViewById(R.id.search_src_text);
        sac_key.setTextColor(Color.WHITE);
        sac_key.setHintTextColor(Color.WHITE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 搜索关键词完成输入
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 搜索关键词发生变化
            public boolean onQueryTextChange(String newText) {
                doSearch(newText);
                return true;
            }
        });

        Bundle bundle = new Bundle(); // 创建一个新包裹
        bundle.putString("hi", "hello"); // 往包裹中存放名叫hi的字符串
        // 设置搜索框的额外搜索数据
        searchView.setAppSearchData(bundle);
    }

    private void doSearch(String text){
        if(text.indexOf("i")==0){
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.search_list_auto,hintArray);

            sac_key.setAdapter(adapter);
            sac_key.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    sac_key.setText(((TextView)view).getText());
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            finish();
        }else if(id==R.id.menu_refresh){
            tv_desc.setText("当前刷新时间："+ DateUtil.getNowDateTime("yyyy-MM_dd HH:mm:ss"));
            return true;
        }else  if(id==R.id.menu_about){
            Toast.makeText(this,"这是一个工具栏演示demo",Toast.LENGTH_SHORT).show();
            return true;
        }else if (id == R.id.menu_quit) { // 点击了退出菜单项
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
