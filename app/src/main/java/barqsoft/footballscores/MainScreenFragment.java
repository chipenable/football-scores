package barqsoft.footballscores;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import barqsoft.footballscores.database.DatabaseContract;
import barqsoft.footballscores.service.myFetchService;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainScreenFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private final static String TAG = "MainScreenFragment";
    public scoresAdapter mAdapter;
    public static final int SCORES_LOADER = 0;
    private String[] fragmentdate = new String[1];
    private int last_selected_item = -1;

    public MainScreenFragment() {
    }

    private void update_scores() {
        Intent service_start = new Intent(getActivity(), myFetchService.class);
        getActivity().startService(service_start);
    }

    public void setFragmentDate(String date) {
        Log.d(TAG, date);
        fragmentdate[0] = date;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        update_scores();
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        LinearLayout emptyView = (LinearLayout)rootView.findViewById(R.id.empty_view);
        final ListView scoreList = (ListView) rootView.findViewById(R.id.scores_list);
        mAdapter = new scoresAdapter(getActivity(), null, 0);
        scoreList.setAdapter(mAdapter);
        scoreList.setEmptyView(emptyView);
        getLoaderManager().initLoader(SCORES_LOADER, null, this);
        mAdapter.detail_match_id = MainActivity.sSelectedMatchId;

        scoreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "on click");
                ViewHolder selected = (ViewHolder) view.getTag();
                mAdapter.detail_match_id = selected.match_id;
                MainActivity.sSelectedMatchId = (int) selected.match_id;
                mAdapter.notifyDataSetChanged();
            }
        });
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(), DatabaseContract.scores_table.buildScoreWithDate(),
                null, null, fragmentdate, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

        int i = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            i++;
            cursor.moveToNext();
        }

        mAdapter.swapCursor(cursor);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }


}
