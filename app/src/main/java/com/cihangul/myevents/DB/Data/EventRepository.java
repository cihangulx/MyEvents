package com.cihangul.myevents.DB.Data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.cihangul.myevents.DB.Models.Event;

import java.util.List;

public class EventRepository {

    private EventDao eventDao;

    EventRepository(Application application) {
        eventDao = AppDatabase.getInstance(application).eventDao();
    }

    public void insertEvent(Event event) {
        new InsertEventAsyncTask(eventDao).execute(event);
    }

    public void removeEvent(Event event) {
        new RemoveEventAsyncTask(eventDao).execute(event);
    }

    public void updateEvent(Event event) {
        new UpdateEventAsyncTask(eventDao).execute(event);
    }


    public LiveData<List<Event>> getEventList(int userId) {
        return eventDao.getEventList(userId);
    }

    public static class InsertEventAsyncTask extends AsyncTask<Event, Void, Void> {
        EventDao eventDao;

        InsertEventAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            eventDao.addEvent(events[0]);
            return null;
        }
    }

    public static class RemoveEventAsyncTask extends AsyncTask<Event, Void, Void> {
        EventDao eventDao;

        RemoveEventAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            eventDao.removeEvent(events[0]);
            return null;
        }
    }

    public static class UpdateEventAsyncTask extends AsyncTask<Event, Void, Void> {
        EventDao eventDao;

        UpdateEventAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            eventDao.updateEvent(events[0]);
            return null;
        }
    }


}
