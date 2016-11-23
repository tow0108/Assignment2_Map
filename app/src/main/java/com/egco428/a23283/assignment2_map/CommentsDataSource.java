package com.egco428.a23283.assignment2_map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class CommentsDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbhelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_USERNAME,MySQLiteHelper.COLUMN_PASSWORD,MySQLiteHelper.COLUMN_LATITUDE,MySQLiteHelper.COLUMN_LONGTITUDE};

    public CommentsDataSource(Context context){
        dbhelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbhelper.getWritableDatabase();
    }

    public void close(){
        dbhelper.close();
    }

    public Comment createComment(String username,String password, String latitude, String longtitude){
        ContentValues value = new ContentValues();

        value.put(MySQLiteHelper.COLUMN_USERNAME, username);
        value.put(MySQLiteHelper.COLUMN_PASSWORD, password);
        value.put(MySQLiteHelper.COLUMN_LATITUDE, latitude);
        value.put(MySQLiteHelper.COLUMN_LONGTITUDE, longtitude);

        open();
        long insertID = database.insert(MySQLiteHelper.TABLE_COMMENTS,null,value);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,allColumns, MySQLiteHelper.COLUMN_ID + " = " +insertID,null,null,null,null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteComment(Comment comment){
        long id = comment.getId();
        System.out.println("Comment deleted with id: "+id);
        database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID+ " = "+id,null);
    }

    public List<Comment> getAllComments(){
        List<Comment> comments = new ArrayList<Comment>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS, allColumns,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        return comments;
    }

    public Comment cursorToComment(Cursor cursor){
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setUsername(cursor.getString(1));
        comment.setPassword(cursor.getString(2));
        comment.setLatitude(cursor.getString(3));
        comment.setLongtitude(cursor.getString(4));
        return comment;
    }
}
