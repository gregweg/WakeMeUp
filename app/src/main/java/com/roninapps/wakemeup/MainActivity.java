package com.roninapps.wakemeup;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ListActivity {

    private TextView headingText;
    private Typeface font;

    public final Calendar cal = Calendar.getInstance();
    public final Date dt = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        headingText = (TextView) findViewById(R.id.heading_tv);
        font = Typeface.createFromAsset(getAssets(), "fonts/OpenSans.ttf");
        headingText.setTypeface(font);

        registerForContextMenu(getListView());
    }

    private Cursor createCursor() {
        long time = cal.getTimeInMillis();
        Cursor c = RemindMe.db.rawQuery("SELECT * FROM Notification WHERE time BETWEEN " +
                                            time+" AND "+(time+86400000), null);
        startManagingCursor(c);
        return c;
    }

    @Override
    protected void onResume() {
        super.onResume();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.row,
                createCursor(),
                new String[] {Notification.COL_MSG, Notification.COL_DATETIME},
                new int[] {R.id.msg_tv, R.id.time_tv});
        adapter.setViewBinder(new ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view.getId() == R.id.msg_tv)
                    return false;

                TextView tv = (TextView)view;
                switch(view.getId()) {
                    case R.id.time_tv:
                        dt.setTime(cursor.getLong(columnIndex));
                        tv.setText(dt.getHours()+":"+dt.getMinutes());
                        break;
                }
                return true;
            }
        });
        setListAdapter(adapter);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.imageButton1:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.imageButton2:
                startActivity(new Intent(this, AddAlarmActivity.class));
                break;
            case R.id.imageButton3:
                break;
            case R.id.imageButton4:
                break;
        }
    }
}
