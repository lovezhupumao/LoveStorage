package train.zpm.com.lovestorage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.widget.TextView;

import com.litesuits.orm.db.assit.QueryBuilder;
import com.zpm.sql.DbSql;
import com.zpm.sql.SqlDataModel;

public class ScanActivity extends BaseActivity {

    private TextView mTxtHeader;
    private TextView mTxtTime;
    private TextView mTxtContent;
    private SqlDataModel mdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        overridePendingTransition(Animation.INFINITE, Animation.INFINITE);
        DbSql.createDb(this);
        int index=getIntent().getIntExtra("index",0);
        mdata=DbSql.liteOrm.queryById((long)index,SqlDataModel.class);
        mTxtHeader=(TextView)findViewById(R.id.scan_head);
        mTxtTime=(TextView)findViewById(R.id.scan_time);
        mTxtContent=(TextView)findViewById(R.id.scan_content);
        mTxtHeader.setText(mdata.getText_header());
        mTxtTime.setText(mdata.getSetup_Time());
        mTxtContent.setText(mdata.getText_content());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scan, menu);
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

}
