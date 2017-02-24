package com.jy.cityselectordemo;

import java.util.List;

/**
 * Created by JY on 2017/2/24.
 */

public class CityEntity {


    private List<ProvinceBean> province;

    public List<ProvinceBean> getProvince() {
        return province;
    }

    public void setProvince(List<ProvinceBean> province) {
        this.province = province;
    }

    public static class ProvinceBean {
        /**
         * name : 北京
         * country : [{"name":"北京","area":["东城区","西城区","崇文区","宣武区","朝阳区","丰台区","石景山区","海淀区","门头沟区","房山区","通州区","顺义区","昌平区","大兴区","平谷区","怀柔区","密云县","延庆县"]}]
         */

        private String name;
        private List<CityBean> city;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<CityBean> getCity() {
            return city;
        }

        public void setCity(List<CityBean> city) {
            this.city = city;
        }

        public static class CityBean {
            /**
             * name : 北京
             * area : ["东城区","西城区","崇文区","宣武区","朝阳区","丰台区","石景山区","海淀区","门头沟区","房山区","通州区","顺义区","昌平区","大兴区","平谷区","怀柔区","密云县","延庆县"]
             */

            private String name;
            private List<String> area;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<String> getArea() {
                return area;
            }

            public void setArea(List<String> area) {
                this.area = area;
            }
        }


    }

    /**
     * 获取省的数量
     *
     * @return 省的数量
     */
    public int getProvinceCount() {
        return province.size();
    }

    /**
     * 获取市的数量
     *
     * @param provincePosition 在省列表中省的位置
     * @return 市的数量
     */
    public int getCityCount(int provincePosition) {
        return getProvince().get(provincePosition).getCity().size();
    }

    /**
     * 获取区县的数量
     *
     * @param provincePosition 在省列表中省的位置
     * @param cityPosition     在城市类表中市的位置
     * @return 区县的数量
     */
    public int getDistrictCount(int provincePosition, int cityPosition) {
        return getProvince().get(provincePosition).getCity().get(cityPosition).getArea().size();
    }


    /**
     * 获取省的名称
     *
     * @param position 该省在列表中的位置
     * @return 省的名称
     */
    public String getProvinceName(int position) {
        return getProvince().get(position).getName();
    }

    /**
     * 获取市的名称
     *
     * @param provincePosition 该省在列表中的位置
     * @param cityPosition     该市在列表中的位置
     * @return 市的名称
     */
    public String getCityName(int provincePosition, int cityPosition) {
        return getProvince().get(provincePosition).getCity().get(cityPosition).getName();
    }

    /**
     * 获取区县的名称
     * @param provincePosition 该省在列表中的位置
     * @param cityPosition 该市在列表中的位置
     * @param districtPosition 该区县在列表中的位置
     * @return
     */
    public String getDistrictName(int provincePosition, int cityPosition, int districtPosition) {
        return getProvince().get(provincePosition).getCity().get(cityPosition).getArea().get(districtPosition).toString();
    }
}
