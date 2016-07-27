package com.zpm.sql;

/**
 * Created by Administrator on 2016/4/29 0029.
 * 参数类，包含刷新方式，刷新时间间隔，目前显示内容的编号
 */
public class Params {
    private static int refreshway=0;
    private static int refreshTime=0;
    private static int showIndex=0;

    public static int getRefreshTime() {
        return refreshTime;
    }

    public static void setRefreshTime(int refreshTime) {
        Params.refreshTime = refreshTime;
    }

    public static int getRefreshway() {
        return refreshway;
    }

    public static void setRefreshway(int refreshway) {
        Params.refreshway = refreshway;
    }

    public static int getShowIndex() {
        return showIndex;
    }

    public static void setShowIndex(int showIndex) {
        Params.showIndex = showIndex;
    }
}
