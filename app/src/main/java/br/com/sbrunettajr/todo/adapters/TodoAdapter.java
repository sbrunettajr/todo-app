package br.com.sbrunettajr.todo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import br.com.sbrunettajr.todo.DatabaseHelper;
import br.com.sbrunettajr.todo.R;
import br.com.sbrunettajr.todo.models.Todo;

public class TodoAdapter extends BaseAdapter {

    private Context context;

    private List<Todo> todos;

    private final LayoutInflater inflater;

    public TodoAdapter(Context context, List<Todo> todos) {
        this.context = context;
        this.todos = todos;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return todos.size();
    }

    @Override
    public Object getItem(int position) {
        return todos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return todos.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.todo_adapter, parent, false);

        final Todo todo = (Todo) getItem(position);

        final TextView text_text = (TextView) view.findViewById(R.id.text_text);
        final TextView text_datetime = (TextView) view.findViewById(R.id.text_datetime);
        final CheckBox check_done = (CheckBox) view.findViewById(R.id.check_done);

        check_done.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkTodo(todo.id, check_done.isChecked());
                    }
                }
        );
        text_text.setText(todo.text);
         text_datetime.setText(todo.dateTime);
        check_done.setChecked(todo.done);

        return view;
    }

    private void checkTodo(long id, boolean done) {
        new DatabaseHelper(context).checkTodo(id, done);
    }

}
