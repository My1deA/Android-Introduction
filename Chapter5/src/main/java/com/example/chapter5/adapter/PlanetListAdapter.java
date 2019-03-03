package com.example.chapter5.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter5.R;
import com.example.chapter5.bean.Planet;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PlanetListAdapter extends BaseAdapter implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private Context mContext;
    private ArrayList<Planet> mPlanetList;

    public PlanetListAdapter(Context context,ArrayList<Planet> planet_list){
        mContext=context;
        mPlanetList=planet_list;
    }


    @Override
    public int getCount() {
        return mPlanetList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPlanetList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private final class ViewHolder{
        public ImageView iv_icon;
        public TextView tv_name;
        public TextView tv_desc;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            holder=new ViewHolder();
            // 根据布局文件item_list.xml生成转换视图对象
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_list,null);
            holder.iv_icon=convertView.findViewById(R.id.iv_icon);
            holder.tv_name=convertView.findViewById(R.id.tv_time);
            holder.tv_desc=convertView.findViewById(R.id.tv_desc);

            convertView.setTag(holder);

        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        Planet planet=mPlanetList.get(position);
        holder.iv_icon.setImageResource(planet.image); // 显示行星的图片
        holder.tv_name.setText(planet.name); // 显示行星的名称
        holder.tv_desc.setText(planet.desc); // 显示行星的描述
        return convertView;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String desc=String.format("你点击了第%d个行星，他的名字是 %s",position+1,mPlanetList.get(position).name);
        Toast.makeText(mContext,desc,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String desc=String.format("你点击了第%d个行星，他的名字是 %s",position+1,mPlanetList.get(position).name);
        Toast.makeText(mContext,desc,Toast.LENGTH_SHORT).show();
        return true;
    }
}
