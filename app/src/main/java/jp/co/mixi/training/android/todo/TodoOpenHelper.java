package jp.co.mixi.training.android.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import jp.co.mixi.training.android.todo.entity.TodoEntity;

/**
 * Created by Tomoya Miwa on 2015/04/16.
 */
public class TodoOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Todo.db";

    private static final String COMMON_COLUMN_NAME_CREATE_AT = "craete_at";
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
}
