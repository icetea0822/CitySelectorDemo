package com.jy.cityselectordemo;

/**
 * Created by JY on 2017/2/24.
 */

public interface OnItemClickListener {
    /**
     * 列表项点击的监听
     * @param position 点击位置
     * @param tag 点击的列表
     */
    void OnItemClick(int position, String tag);
}
