package org.singledog.wechat.sdk.handler.analyzer.robot;

import java.util.List;

/**
 * Created by adam on 16-1-4.
 */
public class RobotDishsResult extends RobotTextResult {

    private List<DishItem> list;

    public List<DishItem> getList() {
        return list;
    }

    public void setList(List<DishItem> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            sb.append("已找到以下菜谱：").append(seprator);
            for (DishItem item : list) {
                sb.append(seprator);
                sb.append("菜名: ").append(item.getName()).append(seprator)
                        .append("材料: ").append(item.getInfo()).append(seprator)
                        .append("详细做法： ").append(item.getDetailurl()).append(seprator);
            }
        } else {
            sb.append("未找到相关菜谱");
        }

        return sb.toString();
    }

    public static class DishItem {
        private String name;
        private String icon;
        private String info;
        private String detailurl;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String ico) {
            this.icon = ico;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getDetailurl() {
            return detailurl;
        }

        public void setDetailurl(String detailurl) {
            this.detailurl = detailurl;
        }
    }

}
