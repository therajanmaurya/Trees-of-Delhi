package dapsr.treesofdelhi.Tree;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import dapsr.treesofdelhi.Adapter.RecyclerViewAdapter;
import dapsr.treesofdelhi.R;
import dapsr.treesofdelhi.Adapter.RecyclerItemClickListner;



/**
 * Created by rajanmaurya on 30/4/15.
 */
public class MainActivity extends ActionBarActivity implements RecyclerItemClickListner.OnItemClickListener {

    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager ;
    protected RecyclerViewAdapter mAdapter;
    protected String[] mDataset;
    private static final int SPAN_COUNT = 2;
    private int icon [] = {R.drawable.ic_abstract,R.drawable.ic_abstract,R.drawable.ic_abstract,R.drawable.ic_abstract,R.drawable.ic_abstract,R.drawable.ic_abstract,R.drawable.ic_abstract,R.drawable.ic_abstract,R.drawable.ic_abstract,R.drawable.ic_abstract };

    @Override
    public void onItemClick(View childView, int position) {


            Toast.makeText(this,"Item Number " + position,Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    Context context;

    protected LayoutManagerType mCurrentLayoutManagerType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDataset();

        /*
        *
        * Toolbar implementation
        * */
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        /*
        *
        * RecyclerView implementation
        *
        * */


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        /*
        *
        * RecyclerView Item Click Event
        *
        * */
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListner(this, this));


       // mRecyclerView.setHasFixedSize(true);

        /*
        *
        * for Linear layout
        *
        * mLayoutManager = new LinearLayoutManager(this);
        *
        * for Grid Layout
        *
        * mLayoutManager = new GridLayoutManager(this,SPAN_COUNT);
        * // SPAN_COUNT is the number of column in grid view
        *
        * */
       // mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager = new GridLayoutManager(this,SPAN_COUNT);
        mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
        mRecyclerView.setLayoutManager(mLayoutManager);

        int scrollPosition = 0;
        mRecyclerView.scrollToPosition(scrollPosition);

        // specify an adapter (see also next example)
        mAdapter = new RecyclerViewAdapter(mDataset , icon,context);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initDataset() {
        mDataset = new String[icon.length];

        for (int i = 0; i < icon.length; i++) {

            mDataset[i] = "Tree Number " + i;
        }
    }

}
