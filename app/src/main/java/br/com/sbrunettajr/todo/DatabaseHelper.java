package br.com.sbrunettajr.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

import br.com.sbrunettajr.todo.models.Todo;

public class DatabaseHelper extends SQLiteOpenHelper {

    public Context context;
    private static final String DATABASE_NAME = "todo.sql";
    private static final int DATABASE_VERSION = 2;

    private final String CREATE_TABLE_TODO =
        "CREATE TABLE todo(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "text TEXT NOT NULL, " +
            "datetime TEXT NOT NULL, " +
            "done INTEGER NOT NULL" +
        ");";

    private final String DROP_TABLE_TODO =
            "DROP TABLE IF EXISTS todo;";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TODO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_TODO);
        onCreate(db);
    }

    public List<Todo> selectTodo() {
        List<Todo> todos = new LinkedList();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorTodo = db.query("todo", null, null, null, null, null, null);

        while (cursorTodo.moveToNext()) {
            Todo todo = new Todo();
            todo.id = cursorTodo.getLong(cursorTodo.getColumnIndex("id"));
            todo.text = cursorTodo.getString(cursorTodo.getColumnIndex("text"));
            todo.dateTime = cursorTodo.getString(cursorTodo.getColumnIndex("datetime"));
            todo.done = cursorTodo.getInt(cursorTodo.getColumnIndex("done")) == 1;

            todos.add(todo);
        }
        cursorTodo.close();
        db.close();
        return todos;
    }

    public void checkTodo(long id, boolean checked) {
        ContentValues values = new ContentValues();
        values.put("done", checked);

        update(id, values);
    }

    public void saveTodo(Todo todo) {
        ContentValues values = new ContentValues();
        values.put("text", todo.text);
        values.put("datetime", todo.dateTime);
        values.put("done", todo.done);

        if (todo.id != 0) {
            update(todo.id, values);
        } else {
            insert(values);
        }
    }

    private void insert(ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert("todo", null, values);
        db.close();
    }

    private void update(long id, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.update("todo", values, "id=" + id, null);
        db.close();
    }

    public void remove(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("todo", "id=" + id, null);
        db.close();
    }

}
