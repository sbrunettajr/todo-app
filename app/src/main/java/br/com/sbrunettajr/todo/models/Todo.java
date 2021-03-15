package br.com.sbrunettajr.todo.models;

import java.io.Serializable;

public class Todo implements Serializable {

    public long id;
    public String text;
    public String dateTime;
    public boolean done;

    public Todo() {
        id = 0;
        text = null;
        dateTime = null;
        done = false;
    }

    public Todo(long id, String text, String dateTime, boolean done) {
        this.id = id;
        this.text = text;
        this.dateTime = dateTime;
        this.done = done;

    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", dateTime=" + dateTime +
                ", done=" + done +
                '}';
    }

}
