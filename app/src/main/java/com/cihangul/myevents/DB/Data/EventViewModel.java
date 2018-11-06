package com.cihangul.myevents.DB.Data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.cihangul.myevents.DB.Models.Event;

import java.util.List;

public class EventViewModel extends AndroidViewModel {


    private EventRepository eventRepository;

    public EventViewModel(@NonNull Application application) {
        super(application);
        eventRepository = new EventRepository(application);
    }

    public void insertEvent(Event event) {
        eventRepository.insertEvent(event);
    }

    public void updateEvent(Event event) {
        eventRepository.updateEvent(event);
    }

    public void removeEvent(Event event) {
        eventRepository.removeEvent(event);
    }

    public LiveData<List<Event>> getEventList(int userId) {
        return eventRepository.getEventList(userId);
    }
}
