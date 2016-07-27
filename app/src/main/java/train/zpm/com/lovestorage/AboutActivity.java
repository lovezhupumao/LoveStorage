package train.zpm.com.lovestorage;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
/*
*关于界面activity
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将activity的显示界面为activity_about
        setContentView(R.layout.activity_about);
        //找到activity_about.xml中id为about_text的textview控件
        TextView textView = (TextView)findViewById(R.id.about_text);
        //设置textView显示内容
        textView.setText("该软件可以记录平时生活点滴，可以查询，删除历史记录"+"\r\n"+"开发者：XXX");
    }


}
