package cn.edu.swufe.activitytest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RateActivity extends AppCompatActivity implements Runnable {

    private final String TAG="Rate";
    private float dollarRate=0.1f;
    private float euroRate=0.2f;
    private float wonRate=0.3f;
    private String updateDate;


    EditText rmb;
    TextView show;
    Handler handler;
    private InputStream inputStream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        rmb=findViewById(R.id.rmb);
        show=findViewById(R.id.showOut);

        SharedPreferences sharedPreferences=getSharedPreferences("myrate",Activity.MODE_PRIVATE);
        SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(this);
        dollarRate=sharedPreferences.getFloat("dollar_rate",0.0f);
        euroRate=sharedPreferences.getFloat("euro_rate",0.0f);
        wonRate=sharedPreferences.getFloat("won_rate",0.0f);
        updateDate=sharedPreferences.getString("update_date","");

        final Date today=Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        final String todayStr=sdf.format(today);

        if(!todayStr.equals(updateDate)){
            Thread t=new Thread(this);
            t.start();
        }else{
            Log.i(TAG,"onCreate:不需要更新");
        }

        Thread t=new Thread(this);
        t.start();

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==5){
                    Bundle bdl=(Bundle)msg.obj;
                    dollarRate=bdl.getFloat("dollar-rate");
                    euroRate=bdl.getFloat("euro-rate");
                    wonRate=bdl.getFloat("won-rate");

                    Log.i(TAG,"handleMessage:dollarRate:"+dollarRate);
                    Log.i(TAG,"handleMessage:euroRate:"+euroRate);
                    Log.i(TAG,"handleMessage:wonRate:"+wonRate);

                    SharedPreferences sharedPreferences =getSharedPreferences("myrate", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putString("update_date",todayStr);

                    Toast.makeText(RateActivity.this,"汇率已更新",Toast.LENGTH_SHORT).show();

                }
            }
                super.handleMessage(msg);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1 && resultCode==2){
            Bundle bundle=data.getExtras();
            dollarRate=bundle.getFloat("key_dollar",0.1f);
            euroRate=bundle.getFloat("key_euro",0.1f);
            wonRate=bundle.getFloat("key_won",0.1f);

            SharedPreferences sharedPreferences =getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor =sharedPreferences.edit();
            editor.putFloat("dollar_rate",dollarRate);
            editor.putFloat("euro_rate",euroRate);
            editor.putFloat("won_rate",wonRate);
            editor.commit();

        super.onActivityResult(requestCode,resultCode,data);
        }

    }

    public void onClick(View btn) throws IOException {
        String str=rmb.getText().toString();
        float r=0;
        if(str.length()>0){
            Float r=Float.parseFloat(str);
        }
        if(btn.getId()==R.id.btn_dollar){
            show.setText(String.format("%.2f",r*dollarRate);
        }else if(btn.getId()==R.id.btn_euro){
            show.setText(String.format("%.2f",r*euroRate));
        }else{
            show.setText(String.format("%.2f",r*wonRate));
        }

        (View btn)
        Intent config=new Intent(this,ConfigActivity.class);
        config.putExtra("dollar_rate_key",dollarRate);
        config.putExtra("euro_rate_key",euroRate);
        config.putExtra("won_rate_key",wonRate);
        startActivity(config);


        public void run(){
            for(int i=1;i<6;i++){
                try{
                    Thread.sleep(2000);
            }catch (InterruptedException e){
                    e.printStackTrace();
                }
                Bundle bundle;

        }

            private Bundle getFromBOC(){
                Bundle bundle=new Bundle();
            Document doc = null;
            try {
                doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
                Log.i(TAG, "run: "+doc.title());
                Elements tables=doc.getElementsByTag("table");

                Element table2=tables.get(1);

                Elements tds=table2.getElementsByTag("td");
                for(int i=0;i<tds.size();i+=8){
                for(Element td:tds) {
                    Element td1=tds.get(i);
                    Element td2=tds.get(i+5);
                    Log.i(TAG, "run:" + td1.text()+"==>"+td2.text());
                    String str1=td1.text();
                    String val=td2.text();

                    if("美元".equals(str1)){
                        bundle.putFloat("dollar_rate",100f/Float.parseFloat(val);

                    }else if ("欧元".equals(str1)){
                        bundle.putFloat("euro_rate",100f/Float.parseFloat(val);
                    }else if ("韩国元".equals(str1)){
                        bundle.putFloat("won_rate",100f/Float.parseFloat(val);
                    }



                }
            } catch (IOException e) {
                e.printStackTrace();
            }
                return bundle;
            }


                private Bundle getFromUsdcny(){
                    Bundle bundle=new Bundle();
                    Document doc = null;
                    try {
                        doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
                        Log.i(TAG, "onClick: "+doc.title());
                        Elements tables=doc.getElementsByTag("table");
                        int i=1;
                        Element table6=tables.get(5);
                        Log.i(TAG,"run:table6="+table6);
                        Elements tds=table6.getElementsByTag("td");
                        for(int i=0;i<tds.size();i+=8){
                            for(Element td:tds) {
                                Element td1=tds.get(i);
                                Element td2=tds.get(i+5);
                                Log.i(TAG, "run:" + td1.text()+"==>"+td2.text());
                                String str1=td1.text();
                                String val=td2.text();

                                if("美元".equals(str1)){
                                    bundle.putFloat("dollar_rate",100f/Float.parseFloat(val);

                                }else if ("欧元".equals(str1)){
                                    bundle.putFloat("euro_rate",100f/Float.parseFloat(val);
                                }else if ("韩国元".equals(str1)){
                                    bundle.putFloat("won_rate",100f/Float.parseFloat(val);
                                }



                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return bundle;
                    }

            Message msg=handler.obtainMessage(5);
                msg.obj=bundle;
                handler.sendMessage(msg);

            }



    }

}



