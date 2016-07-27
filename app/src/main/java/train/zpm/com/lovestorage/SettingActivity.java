package train.zpm.com.lovestorage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.zpm.sql.Params;
//设置界面activity，继承类 AppCompatActivity。继承接口View.OnClickListener
public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner mSpinneRefresh;
    private Spinner mSpinnerShowway;
    private ImageButton  mImagrBtn;
    private SharedPreferences sp;
    private Button mBtnSave;
    private int refreshway=0;
    private int refreshtime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        //获取SharedPreferences，保存设置参数
        sp=getSharedPreferences("settingpara", 0);
        //根据id查找控件
        mSpinnerShowway=(Spinner)findViewById(R.id.setting_showway);
        mSpinneRefresh=(Spinner)findViewById(R.id.setting_refresh);
        mImagrBtn=(ImageButton)findViewById(R.id.setting_back);
        mBtnSave=(Button)findViewById(R.id.setting_save);
        mSpinneRefresh.setSelection(sp.getInt("refresh", 0));
        mSpinnerShowway.setSelection(sp.getInt("showway", 0));
        mImagrBtn.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_back:
                //点击返回结束
                finish();
                break;
            case R.id.setting_save:
                //点击保存按钮后，将显示参数保存到SharedPreferences
                SharedPreferences.Editor editor=sp.edit();
                editor.putInt("refresh", mSpinneRefresh.getSelectedItemPosition());
                editor.putInt("showway", mSpinnerShowway.getSelectedItemPosition());
                editor.commit();
                Params.setRefreshTime(mSpinneRefresh.getSelectedItemPosition());
                Params.setRefreshway(mSpinnerShowway.getSelectedItemPosition());
                Intent intent =new Intent(SettingActivity.this,TimerWidgetService.class);
                intent.putExtra("way",mSpinnerShowway.getSelectedItemPosition());
                intent.putExtra("time",mSpinneRefresh.getSelectedItemPosition());
                stopService(intent);
               startService(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
