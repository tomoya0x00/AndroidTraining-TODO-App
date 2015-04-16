package jp.co.mixi.training.android.todo.entity;

import android.provider.BaseColumns;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * TODOを表現するためのEntity
 * Created by Hideyuki.Kikuma on 2015/03/15.
 */
public class TodoEntity implements BaseColumns {
    private static final Gson GSON = new GsonBuilder().create();
    public static final String TODO_TABLE_NAME = "todo";
    public static final String COLUMN_NAME_TODO_TITLE = "title";

    private String title;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String toString() {
        return title;
    }

    public String toJson() {
        return GSON.toJson(this);
    }

    public static TodoEntity fromJson(String json) {
        return GSON.fromJson(json, TodoEntity.class);
    }
    //TODO builderパターンにする
}
