package cn.edu.swufe.activitytest;

import android.app.ListActivity;
import android.os.Bundle;

public class RateListActivity extends ListActivity {

    String data[]={"one","two"，"three"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list);
        ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
    }
}
