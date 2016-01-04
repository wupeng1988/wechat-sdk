package org.singledog.wechat.sdk.handler.analyzer.robot;

import java.util.List;

/**
 * Created by adam on 16-1-4.
 */
public class RobotNewsResult extends RobotTextResult {

    private List<NewsItem> list;

    public List<NewsItem> getList() {
        return list;
    }

    public void setList(List<NewsItem> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            sb.append("已找到以下新闻： ").append(seprator);
            for (NewsItem item : list) {
                sb.append(seprator)
                        .append(item.getArticle()).append(seprator)
                        .append("来源: ").append(item.getSource()).append(seprator)
                        .append(item.getDetailurl()).append(seprator);
            }
        } else {
            sb.append("未找到新闻...");
        }

        return sb.toString();
    }

    public static class NewsItem {
        private String article;
        private String source;
        private String icon;
        private String detailurl;

        public String getArticle() {
            return article;
        }

        public void setArticle(String article) {
            this.article = article;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getDetailurl() {
            return detailurl;
        }

        public void setDetailurl(String detailurl) {
            this.detailurl = detailurl;
        }
    }

}
