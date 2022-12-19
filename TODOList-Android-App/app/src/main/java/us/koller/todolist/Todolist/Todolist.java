package us.koller.todolist.Todolist;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import us.koller.todolist.RecyclerViewAdapters.MainRVAdapter;
import us.koller.todolist.Settings;

/**
 * Write a description of class Todolist here. Todolist manages your EVENTs.
 *
 * @author (Lukas Koller)
 * @version (1.0)
 */

public class Todolist {

    public static final String EVENTS_FILENAME = "events";
    private static final String ADDED_EVENTS_FILENAME = "removedEvents";
    private static final String REMOVED_EVENTS_FILENAME = "addedEvents";

    private Settings settings;
    private ArrayList<Event> todolist;
    private ArrayList<Event> adapter_list;

    //only needed for sync
    private ArrayList<Long> removedEvents;
    private ArrayList<Long> addedEvents;

    //to restore last deleted Event
    private Event lastRemovedEvent;
    private int lastRemovedEventPosition;

    public Todolist(Settings settings) {
        this.settings = settings;

        todolist = new ArrayList<>();

        removedEvents = new ArrayList<>();
        addedEvents = new ArrayList<>();
    }

    public void addEvent(MainRVAdapter mAdapter, Event e) {
        todolist.add(e);
        mAdapter.addItem(e);

        if ((boolean) settings.get(Settings.SYNC_ENABLED)) {
            addedEvents.add(e.getId());
        }
    }

    public void initData(Context context) {
        try {
            readData(context);
        } catch (JSONException | FileNotFoundException e) {
            //e.printStackTrace();
        }
    }

    public ArrayList<Event> initAdapterList() {
        adapter_list = new ArrayList<>();
        for (int i = 0; i < todolist.size(); i++) {
            if (settings.getCategory(todolist.get(i).getColor())) {
                adapter_list.add(todolist.get(i));
            }
        }
        return adapter_list;
    }

    public void restoreLastRemovedEvent() {
        if (lastRemovedEvent != null) {
            todolist.add(lastRemovedEventPosition, lastRemovedEvent);
            if ((boolean) settings.get(Settings.SYNC_ENABLED)) {
                removedEvents.remove(lastRemovedEvent.getId());
            }
            lastRemovedEvent = null;
        }
    }

    public void removeEvent(Event e) {
        //Only for removing Event that are not in the Adapter-List!!!!
        lastRemovedEventPosition = todolist.indexOf(e);
        lastRemovedEvent = e;
        todolist.remove(e);

        if ((boolean) settings.get(Settings.SYNC_ENABLED)) {
            removedEvents.add(e.getId());
        }
    }

    public void removeEvent(MainRVAdapter mAdapter, int index) {
        Event e = adapter_list.get(index);
        removeEvent(e);
        mAdapter.removeItem(index);
    }

    public int getIndexOfEventInAdapterListById(long id) {
        for (int i = 0; i < adapter_list.size(); i++) {
            if (adapter_list.get(i).getId() == id) {
                return i;
            }
        }
        return todolist.size();
    }

    public Event getEventById(long id) {
        for (int i = 0; i < todolist.size(); i++) {
            Event e = todolist.get(i);
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public int getEventIndexById(long id) {
        for (int i = 0; i < todolist.size(); i++) {
            Event e = todolist.get(i);
            if (e.getId() == id) {
                return i;
            }
        }
        return todolist.size();
    }

    public boolean isAlarmScheduled() {
        for (int i = 0; i < todolist.size(); i++) {
            if (todolist.get(i).hasAlarm()
                    && !hasAlarmFired((todolist.get(i)))) {
                return true;
            }
        }
        return false;
    }

    public void addOrRemoveEventFromAdapter(MainRVAdapter mAdapter) {
        Event[] tempAdapterList = new Event[adapter_list.size()];
        //Needed for running through the whole mAdapterlist
        for (int i = 0; i < adapter_list.size(); i++) {
            tempAdapterList[i] = adapter_list.get(i);
        }
        for (int i = tempAdapterList.length - 1; i >= 0; i--) {
            if (!settings.getCategory(tempAdapterList[i].getColor())) {
                mAdapter.removeItem(adapter_list.indexOf(tempAdapterList[i]));
            }
        }
        for (int i = 0; i < todolist.size(); i++) {
            if (settings.getCategory(todolist.get(i).getColor())
                    && !isEventInAdapterList(todolist.get(i))) {
                int index = getExpectedAdapterListPosition(todolist.get(i));
                mAdapter.addItem(index, todolist.get(i));
            }
        }
    }

    public boolean isEventInAdapterList(Event e) {
        for (int i = 0; i < adapter_list.size(); i++) {
            if (e.getId() == adapter_list.get(i).getId()) {
                return true;
            }
        }
        return false;
    }

    public int getExpectedAdapterListPosition(Event e) {
        int adapterListPosition = 0;
        for (int i = 0; i < todolist.size(); i++) {
            if (todolist.get(i).getId() == e.getId()) {
                return adapterListPosition;
            }
            if (settings.getCategory(todolist.get(i).getColor())) {
                adapterListPosition++;
            }
        }
        return adapter_list.size();
    }

    public int getAdapterIndexFromTodolistIndex(int todolist_index) {
        int adapterListPosition = 0;
        Event e = todolist.get(todolist_index);
        for (int i = 0; i < todolist.size(); i++) {
            if (todolist.get(i).getId() == e.getId()) {
                return adapterListPosition;
            }
            if (settings.getCategory(todolist.get(i).getColor())) {
                adapterListPosition++;
            }
        }
        return adapter_list.size();
    }

    public boolean isAdapterListTodolist() {
        if (adapter_list.size() != todolist.size()) {
            return false;
        }
        for (int i = 0; i < todolist.size(); i++) {
            if (adapter_list.get(i).getId() != todolist.get(i).getId()) {
                return false;
            }
        }
        return true;
    }

    public boolean isEventInTodolist(long id) {
        for (int i = 0; i < todolist.size(); i++) {
            if (todolist.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void eventMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(todolist, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(todolist, i, i - 1);
            }
        }
    }

    public boolean doesCategoryContainEvents(int category_index) {
        for (int i = 0; i < todolist.size(); i++) {
            if (todolist.get(i).getColor() == category_index) {
                return true;
            }
        }
        return false;
    }

    public void saveData(Context context) throws JSONException {
        saveFile(context, EVENTS_FILENAME, getData());
        //save removed Todos for sync
        if ((boolean) settings.get(Settings.SYNC_ENABLED)) {
            saveRemovedEvents(context);

            saveAddedEvents(context);
        }
    }

    private void saveRemovedEvents(Context context) throws JSONException {
        saveFile(context, REMOVED_EVENTS_FILENAME, getRemovedEventsString());
    }

    private void saveAddedEvents(Context context) throws JSONException {
        saveFile(context, ADDED_EVENTS_FILENAME, getAddedEventsString());
    }

    public void clearRemovedAndAddedEvents() {
        removedEvents.clear();
        addedEvents.clear();
    }

    public String getData() throws JSONException {
        JSONArray array = new JSONArray();
        for (int i = 0; i < todolist.size(); i++) {
            array.put(todolist.get(i).saveData());
        }
        return array.toString();
    }

    private String getRemovedEventsString() throws JSONException {
        JSONArray array = new JSONArray();
        for (int i = 0; i < removedEvents.size(); i++) {
            array.put(removedEvents.get(i));
        }
        return array.toString();
    }

    private String getAddedEventsString() throws JSONException {
        JSONArray array = new JSONArray();
        for (int i = 0; i < addedEvents.size(); i++) {
            array.put(addedEvents.get(i));
        }
        return array.toString();
    }

    private void readData(Context context) throws JSONException, FileNotFoundException {
        String data = readFile(context, EVENTS_FILENAME);
        JSONArray array = new JSONArray(data);
        for (int i = 0; i < array.length(); i++) {
            todolist.add(new Event(array.getJSONObject(i)));
        }

        // read removedEvents and addedEvents
        if ((boolean) settings.get(Settings.SYNC_ENABLED)) {
            String data_removedEvents = readFile(context, REMOVED_EVENTS_FILENAME);
            JSONArray array_removedEvents = new JSONArray(data_removedEvents);
            for (int i = 0; i < array_removedEvents.length(); i++) {
                removedEvents.add(array_removedEvents.getLong(i));
            }

            String data_addedEvents = readFile(context, ADDED_EVENTS_FILENAME);
            JSONArray array_addedEvents = new JSONArray(data_addedEvents);
            for (int i = 0; i < array_addedEvents.length(); i++) {
                addedEvents.add(array_addedEvents.getLong(i));
            }
        }
    }

    public ArrayList<Long> eventRemovedThroughNotificationButton(Context context)
            throws JSONException, FileNotFoundException {
        String data = readFile(context, EVENTS_FILENAME);
        JSONArray array = new JSONArray(data);
        ArrayList<Long> newList = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Event e = new Event(array.getJSONObject(i));
            newList.add(e.getId());
        }
        ArrayList<Long> currentList = new ArrayList<>();
        for (int i = 0; i < todolist.size(); i++) {
            currentList.add(todolist.get(i).getId());
        }
        currentList.removeAll(newList);
        return currentList;
    }

    private String readFile(Context context, String filename) {
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fis = context.openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    private void saveFile(Context context, String filename, String data)
            throws JSONException {
        try {
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getShareFileData(ArrayList<Event> eventsToShare)
            throws JSONException {
        JSONArray array = new JSONArray();
        for (int i = 0; i < eventsToShare.size(); i++) {
            array.put(eventsToShare.get(i).saveData());
        }
        return array.toString();
    }

    public void importEvents(ArrayList<Event> events) {
        for (int i = 0; i < events.size(); i++) {
            if (!isEventInTodolist(events.get(i).getId())) {
                todolist.add(events.get(i));
            }
        }
    }

    public String getSyncData() {
        JSONArray array = new JSONArray();
        long timeStamp = System.currentTimeMillis();
        try {
            array.put(0, timeStamp);
            array.put(1, new JSONArray(getData()));
            array.put(2, settings.getSelectedCategoriesJSON());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return array.toString();
    }

    public ArrayList<Long> getRemovedEvents() {
        return removedEvents;
    }

    public ArrayList<Long> getAddedEvents() {
        return addedEvents;
    }

    public boolean hasAlarmFired(Event e) {
        return (long) e.getAlarm().get(Alarm.TIME) < System.currentTimeMillis();
    }

    public Event getLastRemovedEvent() {
        return lastRemovedEvent;
    }

    public ArrayList<Event> getTodolistArray() {
        return todolist;
    }

    public ArrayList<Event> getAdapterList() {
        return adapter_list;
    }
}
