package us.koller.todolist;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import us.koller.todolist.Todolist.Event;
import us.koller.todolist.Util.ThemeHelper;

/**
 * Created by Lukas on 25.08.2016.
 */
public class ImportListViewAdapter extends ArrayAdapter<Event> {
    private ThemeHelper helper;

    public boolean eventsImport = true;

    //public Context context;

    private int padding;

    public ImportListViewAdapter(Context context, ArrayList<Event> events, ThemeHelper helper) {
        super(context, 0, events);
        this.helper = helper;

        //this.context = context;

        float scale = context.getResources().getDisplayMetrics().density;
        padding = (int) (20 * scale + 0.5f);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Event e = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.widget_list_row, parent, false);
        }

        int color_index = 1;
        String whatToDo = "Error";
        if(e != null){
            color_index = e.getColor();
            whatToDo = e.getWhatToDo();
        }

        TextView text = (TextView) convertView.findViewById(R.id.list_row_text);

        text.setText(whatToDo);

        text.getBackground().setColorFilter(helper.getEventColor(color_index), PorterDuff.Mode.SRC_IN);
        text.setTextColor(helper.getEventTextColor(color_index));
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);

        text.setPadding(padding, padding, padding, padding);

        return convertView;
    }
}
