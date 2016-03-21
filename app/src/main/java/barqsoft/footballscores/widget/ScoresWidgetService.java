package barqsoft.footballscores.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.database.DatabaseContract;
import barqsoft.footballscores.service.myFetchService;

public class ScoresWidgetService extends RemoteViewsService {
    public static final String TAG = "ScoresWidgetService";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "onGetViewFactory");
        return new ScoresRemoteViewsFactory(getApplicationContext(), intent);
    }

    public class ScoresRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private final int TIME_COL_IND = 2;
        private final int HOME_COL_IND = 3;
        private final int AWAY_COL_IND = 4;
        private final int HOME_GOALS_COL_IND = 6;
        private final int AWAY_GOALS_COL_IND = 7;

        private int mAppWidgetId;
        private Cursor mCursor;
        private Context mContext;

        public ScoresRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
            Log.d(TAG, "onCreate");
            mCursor = getData();
        }

        @Override
        public void onDataSetChanged() {
            Log.d(TAG, "onDataSetChanged");
            mCursor = getData();
        }

        @Override
        public void onDestroy() {
            if (mCursor != null) {
                mCursor.close();
            }
        }

        @Override
        public int getCount() {
            if (mCursor != null)
                return mCursor.getCount();
            else
                return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.small_scores_widget);

            mCursor.moveToPosition(position);
            remoteViews.setTextViewText(R.id.home_name, mCursor.getString(HOME_COL_IND));
            remoteViews.setTextViewText(R.id.away_name, mCursor.getString(AWAY_COL_IND));
            remoteViews.setTextViewText(R.id.data_textview, mCursor.getString(TIME_COL_IND));
            remoteViews.setTextViewText(R.id.score_textview,
                    Utilies.getScores(mCursor.getInt(HOME_GOALS_COL_IND), mCursor.getInt(AWAY_GOALS_COL_IND)));

            Intent clickIntent = new Intent();
            clickIntent.putExtra(ScoresWidgetProvider.ITEM_POSITION, position);
            remoteViews.setOnClickFillInIntent(R.id.small_scores_widget_layout, clickIntent);

            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
                return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        private Cursor getData() {
            Log.d(TAG, "get widget data");
            Cursor cursor = null;
            Uri uri = DatabaseContract.scores_table.buildScoreWithDate();

            //int i = 2;
            //Date date = new Date(System.currentTimeMillis() + ((i - 2) * 86400000));
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat mformat = new SimpleDateFormat(mContext.getString(R.string.full_date_format));
            String[] selArgs = new String[1];
            selArgs[0] = mformat.format(date);
            cursor = mContext.getContentResolver().query(uri, null, null, selArgs, null);
            //Log.d(TAG, DatabaseUtils.dumpCursorToString(cursor));

            return cursor;
        }

    }


}
