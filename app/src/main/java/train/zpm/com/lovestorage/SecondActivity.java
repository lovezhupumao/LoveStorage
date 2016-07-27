package train.zpm.com.lovestorage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.leo.gesturelibray.enums.LockMode;
import com.leo.gesturelibray.view.CustomLockView;
/*
密码修改界面
包括设置密码、修改密码清楚密码
 */
public class SecondActivity extends AppCompatActivity {

    private CustomLockView mCustomView;
    private TextView tvHint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mCustomView=(CustomLockView)findViewById(R.id.second_lock);
        tvHint=(TextView)findViewById(R.id.second_text);
        LockMode lockMode = (LockMode) getIntent().getSerializableExtra(Contants.INTENT_SECONDACTIVITY_KEY);
        setLockMode(lockMode);
        mCustomView.setShow(true);
        //允许最大输入次数
        mCustomView.setErrorNumber(3);
        //密码最少位数
        mCustomView.setPasswordMinLength(4);
        //编辑密码或设置密码时，是否将密码保存到本地，配合setSaveLockKey使用
        mCustomView.setSavePin(true);
        //保存密码Key
        mCustomView.setSaveLockKey(Contants.PASS_KEY);
        mCustomView.setOnCompleteListener(onCompleteListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //设置锁的模式 ，有四种。分别为CLEAR_PASSWORD、EDIT_PASSWORD、CLEAR_PASSWORD、VERIFY_PASSWORD
    private void setLockMode(LockMode mode, String password, String msg) {
        mCustomView.setMode(mode);
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
    //监听解锁结果
    CustomLockView.OnCompleteListener onCompleteListener = new CustomLockView.OnCompleteListener() {
        @Override
        public void onComplete(String password, int[] indexs) {
            tvHint.setText(getPassWordHint());
            setResult(1,getIntent());
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
    private void setLockMode(LockMode mode) {
        String str = "";
        switch (mode) {
            case CLEAR_PASSWORD:
                str = "清除密码";
                setLockMode(LockMode.CLEAR_PASSWORD, PasswordUtil.getPin(this), str);
                break;
            case EDIT_PASSWORD:
                str = "修改密码";
                setLockMode(LockMode.EDIT_PASSWORD, PasswordUtil.getPin(this), str);
                break;
            case SETTING_PASSWORD:
                str = "设置密码";
                setLockMode(LockMode.SETTING_PASSWORD, null, str);
                break;
            case VERIFY_PASSWORD:
                str = "验证密码";
                setLockMode(LockMode.VERIFY_PASSWORD, PasswordUtil.getPin(this), str);
                break;
        }
       // tvText.setText(str);
    }
}
