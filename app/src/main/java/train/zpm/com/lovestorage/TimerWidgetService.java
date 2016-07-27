package train.zpm.com.lovestorage;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.zpm.sql.DbSql;
import com.zpm.sql.Params;
import com.zpm.sql.SqlDataModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
/*
定时刷新服务，继承Service
 */
public class TimerWidgetService extends Service {
    private Timer timer;
    private TimerTask task;
    private int showindex=0;
    private int way=0;
    private int time=0;
    private  long period=1000;
    public TimerWidgetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //服务启动后执行的程序：
        way=intent.getIntExtra("way",0);
        time=intent.getIntExtra("time",0);
        //刷新频率如下为了调试程序设置为1s、2s、7s、30s，可以自行修改：
        switch (time){
            case 0:
                period=1000;
                break;
            case 1:
                period=1000*2;
                break;
            case 2:
                period=1000*7;
                break;
            case 3:
                period=1000*30;
                break;
            default:
                break;

        }
        //新建定时器
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if (way==0){
                    showindex=new Random().nextInt(DbSql.liteOrm.query(SqlDataModel.class).size());
                }
                else
                {
                    showindex=showindex+1;
                    if (showindex>=DbSql.liteOrm.query(SqlDataModel.class).size())
                        showindex=0;
                }
                Params.setShowIndex(showindex);
                sendBroadcast(new Intent("train.zpm.com.lovestorage")
                        .putExtra("content", DbSql.liteOrm.query(SqlDataModel.class).get(showindex).getText_header()));
                System.out.println("service widget"+showindex+"/"+ DbSql.liteOrm.query(SqlDataModel.class).size());
            }
        };
       // 启动定时器,周期为前面设置的period，延时1s执行
        timer.scheduleAtFixedRate(task, 1000, period);//延迟一秒

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (timer!=null){
            timer.cancel();
        }

        timer = null;
        task = null;
        super.onDestroy();
    }
}
