package com.example.chapter10;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


public class JsonParseActivity extends AppCompatActivity implements View.OnClickListener {
    private String mJsonStr;
    private TextView tv_json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_parse);
        findViewById(R.id.btn_construct_json).setOnClickListener(this);
        findViewById(R.id.btn_parse_json).setOnClickListener(this);
        findViewById(R.id.btn_traverse_json).setOnClickListener(this);
        tv_json=findViewById(R.id.tv_json);
        mJsonStr=getJsonStr();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_construct_json) {
            // 显示完整的json串
            tv_json.setText(mJsonStr);
        } else if (v.getId() == R.id.btn_parse_json) {
            // 显示json串解析后的各个参数值
            tv_json.setText(parseJson(mJsonStr));
        } else if (v.getId() == R.id.btn_traverse_json) {
            // 显示json串的遍历结果串
            tv_json.setText(traverseJson(mJsonStr));
        }
    }


    private String getJsonStr(){
        String str="";

        JSONObject obj=new JSONObject();
        try{
            obj.put("name","address");

            JSONArray array=new JSONArray();
            for(int i=0;i<3;i++){
                JSONObject item=new JSONObject();
                item.put("item","第"+(i+1)+"个元素");
                array.put(item);
            }

            obj.put("list",array);
            obj.put("count",array.length());
            obj.put("desc","这是测试串");

            str=obj.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str;
    }

    //解析json串 内部各个参数
    private String parseJson(String jsonStr){
        String result="";
        try{
            JSONObject obj=new JSONObject(jsonStr);
            String name=obj.getString("name");
            String desc=obj.getString("desc");
            int count=obj.getInt("count");

            result=String.format("%sname=%s\n",result,name);
            result=String.format("%sdesc=%s\n",result,desc);
            result=String.format("%scount=%d\n",result,count);

            //list
            JSONArray array=obj.getJSONArray("list");
            for(int i=0;i<array.length();i++){
                //获得数组下标的 obj对象
                JSONObject list_item=array.getJSONObject(i);

                String item=list_item.getString("item");
                result=String.format("%s\titem=%s\n",result,item);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    //遍历json串保存的键值对信息
    private String traverseJson(String jsonStr){
        String result="";
        try{
            JSONObject obj=new JSONObject(jsonStr);
            Iterator<String> it=obj.keys();
            while(it.hasNext()){
                String key=it.next();
                String value=obj.getString(key);

                result = String.format("%skey=%s, value=%s\n", result, key, value);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  result;
    }
}
