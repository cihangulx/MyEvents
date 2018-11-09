package com.cihangul.myevents.DB.Models;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

@Entity(tableName = "events")
public class Event {

    @PrimaryKey(autoGenerate = true)
    private int eventId;
    private int userId;
    private String title;
    private String subTitle;
    private Date date;

    public Event(int eventId, int userId, String title, String subTitle, Date date) {
        this.eventId = eventId;
        this.userId = userId;
        this.title = title;
        this.subTitle = subTitle;
        this.date = date;
    }

    @Ignore
    public Event() {
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    /**
     * @return now - selected date
     */
    public String getTimeRemaining() {
        Calendar calendar = Calendar.getInstance();

        long timeDiff = date.getTime() - calendar.getTime().getTime();
        long days = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);

        if (days > 0) {
            return days + " Days Remaining";
        } else if (days < 0) {
            return Math.abs(days) + " Days Past";
        } else {
            long hours = TimeUnit.HOURS.convert(timeDiff, TimeUnit.MILLISECONDS);
            if (hours < 0) {
                return Math.abs(hours) + " Hours Past";
            } else {
                return Math.abs(hours) + " Hours Remaining";
            }
        }
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}