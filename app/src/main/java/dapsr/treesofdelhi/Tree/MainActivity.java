package dapsr.treesofdelhi.Tree;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import dapsr.treesofdelhi.Adapter.RecyclerItemClickListner;
import dapsr.treesofdelhi.Adapter.RecyclerViewAdapter;
import dapsr.treesofdelhi.R;
import dapsr.treesofdelhi.ScrollView.SlidingUpScrollViewActivity;


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


    /*
    *
    * Basic TenType Leaves
    *
    * */
    private int icon [] = {R.drawable.tree1,R.drawable.tree2,R.drawable.tree3,R.drawable.tree4,R.drawable.tree5,R.drawable.tree6,R.drawable.tree7,R.drawable.tree8,R.drawable.tree9,R.drawable.tree10 };

    @Override
    public void onItemClick(View childView, int position) {

        switch (position) {
            case 0:


                break;
            case 1:


                break;

            case 2:

                break;

            case 3:

                break;

            case 4:

                break;

            case 5:

                break;

            case 6:

                break;

            case 7:

                break;

            case 8:

                break;

            case 9:

                break;

            case 10:

                break;


        }
        Intent intent = new Intent(this,SlidingUpScrollViewActivity.class);
        startActivity(intent);

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

        mRecyclerView.addItemDecoration(new SpacesItemDecoration(5));

        /*
        *
        * RecyclerView Item Click Event
        *
        * */
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListner(this, this));


        mRecyclerView.setHasFixedSize(true);

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

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            //outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if(parent.getChildPosition(view) == 0){
                outRect.top = space;
                outRect.right = 0;
            }

            if(parent.getChildPosition(view) == 1){
                outRect.top = space;
            }

            if(parent.getChildPosition(view) == 2){
                outRect.right = 0;
            }
            if(parent.getChildPosition(view) == 4){
                outRect.right = 0;
            }
            if(parent.getChildPosition(view) == 6){
                outRect.right = 0;
            }
            if(parent.getChildPosition(view) == 8){
                outRect.right = 0;
            }



        }
    }


}
