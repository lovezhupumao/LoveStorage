package train.zpm.com.lovestorage;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import com.zpm.sql.DbSql;
import com.zpm.sql.SqlDataModel;
/*
"历史记录"类
 */
public class HistoryActivity extends BaseActivity {

    private ImageButton mImageBtn_back;
    private ExpandableListView mListView;
    private ExpandableListViewAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        overridePendingTransition(Animation.INFINITE, Animation.INFINITE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        mImageBtn_back=(ImageButton)findViewById(R.id.history_back);
        mListView=(ExpandableListView)findViewById(R.id.history_listview);
        mListAdapter=new ExpandableListViewAdapter(this);
        mListView.setAdapter(mListAdapter);
        mListView.setGroupIndicator(getResources().getDrawable(R.drawable.expand_selector));
        mImageBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryActivity.this.finish();
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               showdialog(position);
                return false;
            }
        });

    }
    //内容修改后，退出提示对话框
    private void showdialog( final int position) {
        AlertDialog isExit = new AlertDialog.Builder(this).create();
        // 设置对话框标
        isExit.setTitle("系统提示");
        // 设置对话框消�?????
        isExit.setMessage("是否删除该文本记录");

        isExit.setButton(DialogInterface.BUTTON_POSITIVE, "是", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                DbSql.liteOrm.delete(DbSql.liteOrm.query(SqlDataModel.class).get(position));
                mListAdapter = new ExpandableListViewAdapter(HistoryActivity.this);
                mListView.setAdapter(mListAdapter);
            }
        });

        isExit.setButton(DialogInterface.BUTTON_NEUTRAL, "否", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        isExit.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }
}
