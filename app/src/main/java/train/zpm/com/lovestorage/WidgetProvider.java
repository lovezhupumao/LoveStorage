package train.zpm.com.lovestorage;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.zpm.sql.DbSql;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/4/27 0027.
 */
public class WidgetProvider extends AppWidgetProvider {
    private static int showwayindex=0;//0 为随机，1为顺序。
    private int refreshfre=0;//0为1天，1为2天，2为1星期，3为1年
    private int index = 0;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        System.out.println("onUpdate widget：" + Arrays.toString(appWidgetIds)+"//"+appWidgetIds.length);
        /*
         * 构造pendingIntent发广播，onReceive()根据收到的广播，更新
         */
        DbSql.createDb(context);
        /*
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("refreshway",showwayindex);
        intent.putExtra("index",index);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.view_widget);
        rv.setOnClickPendingIntent(R.id.widgetenter, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, rv);
*/
        for(int i= 0;i<appWidgetIds.length;i++){
            System.out.println(appWidgetIds[i]);
            //新intent
            Intent intent1 = new Intent(context,DetailActivity.class);
            //创建一个pendingIntent。另外两个参数以后再讲。

            PendingIntent pendingIntent1 = PendingIntent.getActivity(
                    context, 0, intent1 ,PendingIntent.FLAG_UPDATE_CURRENT);
            //创建一个remoteViews。
            RemoteViews remoteViews  = new RemoteViews(
                    context.getPackageName(), R.layout.view_widget);
            //绑定处理器，表示控件单击后，会启动pendingIntent。
            remoteViews.setOnClickPendingIntent(R.id.widgetenter, pendingIntent1);
            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        System.out.println("onDeleted widget");
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        System.out.println("onDisabled widget");
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        System.out.println("onEnabled widget");
        System.out.println("onEnabled widget");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        System.out.println("onReceive widget");
        /*
         * 接收 <action android:name="train.zpm.com.lovestorage"/>
           在其他组件或activity或service中发送这些广播
         */

        if (intent.getAction().equals("train.zpm.com.lovestorage")) {
            String content = intent.getStringExtra("content");
            updateWidget(context,content );
            System.out.println("receive train.zpm.com.lovestorage");
        }
    }
    private void updateWidget(Context context, String header) {
        //RemoteViews处理异进程中的View
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.view_widget);
        System.out.println("header=" + header);
        rv.setTextViewText(R.id.widgetenter, header);

        AppWidgetManager am = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = am.getAppWidgetIds(new ComponentName(context, WidgetProvider.class));
        am.updateAppWidget(appWidgetIds, rv);//更新 所有实例
    }
}
