package com.jy.cityselectordemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by JY on 2017/2/24.
 */

public class CityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    CityEntity country;
    String tag;
    String provinceSelected;
    String citySelected;
    MyViewHolder item;

    OnItemClickListener onItemClickListener;

    public static final String TAG_PROVINCE = "province";
    public static final String TAG_CITY = "city";
    public static final String TAG_DISTRICT = "district";

    public CityAdapter(Context context, CityEntity country, String tag, String provinceSelected, String citySelected) {
        this.context = context;
        this.tag = tag;
        this.country = country;
        if (provinceSelected != null) {
            this.provinceSelected = provinceSelected;
        }
        if (citySelected != null) {
            this.citySelected = citySelected;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(context).inflate(R.layout.item_city, parent, false);
        item = new MyViewHolder(holder);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (tag) {
            case TAG_PROVINCE:
                ((MyViewHolder) holder).tv.setText(country.getProvinceName(position));
                break;
            case TAG_CITY:
                if (provinceSelected != null) {
                    for (int i = 0; i < country.getProvinceCount(); i++) {
                        if (provinceSelected == country.getProvinceName(i)) {
                            ((MyViewHolder) holder).tv.setText(country.getCityName(i, position));
                        }
                    }
                }
                break;
            case TAG_DISTRICT:
                if (citySelected != null) {
                    for (int i = 0; i < country.getProvinceCount(); i++) {
                        for (int j = 0; j < country.getCityCount(i); j++) {
                            if (citySelected == country.getCityName(i, j)) {
                                ((MyViewHolder) holder).tv.setText(country.getDistrictName(i, j, position));
                            }
                        }
                    }
                }
                break;
        }

        ((MyViewHolder) holder).tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(position, tag);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (tag == TAG_PROVINCE) {
            return country.getProvinceCount();
        }
        if (tag == TAG_CITY) {
            if (provinceSelected != null) {
                for (int i = 0; i < country.getProvinceCount(); i++) {
                    if (provinceSelected == country.getProvinceName(i)) {
                        return country.getCityCount(i);
                    }
                }
            }

        }
        if (tag == TAG_DISTRICT) {
            if (citySelected != null) {
                for (int i = 0; i < country.getProvinceCount(); i++) {
                    for (int j = 0; j < country.getCityCount(i); j++) {
                        if (citySelected == country.getCityName(i, j)) {
                            return country.getDistrictCount(i, j);
                        }
                    }
                }
            }
        }
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tvCity);
        }
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        onItemClickListener = l;
    }
}
