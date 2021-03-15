package br.com.sbrunettajr.todo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import br.com.sbrunettajr.todo.DatabaseHelper;
import br.com.sbrunettajr.todo.R;
import br.com.sbrunettajr.todo.models.Todo;

public class TodoActivity extends AppCompatActivity {

    private EditText edit_text;
    private EditText edit_date;
    private EditText edit_time;
    private Button button_save;

    private Todo todo = null;

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String dia = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
            String mes = month < 10 ? "0" + month : String.valueOf(month);
            String ano = String.valueOf(year);

            edit_date.setText(dia + "/" + mes + "/" + ano);
        }
    };

    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String horas = hourOfDay < 10 ? "0" + hourOfDay : String.valueOf(hourOfDay);
            String minutos = minute < 10 ? "0" + minute : String.valueOf(minute);

            edit_time.setText(horas + ":" + minutos);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        todo = (Todo) getIntent().getSerializableExtra("todo");

        String title = todo == null ? "Cadastrar" : "Atualizar";

        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit_text = (EditText) findViewById(R.id.edit_text);
        edit_date = (EditText) findViewById(R.id.edit_date);
        edit_time = (EditText) findViewById(R.id.edit_time);

        if (todo != null) {
            String[] dateTime = todo.dateTime.split(" ");

            edit_text.setText(todo.text);
            edit_date.setText(dateTime[0]);
            edit_time.setText(dateTime[1]);
        }

        button_save = (Button) findViewById(R.id.button_save);
        button_save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isTodoValid()) {
                            saveTodo();
                            finish();
                        } else {
                            Toast.makeText(TodoActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        edit_date.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar c = Calendar.getInstance();

                        int dia = c.get(Calendar.DAY_OF_MONTH);
                        int mes = c.get(Calendar.MONTH);
                        int ano = c.get(Calendar.YEAR);

                        new DatePickerDialog(TodoActivity.this, dateSetListener, ano, mes, dia).show();
                    }

                }
        );

        edit_time.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar c = Calendar.getInstance();

                        int horas = c.get(Calendar.HOUR_OF_DAY);
                        int minutos = c.get(Calendar.MINUTE);

                        new TimePickerDialog(TodoActivity.this, timeSetListener, horas, minutos, true).show();
                    }
                }
        );

    }

    public boolean isTodoValid() {
        return !edit_text.getText().toString().isEmpty()
                && !edit_date.getText().toString().isEmpty()
                && !edit_time.getText().toString().isEmpty();
    }

    public void saveTodo() {
        if (todo == null) {
            todo = new Todo();
        }
        todo.text = edit_text.getText().toString();
        todo.dateTime = edit_date.getText().toString() + " " + edit_time.getText().toString();

        new DatabaseHelper(this).saveTodo(todo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (todo != null) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.action_remove) {
            new DatabaseHelper(this).remove(todo.id);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

