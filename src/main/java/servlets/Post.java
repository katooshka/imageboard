package servlets;

import javax.validation.constraints.Null;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {
    private final String name;
    public String message;
    private final Date date;
    private final int number;
    private final int threadNumber;
    private Integer threadLength;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Post(String name, String message, Date date, int threadNumber) {
        this.name = name;
        this.message = message;
        this.date = date;
        this.number = 0;
        this.threadNumber = threadNumber;
        this.threadLength = null;
    }

    public Post(String name, String message, Date date, int number, int threadNumber) {
        this.name = name;
        this.message = message;
        this.date = date;
        this.number = number;
        this.threadNumber = threadNumber;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg){
        this.message = msg;
    }

    public String getDateAsString() {
        return df.format(date);
    }

    public Date getDate() {
        return date;
    }

    public int getNumber() {
        return number;
    }

    public int getThreadNumber() {return threadNumber;}

    public Integer getThreadLength() {
        return threadLength;
    }
}
