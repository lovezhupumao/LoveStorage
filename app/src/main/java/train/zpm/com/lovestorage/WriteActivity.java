package train.zpm.com.lovestorage;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.leo.gesturelibray.view.CustomLockView;

import java.util.Timer;
import java.util.TimerTask;
/*
解锁后界面activity，继承类 AppCompatActivity。继承接口View.OnClickListener
 */
public class WriteActivity extends AppCompatActivity implements View.OnClickListener {

    private CombineView mStartSportCombineView;
    private CombineView mHistoryRouteCombineView;
    private CombineView mHistoryTableCombineView;
    private CombineView mAboutCombineView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
       // setSupportActionBar(toolbar);
        //根据id查找activity_write.xml中的控件
        mStartSportCombineView = (CombineView) findViewById(R.id.main_begin);
        mHistoryRouteCombineView = (CombineView) findViewById(R.id.main_history);
        mHistoryTableCombineView = (CombineView) findViewById(R.id.main_table);
        mAboutCombineView=(CombineView)findViewById(R.id.main_setting);
        //设置控件点击事件
        mStartSportCombineView.setOnClickListener(this);
        mHistoryRouteCombineView.setOnClickListener(this);
        mHistoryTableCombineView.setOnClickListener(this);
        mAboutCombineView.setOnClickListener(this);
    }

    //按钮点击事件

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_begin:
                //事件记录控件点击事件跳转到编辑界面（EditActivity）
                Intent intent = new Intent(WriteActivity.this, EditActivity.class);
                startActivity(intent);
               // Intent serviceIntent = new Intent(WriteActivity.this, TimerWidgetService.class);
               // startService(serviceIntent);
                break;
            case R.id.main_history:
                //历史记录控件点击事件跳转到编辑界面（HistoryActivity）
                Intent intenthistory = new Intent(WriteActivity.this, HistoryActivity.class);
                startActivity(intenthistory);
                break;
            case R.id.main_table:
                //挂件设置控件点击事件跳转到编辑界面（SettingActivity）
                Intent intentgraph = new Intent(WriteActivity.this, SettingActivity.class);
                startActivity(intentgraph);
                break;
            case R.id.main_setting:
                //关于软件控件点击事件跳转到编辑界面（AboutActivity）
                Intent intentabout = new Intent(WriteActivity.this, AboutActivity.class);
                startActivity(intentabout);
            default:
                break;
        }
    }
    /*
    设置点击返回键后提示再次点击退出功能，一个定时器，当两次连续点击间隔小于2s时退出程序
     */
    private static boolean first = false;
    private Timer ExitTimer = new Timer();
    class ExitCleanTask extends TimerTask {

        @Override
        public void run() {

            Log.e("ExitCleanTask", "Run in!!!! ");
            first = false;
        }
    }
    //返回键点击执行程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.d("TAG", "onKeyDown KEYCODE_BACK");

            if (first) {
                first = false;
                finish();
            }
            else
            {
                first = true;
                Toast.makeText(WriteActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                ExitTimer.schedule(new ExitCleanTask(), 2000);
            }

            //finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
