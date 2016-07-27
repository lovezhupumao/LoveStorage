package train.zpm.com.lovestorage;



import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.zpm.sql.DbSql;
import com.zpm.sql.SqlDataModel;

import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;

import me.james.biuedittext.BiuEditText;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton mImageBtn_back;
    private ImageButton mImageBtn_add;
    private Button mBtn_save;
    private BiuEditText mEditText_content;
    private BiuEditText mEditText_header;
    private boolean bIsChanged=false;
    private SqlDataModel msqldata;
    private String strfirstTemp="";
    private String strendTemp="";
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        DbSql.createDb(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        msqldata=new SqlDataModel();
        mImageBtn_back=(ImageButton)findViewById(R.id.edit_back);
        mImageBtn_add=(ImageButton)findViewById(R.id.edit_add);
        mBtn_save=(Button)findViewById(R.id.edit_save);
        mEditText_content=(BiuEditText)findViewById(R.id.edit_text);
        mEditText_header=(BiuEditText)findViewById(R.id.edit_header);
        mImageBtn_back.setOnClickListener(this);
        mImageBtn_add.setOnClickListener(this);
        mBtn_save.setOnClickListener(this);
        mEditText_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            strfirstTemp=s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bIsChanged = true;
                mBtn_save.setEnabled(true);
                strendTemp=s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mEditText_content.getText().toString().equals("")) {
                    mBtn_save.setEnabled(false);
                }
                if (strendTemp==strfirstTemp){
                    mBtn_save.setEnabled(false);
                    bIsChanged = false;
                }
            }
        });
        mEditText_header.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strfirstTemp=s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bIsChanged = true;
                mBtn_save.setEnabled(true);
                strendTemp=s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mEditText_header.getText().toString().equals("")) {
                    mBtn_save.setEnabled(false);
                }
                if (strendTemp==strfirstTemp){
                    mBtn_save.setEnabled(false);
                    bIsChanged = false;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_back:
                if (bIsChanged){
                showDialog();
                }
                else {
                    finish();
                }
                break;
            case  R.id.edit_add:
                Intent intent=new Intent(EditActivity.this,EditActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.edit_save:
                bIsChanged=false;
                saveToSql();
                break;
            default:
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK) {
            // 创建退出对话框

            if (bIsChanged) {
                showDialog();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showDialog() {
        AlertDialog isExit = new AlertDialog.Builder(this).create();
        // 设置对话框标�?????
        isExit.setTitle("系统提示");
        // 设置对话框消�?????
        isExit.setMessage("文本已更改，是否保存改变");

        isExit.setButton(DialogInterface.BUTTON_POSITIVE, "是", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveToSql();
                EditActivity.this.finish();
            }
        });
        isExit.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }

        });
        isExit.setButton(DialogInterface.BUTTON_NEUTRAL, "否", new  DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                EditActivity.this.finish();
            }
        });
        isExit.show();
    }

    private void saveToSql() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/d HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String strdate = formatter.format(curDate);
        String str_context=mEditText_content.getText().toString();
        String str_header=mEditText_header.getText().toString();

        if (str_header.equals("")){
            showToast("标题不能为空");
            return;
        }
        if (str_context.equals("")){
            showToast("内容不能为空");
            return;
        }
        msqldata.setSetup_Time(strdate);
        msqldata.setText_header(str_header);
        msqldata.setText_content(str_context);
        DbSql.liteOrm.save(msqldata);
        mBtn_save.setEnabled(false);
        showToast(DbSql.liteOrm.query(SqlDataModel.class).size() + "条");
    }
    private void showToast(String  str){
        Toast.makeText(EditActivity.this,str,Toast.LENGTH_LONG).show();
    }
}
