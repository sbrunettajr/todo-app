package br.com.sbrunettajr.todo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.com.sbrunettajr.todo.DatabaseHelper;
import br.com.sbrunettajr.todo.R;
import br.com.sbrunettajr.todo.adapters.TodoAdapter;
import br.com.sbrunettajr.todo.models.Todo;

public class MainActivity extends AppCompatActivity {

    private ListView list_todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTodo(null);
            }
        });

        list_todo = (ListView) findViewById(R.id.list_todo);
        list_todo.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Todo todo = (Todo) parent.getAdapter().getItem(position);

                        openTodo(todo);
                    }
                }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();

        List<Todo> todos = new DatabaseHelper(this).selectTodo();
        list_todo.setAdapter(new TodoAdapter(this, todos));
    }

    private void openTodo(Todo todo) {
        Intent intent = new Intent(MainActivity.this, TodoActivity.class);

        if (todo != null) {
            intent.putExtra("todo", todo);
        }
        startActivity(intent);
    }

}