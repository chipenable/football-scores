package barqsoft.footballscores.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.service.myFetchService;

/**
 * Created by Pashgan on 02.01.2016.
 */
public class ScoresWidgetProvider extends AppWidgetProvider {

    private static final String TAG = "ScoresWidgetProvider";
    private static final String ACTION_OPEN_ACTIVITY = "com.barqsoft.footballscores.OPEN_ACTIVITY";
    private static final String ACTION_UPDATE_DATA = "com.barqsoft.footballscores.UPDATE_DATA";
    public static final String ITEM_POSITION = "item_position";



    public ScoresWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");

        String action = intent.getAction();
        if (ACTION_UPDATE_DATA.equals(action)){
            Log.d(TAG, "update button");
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            myFetchService.updateData(context);
        }
        else {
            super.onReceive(context, intent);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(ScoresWidgetService.TAG, "onUpdate");

        for(int i = 0; i < appWidgetIds.length; i++){
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.scores_widget);
            rv.setEmptyView(R.id.scores_list_view, R.id.empty_view);

            Intent butIntent = new Intent(context, ScoresWidgetProvider.class);
            butIntent.setAction(ACTION_UPDATE_DATA);
            butIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, butIntent, 0);
            rv.setOnClickPendingIntent(R.id.update_widget_button, pendingIntent);

            Intent intent = new Intent(context, ScoresWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            rv.setRemoteAdapter(appWidgetIds[i], R.id.scores_list_view, intent);

            Intent listClickIntent = new Intent(context, MainActivity.class);
            listClickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            PendingIntent listClickPIntent = PendingIntent.getActivity(context, 0, listClickIntent, 0);
            rv.setPendingIntentTemplate(R.id.scores_list_view, listClickPIntent);
            rv.setOnClickPendingIntent(R.id.empty_view, listClickPIntent);

            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }

        myFetchService.updateData(context);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


}
