package com.nostra13.example.changhong;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.nostra13.example.universalimageloader.ImageGridActivity;
import com.nostra13.example.universalimageloader.R;

/**
 * Created by changhong on 14-3-28.
 */
public class Home extends Activity implements AdapterView.OnItemClickListener{
    GridView gridView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changhong_homegrid);
        gridView = (GridView) findViewById(R.id.homegrid);
        gridView.setAdapter(new HomeAdapter(
                AlubumUtil.getInstance().maulbums,
                this));
        gridView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getApplicationContext(), ImageGridActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("bid", AlubumUtil.getInstance().maulbums_bucketID.get(position));

        intent.putExtras(bundle);
        startActivity(intent);


    }
}