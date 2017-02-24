package com.jy.cityselectordemo;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    private static final String TAG = "MainActivity";
    RecyclerView rvProvince, rvCity, rvDistrict;
    CityAdapter provinceAdapter, cityAdapter, districtAdapter;
    CityEntity cityEntity;

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    cityEntity = (CityEntity) msg.obj;
                    Log.d(TAG, "handleMessage: " + cityEntity.getProvinceCount());
                    initListView();
                    break;
            }
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvProvince = (RecyclerView) findViewById(R.id.rvProvince);
        rvCity = (RecyclerView) findViewById(R.id.rvCity);
        rvDistrict = (RecyclerView) findViewById(R.id.rvDistrict);

        initData();
    }


    /**
     * 初始化各个Recyclerview其中首先加载省市的列表，其它两个列表暂不加载数据并且隐藏
     */
    private void initListView() {
        provinceAdapter = new CityAdapter(this, cityEntity, CityAdapter.TAG_PROVINCE, null, null);
        rvProvince.setLayoutManager(new LinearLayoutManager(this));
        rvProvince.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvProvince.setAdapter(provinceAdapter);
        provinceAdapter.setOnItemClickListener(this);

        cityAdapter = new CityAdapter(this, cityEntity, CityAdapter.TAG_CITY, null, null);
        rvCity.setLayoutManager(new LinearLayoutManager(this));
        rvCity.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvCity.setAdapter(cityAdapter);
        cityAdapter.setOnItemClickListener(this);

        districtAdapter = new CityAdapter(this, cityEntity, CityAdapter.TAG_DISTRICT, null, null);
        rvDistrict.setLayoutManager(new LinearLayoutManager(this));
        rvDistrict.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvDistrict.setAdapter(districtAdapter);
        districtAdapter.setOnItemClickListener(this);
    }

    /**
     * 初始化数据，从assets目录中的json文件获取，并生成相应实体类
     */
    public void initData() {
        AssetManager assetManager = getAssets();
        try {
            InputStream is = assetManager.open("cities.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer stringBuffer = new StringBuffer();
            String str = null;
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }
            Gson gson = new Gson();
            CityEntity cityEntity = gson.fromJson(stringBuffer.toString(), CityEntity.class);
            mHandler.sendMessage(mHandler.obtainMessage(0, cityEntity));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int provincePosition = -1;//记录点击的省在列表中的位置
    int cityPosition = -1;//记录点击的市在列表中的位置

    @Override
    public void OnItemClick(int position, String tag) {
        switch (tag) {
            case CityAdapter.TAG_PROVINCE:
                cityAdapter.provinceSelected = cityEntity.getProvinceName(position);
                cityAdapter.notifyDataSetChanged();
                rvCity.setVisibility(View.VISIBLE);
                if (rvDistrict.getVisibility() == View.VISIBLE) {
                    rvDistrict.setVisibility(View.INVISIBLE);
                }
                provincePosition = position;
                break;
            case CityAdapter.TAG_CITY:
                if (provincePosition >= 0) {
                    districtAdapter.citySelected = cityEntity.getCityName(provincePosition, position);
                    districtAdapter.notifyDataSetChanged();
                    rvDistrict.setVisibility(View.VISIBLE);
                    cityPosition = position;
                }
                break;
            case CityAdapter.TAG_DISTRICT:
                Log.d(TAG, "OnItemClick: " + tag + " " + position);
                String provinceName = cityEntity.getProvinceName(provincePosition);
                String cityName = cityEntity.getCityName(provincePosition, cityPosition);
                String districtName = cityEntity.getDistrictName(provincePosition, cityPosition, position);
                Toast.makeText(this, "您选择了："
                        + provinceName + "省"
                        + cityName + "市"
                        + districtName, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
