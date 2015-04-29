package dapsr.treesofdelhi.ScrollView;

import android.view.View;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import dapsr.treesofdelhi.R;

/**
 * Created by rajanmaurya on 4/4/15.
 */
public class SlidingUpScrollViewActivity extends SlidingUpBaseActivity<ObservableScrollView> implements ObservableScrollViewCallbacks {




    @Override
    protected int getLayoutResId() {
        return R.layout.activity_slidingupscrollview;
    }

    @Override
    protected ObservableScrollView createScrollable() {
        ObservableScrollView scrollView = (ObservableScrollView) findViewById(R.id.scroll);
        scrollView.setScrollViewCallbacks(this);
        return scrollView;
    }

    @Override
    public void onScrollChanged(int i, boolean b, boolean b2) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    @Override
    public void onItemClick(View childView, int position) {


       // Toast.makeText(this, "Sub Leaf is clicked", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }
}