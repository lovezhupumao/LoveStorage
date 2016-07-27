package com.zpm.sql;

import android.content.Context;
import android.os.Environment;

import com.litesuits.orm.LiteOrm;
/*
数据库初始化类，生成lovestorage.db数据库文件，位置为存储卡根目录
 */
public class DbSql {
	public static String DB_NAME;
    public static LiteOrm liteOrm;
     
    public static void createDb(Context _activity){
        if (liteOrm==null) {
			liteOrm = LiteOrm.newCascadeInstance(_activity, Environment.getExternalStorageDirectory().getPath()+"/lovestorage.db");
		}
		liteOrm.setDebugged(true);
    }
    
}
