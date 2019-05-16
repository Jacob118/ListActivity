package cn.edu.swufe.activitytest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyList2Activity extends ListActivity implements Runnable,AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {

    Handler handler;
    private List<HashMap<String,String>> listItems;  //存放文字，图片信息
    private SimpleAdapter listItemAdapter;  //适配器



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();

        //MyAdapter myAdapter=new MyAdapter(this,R.layout.list_item,listItems);
        this.setListAdapter(listItemAdapter);

        THread t=new Thread(this);
        ((Thread) t).start();

        Handler handler=new handler(){
            public void handlerMessage(Message msg){
            if(msg.what==7){
                listItems=(List<HashMap<String, String>>) msg.obj;
                listItemAdapter=new SimpleAdapter(MyList2Activity.this,listItems,//listItems数据源
                        R.layout.list_item, //ListItem的布局实现
                        new String[]{"ItemTitle","ItemDetail"},
                        new int[]{R.id.itemTitle,R.id.itemDetail});
                setListAdapter(listItemAdapter);
            }
            super.handlerMessage(msg);
        };
            getListView().setOnItemClickListener(this);
            getListView().setOnItemLongClickListener(this);
        //setContentView(R.layout.activity_my_list2);
    }
    private void initListView(){
        listItems=new ArrayList<HashMap<String, String>>();
        for(int i=0;i<10;i++){
            HashMap<String,String> map=new HashMap<String, String>();
            map.put("ItemTitle","Rate:"+i);  //标题文字
            map.put("ItemDetail","detail"+i);  //详情描述
            listItems.add(map);
        }
        //生成适配器的Item和动态数组的元素
        listItemAdapter=new SimpleAdapter(this,listItems//listItems数据源
                R.layout.list_item, //ListItem的布局实现
                new String[]{"ItemTitle","ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetail});
    };
}

public void run(){
    //获取网络数据，放入list带回到主线程
    List<HashMap<String,String>> retList=new ArrayList<>(HashMap<String,String>)();
    Document doc=null;
    try{
        Thread.sleep(3000);
        doc= Jsoup.connect("http://www.boc.cn/sourcedb/whpj").get();
        Elements tables=doc.getElementsByTag("table");

        Element table2=tables.get(1);
        //获取TD中的数据
        Element tds=table2.getElementsByTag("td");
        for(int i=0;i<tds.size();i+=8){
            Element td1=tds.get(i);
            Element td2=tds.get(i+5);

            String str1=td1.text();
            String val=td2.text();

            HashMap<String.String> map=new HashMap<String,String>();
            map.put("ItemTitle",str1);
            map.put("ItemDetail",val);
            reList.add(map);
        }
    }catch (IOException e){
        e.printStackTrace();

    }catch (InterruptedException e){
        e.printStackTrace();
    }

    Message msg= handler.obtainMessage(7);
    msg.obj=retList;
    handler.sendMessage(msg);
}

@Override
public void onItemClick(AdapterView<?>) parent，View view,int position,long id){


     HashMap<String,String> map=(HashMap<String, String>)  getListView().getItemAtPosition(position);
     String titleStr=map.get("ItemTitle");
     String detailStr=map.get("ItemDetail");

        TextView title=(TextView) view.findViewById(R.id.itemTitle);
        TextView detail=(TextView) view.findViewById(R.id.itemDetail);
        String title2=String.valueOf(title.getText());
        String detail2=String.valueOf(detail.getText());

        Intent rateCal=new Intent(this,RateCalActivity.class);
        rateCal.putExtra("title",titleStr);
        rateCal.putExtra("rate",Float.parseFloat(detailStr));
        startActivity(rateCal);

     }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {

        //listItems.remove(position);
        //listItemAdapter.notifyDataSetChanged();

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("请确认是否删除当前数据").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listItems.remove(position);
                listItemAdapter.notifyDataSetChanged();
            }
        })
        return true;
    }
}