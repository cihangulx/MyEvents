package com.cihangul.myevents.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cihangul.myevents.Adapters.EventAdapter;
import com.cihangul.myevents.DB.Data.EventViewModel;
import com.cihangul.myevents.DB.Models.Event;
import com.cihangul.myevents.DB.Models.User;
import com.cihangul.myevents.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements EventAdapter.OnItemClickListener {


    public static final int ADD_EVENT_REQUEST = 1;
    public static final int UPDATE_EVENT_REQUEST = 2;

    private RecyclerView recyclerView;
    private EventViewModel eventViewModel;//EventViewModel instance
    private TextView tips;//Message box

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));

        //Contains Remove Event Message
        tips = findViewById(R.id.tips);

        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        eventViewModel.getEventList(getUser().getUserId()).observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable List<Event> events) {

                //if first event set new Adapter else update data
                if (recyclerView.getAdapter() == null) {
                    recyclerView.setAdapter(new EventAdapter(MainActivity.this, events, MainActivity.this));
                } else {
                    ((EventAdapter) recyclerView.getAdapter()).updateData(events);
                }
                //if events >0 visibility GONE else VISIBLE
                tips.setVisibility(events != null ? events.size() > 0 ? View.GONE : View.VISIBLE : View.VISIBLE);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                eventViewModel.removeEvent(((EventAdapter) recyclerView.getAdapter()).getItem(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

    }

    /**
     * @param v AddEvent Button request add event action
     */
    public void fabClick(View v) {
        startActivityForResult(new Intent(this, AddEvent.class), ADD_EVENT_REQUEST);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            removeSession();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @param requestCode 1 for add 2 for update
     * @param resultCode
     * @param data        store data...
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_EVENT_REQUEST && resultCode == RESULT_OK) {
            Event event = new Event();
            event.setUserId(getUser().getUserId());
            event.setTitle(data.getStringExtra(AddEvent.EXTRA_TITLE));
            event.setSubTitle(data.getStringExtra(AddEvent.EXTRA_SUBTITLE));
            event.setDate(new Date(data.getLongExtra(AddEvent.EXTRA_DATE, new Date().getTime())));
            eventViewModel.insertEvent(event);
        } else if (requestCode == UPDATE_EVENT_REQUEST && resultCode == RESULT_OK) {
            Event event = new Event();
            event.setUserId(getUser().getUserId());
            event.setEventId(data.getIntExtra(UpdateEvent.EXTRA_ID, -1));
            event.setTitle(data.getStringExtra(UpdateEvent.EXTRA_TITLE));
            event.setSubTitle(data.getStringExtra(UpdateEvent.EXTRA_SUBTITLE));
            event.setDate(new Date(data.getLongExtra(UpdateEvent.EXTRA_DATE, new Date().getTime())));
            eventViewModel.updateEvent(event);
        }
    }

    /**
     * @return Saved User
     */
    public User getUser() {
        return new Gson().fromJson(getSharedPreferences("user", MODE_PRIVATE).getString("user", null), User.class);

    }

    //Handle edit button click event
    @Override
    public void OnItemClick(Event event) {
        startActivityForResult(new Intent(this, UpdateEvent.class).putExtra("event", event.toString()), UPDATE_EVENT_REQUEST);
    }

    // TODO: 6.11.2018 AccountManager Kullanılabilir

    /**
     * Simple DON'T use for real projects
     */
    private void removeSession() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("user");
        editor.apply();
        startActivity(new Intent(this, Login.class));
        finish();
    }
}
