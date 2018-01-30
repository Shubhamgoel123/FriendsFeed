package com.example.kachucool.friendsfeed;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Db_activity {

	final static String Key_Userid="user_id";

	final static String Database_Name="friendfeed0";
	final static String Database_Table="friendtable0";
	final static int Database_Version=1;
	
	Dbhelper ourhelper;
	
	final Context ourcontext;
	SQLiteDatabase ourdatabase;
	
	
	private static class Dbhelper extends SQLiteOpenHelper
	{
		public Dbhelper(Context context)
		{
			super(context,Database_Name,null,Database_Version);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE "+ Database_Table + "(" + Key_Userid + " TEXT NOT NULL);");
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + Database_Table);
			onCreate(db);
		}
	}
	
	public Db_activity(Context c)
	{
		ourcontext=c;
	}
	
	public Db_activity open()
	{
		ourhelper=new Dbhelper(ourcontext);
		ourdatabase=ourhelper.getWritableDatabase();
		return this;
	}
	
	public void close()
	{
		ourhelper.close();
	}

	public void entry(String id) {
		// TODO Auto-generated method stub
		
		ContentValues cv=new ContentValues();
		cv.put(Key_Userid,id);

		
		ourdatabase.insert(Database_Table, null,cv);
		
	}



	public void update(String id) {
		// TODO Auto-generated method stub

		ContentValues cv=new ContentValues();
		cv.put(Key_Userid,id);



		ourdatabase.update(Database_Table,cv,null,null);

	}

	public Integer getCount() {
		// TODO Auto-generated method stub
		
		String[] columns=new String[]{Key_Userid};
		 Cursor c=ourdatabase.query(Database_Table,columns,null,null,null,null,null,null);
		
		 Integer i=c.getCount();
		
		
		return i;
	}


    public void delete()
    {
        ourdatabase.execSQL("delete from "+ Database_Table);
    }

	public String getid() {
		String[] columns=new String[]{Key_Userid};
		 Cursor c=ourdatabase.query(Database_Table,columns,null,null,null,null,null,null);
		
		 c.moveToFirst();
		 return c.getString(0);
	}

	 }
