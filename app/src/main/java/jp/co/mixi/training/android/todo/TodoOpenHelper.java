package jp.co.mixi.training.android.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.mixi.training.android.todo.entity.TodoEntity;

/**
 * Created by Tomoya Miwa on 2015/04/16.
 */
public class TodoOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Todo.db";

    private static final String COMMON_COLUMN_NAME_CREATE_AT = "create_at";
    private static final String COMMON_COLUMN_NAME_UPDATE_AT = "update_at";

    private static final String TODO_TABLE_CREATE =
            "CREATE TABLE " + TodoEntity.TODO_TABLE_NAME + " (" +
                    TodoEntity._ID + " INTEGER PRIMARY KEY," +
                    TodoEntity.COLUMN_NAME_TODO_TITLE + " TEXT NOT NULL," +
                    COMMON_COLUMN_NAME_CREATE_AT + " INTEGER NOT NULL," +
                    COMMON_COLUMN_NAME_UPDATE_AT + " INTEGER NOT NULL);";

    private static final String TODO_TABLE_DELETE =
            "DROP TABLE IF EXISTS " + TodoEntity.TODO_TABLE_NAME;

    public TodoOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TODO_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TODO_TABLE_DELETE);
        onCreate(db);
    }

    public long insertTodo(TodoEntity todo) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            Date date = new Date();
            ContentValues values = new ContentValues();
            values.put(TodoEntity.COLUMN_NAME_TODO_TITLE, todo.getTitle());
            values.put(COMMON_COLUMN_NAME_CREATE_AT, date.getTime());
            values.put(COMMON_COLUMN_NAME_UPDATE_AT, date.getTime());
            long resId = db.insert(TodoEntity.TODO_TABLE_NAME, null, values);
            db.setTransactionSuccessful();
            return resId;
        } finally {
            db.endTransaction();
        }
    }

    public long updateTodo(TodoEntity todo) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            String selection = BaseColumns._ID + " = ?";
            String[] selectionArgs = {
                    String.valueOf(todo.getId())
            };
            Date date = new Date();
            ContentValues values = new ContentValues();
            values.put(TodoEntity.COLUMN_NAME_TODO_TITLE, todo.getTitle());
            values.put(COMMON_COLUMN_NAME_UPDATE_AT, date.getTime());
            int count = db.update(TodoEntity.TODO_TABLE_NAME, values, selection, selectionArgs);
            db.setTransactionSuccessful();
            return count;
        } finally {
            db.endTransaction();
        }
    }

    public List<TodoEntity> loadTodoAll() {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                TodoEntity._ID,
                TodoEntity.COLUMN_NAME_TODO_TITLE
        };

        Cursor cursor = null;
        List<TodoEntity> list = new ArrayList<>();
        try {
            cursor = db.query(
                    TodoEntity.TODO_TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    BaseColumns._ID + " DESC"
            );
            boolean hasNext = cursor.moveToFirst();
            while (hasNext) {
                TodoEntity entity = new TodoEntity();
                entity.setId(cursor.getLong(cursor.getColumnIndex(TodoEntity._ID)));
                entity.setTitle(cursor.getString(cursor.getColumnIndex(TodoEntity.COLUMN_NAME_TODO_TITLE)));
                list.add(entity);
                hasNext = cursor.moveToNext();
            }
        } finally {
            if(cursor != null) cursor.close();
        }

        return list;
    }

    public boolean isExist(TodoEntity entity) {
        if (entity.getId() != 0) {
            return true;
        }
        return false;
    }
}
