package train.zpm.com.lovestorage;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;


import com.leo.gesturelibray.enums.LockMode;
import com.leo.gesturelibray.util.StringUtils;
import com.leo.gesturelibray.view.CustomLockView;

import java.util.Timer;
import java.util.TimerTask;
/*
解锁界面，继承类AppCompatActivity。继承接口View.OnClickListener
 */
public class MainActivity extends AppCompatActivity implements OnClickListener {

    private CustomLockView mCustomView;
    private TextView tvHint;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        mCustomView=(CustomLockView)findViewById(R.id.lv_lock);
        tvHint=(TextView)findViewById(R.id.main_hinttext);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.id_nv_menu);
        method();
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(mNavigationView);


    }

    private void method() {
        if (StringUtils.isEmpty(PasswordUtil.getPin(this))) {
            Toast.makeText(getBaseContext(), "请先设置密码", Toast.LENGTH_SHORT).show();
            setLockMode(LockMode.SETTING_PASSWORD, null, "");
            mCustomView.setPressed(false);
        }
        else {
            setLockMode(LockMode.VERIFY_PASSWORD, PasswordUtil.getPin(this), "验证密码");
            mCustomView.setPressed(true);
        }

        mCustomView.setShow(true);
        //允许最大输入次数
        mCustomView.setErrorNumber(3);
        mCustomView.setPasswordMinLength(4);
        mCustomView.setSavePin(true);
        mCustomView.setSaveLockKey(Contants.PASS_KEY);
        mCustomView.setOnCompleteListener(onCompleteListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.action_clear:
                actionSecondActivity(LockMode.CLEAR_PASSWORD);
                break;
            case R.id.action_edit:
                actionSecondActivity(LockMode.EDIT_PASSWORD);
                break;
            case R.id.action_settings:
                actionSecondActivity(LockMode.SETTING_PASSWORD);
                break;
            case R.id.action_verify:
                actionSecondActivity(LockMode.VERIFY_PASSWORD);
                break;
        }
        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {

    }
    private void actionSecondActivity(LockMode mode) {
        if (mode != LockMode.SETTING_PASSWORD) {
            if (StringUtils.isEmpty(PasswordUtil.getPin(this))) {
                Toast.makeText(getBaseContext(), "请先设置密码", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(Contants.INTENT_SECONDACTIVITY_KEY, mode);
        startActivityForResult(intent, 1);
        //startActivity(intent);
    }
    private void setLockMode(LockMode mode, String password, String msg) {
        mCustomView.setMode(LockMode.VERIFY_PASSWORD);
        mCustomView.setErrorNumber(3);
        //mCustomView.setClearPasssword(false);

        if (mode !=LockMode. SETTING_PASSWORD) {
            tvHint.setText("请输入已经设置过的密码");
            mCustomView.setOldPassword(password);
        } else {
            tvHint.setText("请输入要设置的密码");
        }
        // tvText.setText(msg);
    }
    CustomLockView.OnCompleteListener onCompleteListener = new CustomLockView.OnCompleteListener() {
        @Override
        public void onComplete(String password, int[] indexs) {
            tvHint.setText(getPassWordHint());
            Intent intent =new Intent( MainActivity.this,WriteActivity.class);
            startActivity(intent);
            finish();
        }

        @Override
        public void onError(String errorTimes) {
            tvHint.setText("密码错误，还可以输入" + errorTimes + "次");
        }

        @Override
        public void onPasswordIsShort(int passwordMinLength) {
            tvHint.setText("密码不能少于" + passwordMinLength + "个点");
        }

        @Override
        public void onAginInputPassword(LockMode mode, String password, int[] indexs) {
            tvHint.setText("请再次输入密码");
        }


        @Override
        public void onInputNewPassword() {
            tvHint.setText("请输入新密码");
        }

        @Override
        public void onEnteredPasswordsDiffer() {
            tvHint.setText("两次输入的密码不一致");
        }

        @Override
        public void onErrorNumberMany() {
            tvHint.setText("密码错误次数超过限制，不能再输入");
        }

    };
    private String getPassWordHint() {
        String str = null;
        switch (mCustomView.getMode()) {
            case SETTING_PASSWORD:
                str = "密码设置成功";
                break;
            case EDIT_PASSWORD:
                str = "密码修改成功";
                break;
            case VERIFY_PASSWORD:
                str = "密码正确";
                break;
            case CLEAR_PASSWORD:
                str = "密码已经清除";
                break;
        }
        return str;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        method();
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void setupDrawerContent(NavigationView navigationView)
    {
        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        switch(menuItem.getItemId()){
                            case R.id.action_clear:
                                actionSecondActivity(LockMode.CLEAR_PASSWORD);
                                break;
                            case R.id.action_edit:
                                actionSecondActivity(LockMode.EDIT_PASSWORD);
                                break;
                            case R.id.action_settings:
                                actionSecondActivity(LockMode.SETTING_PASSWORD);
                                break;
                            case R.id.action_verify:
                                actionSecondActivity(LockMode.VERIFY_PASSWORD);
                                break;
                        }
                        return true;
                    }
                });
    }
    private static boolean first = false;
    private Timer ExitTimer = new Timer();


    class ExitCleanTask extends TimerTask {

        @Override
        public void run() {

            Log.e("ExitCleanTask", "Run in!!!! ");
            first = false;
        }
    }

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
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                ExitTimer.schedule(new ExitCleanTask(), 2000);
            }

            //finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
