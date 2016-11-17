package com.example.feng.myapp.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.feng.myapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestBug1Activity extends Activity {

    private ListView lv_list;
    private ArrayList<Map<String,String>> beanContainer;
    private SellAdapter sellAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bug1);

        parseJson();
        sellAdapter = new SellAdapter();
        lv_list = (ListView)findViewById(R.id.lv_list);
        lv_list.setAdapter(sellAdapter);
    }

    class SellAdapter extends BaseAdapter {

        HashMap<Integer, View> lmap = new HashMap<Integer, View>();
        @Override
        public int getCount() {
            return beanContainer.size();
        }

        @Override
        public Object getItem(int position) {
            return beanContainer.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {

            final ViewHolder holder;

            if(lmap.get(position) == null){
                convertView = View.inflate(getApplicationContext(),R.layout.item_sell,null);

                holder = new ViewHolder();
                //找控件
                //门票名称
                holder.ticketName = (TextView) convertView.findViewById(R.id.name_ticket);
                //数量减
                holder.btn_Minus = (ImageButton) convertView.findViewById(R.id.img_btn_minus);
                //门票数量
                holder.ticketCount = (TextView) convertView.findViewById(R.id.text_Count);
                //数量加
                holder.btn_Plus = (ImageButton) convertView.findViewById(R.id.img_btn_plus);
                //门票价格
                holder.ticketPrice = (TextView)convertView.findViewById(R.id.text_TicketPrice);
                //总价
                holder.allMOney = (TextView) convertView.findViewById(R.id.tv_all_money);
                //立即购买键
                holder.btn_buy = (Button) convertView.findViewById(R.id.btn_buy);

                convertView.setTag(holder);
                lmap.put(position,convertView);
            }else{

                convertView = lmap.get(position);
                holder = (ViewHolder)convertView.getTag();
            }

            Map<String,String> bean = beanContainer.get(position);
            //设置门票名称
            holder.ticketName.setText(bean.get("sticketnamech"));
            //设置门票价格
            holder.ticketPrice.setText(bean.get("ngeneralprice"));
            //增加按钮的点击事件
            holder.btn_Plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String count = Integer.valueOf(holder.ticketCount.getText().toString()) + 1 + "";
                    holder.ticketCount.setText(count);
                }
            });

            //减少按钮的点击事件
            holder.btn_Minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.ticketCount.setText((Integer.valueOf(holder.ticketCount.getText().toString()) - 1 + ""));
                    if(Integer.valueOf(holder.ticketCount.getText().toString()) < 1){
                        holder.ticketCount.setText("1");
                    }
                }
            });
            return convertView;
        }

        class ViewHolder {

            public TextView ticketName;
            public ImageButton btn_Minus;
            public TextView ticketCount;
            public ImageButton btn_Plus;
            public TextView ticketPrice;
            public TextView allMOney;
            public Button btn_buy;
        }
    }

    //解析访问信息列表接口所返回的json字符串
    private void parseJson() {

            beanContainer = new ArrayList();
            //遍历数组
            for (int i = 0;i < 10;i++){
                Map<String,String> map = new HashMap<String, String>();
                map.put("sticketnamech","名称_"+i);
                map.put("ngeneralprice","价格_"+i);

                beanContainer.add(map);
            }
    }
}
