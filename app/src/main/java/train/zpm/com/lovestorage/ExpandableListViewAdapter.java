package train.zpm.com.lovestorage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.zpm.sql.DbSql;
import com.zpm.sql.SqlDataModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/28 0028.
 * 扩展listview适配器
 */
public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
    private  Context mContext;
    private ArrayList<SqlDataModel> mSqlDataList;
    public ExpandableListViewAdapter(Context context) {
        this.mContext=context;
        DbSql.createDb(context);
        mSqlDataList=new ArrayList<SqlDataModel>();
        mSqlDataList=DbSql.liteOrm.query(SqlDataModel.class);

    }

    @Override
    public int getGroupCount() {
        return mSqlDataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mSqlDataList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mSqlDataList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext	.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_expandable_parent, null);
        }
        TextView tv1 = (TextView) convertView.findViewById(R.id.expandable_parent_header);
        TextView tv2 = (TextView) convertView.findViewById(R.id.expandable_parent_setuptime);
        tv1.setText(mSqlDataList.get(groupPosition).getText_header());
        tv2.setText(mSqlDataList.get(groupPosition).getSetup_Time());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_expandable_child, null);
        }
        TextView tv1 = (TextView) convertView.findViewById(R.id.expandable_child_content);
        tv1.setText(mSqlDataList.get(groupPosition).getText_content());
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,ScanActivity.class);
                intent.putExtra("index",mSqlDataList.get(groupPosition).getId());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
