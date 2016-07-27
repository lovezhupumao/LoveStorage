package train.zpm.com.lovestorage;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.zpm.sql.DbSql;
import com.zpm.sql.Params;
import com.zpm.sql.SqlDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/*
“详细界面”类，点击桌面挂件显示
 */
public class DetailActivity extends Activity {

    private TextView mTxtHeader;
    private TextView mTxtTime;
    private TextView mTxtContent;
    private  int showway=0;
    private int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        DbSql.createDb(this);
        //获取数据库所有数据
        ArrayList<SqlDataModel> list = DbSql.liteOrm.query(SqlDataModel.class);
        //从params类中获取当前显示内容标号
        index=Params.getShowIndex();
        //查根据id找控件
        mTxtHeader=(TextView)findViewById(R.id.detail_head);
        mTxtTime=(TextView)findViewById(R.id.detail_time);
        mTxtContent=(TextView)findViewById(R.id.detail_content);
        //设置显示内容
        mTxtHeader.setText(list.get(index).getText_header());
        mTxtTime.setText(showway+"/"+index+"记录时间：" + list.get(index).getSetup_Time());
        mTxtContent.setText(list.get(index).getText_content());
    }


}
