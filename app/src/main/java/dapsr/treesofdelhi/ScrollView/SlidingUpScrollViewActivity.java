package dapsr.treesofdelhi.ScrollView;

import android.view.View;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import dapsr.treesofdelhi.R;

/**
 * Created by rajanmaurya on 4/4/15.
 */
public class SlidingUpScrollViewActivity extends SlidingUpBaseActivity<ObservableScrollView> implements ObservableScrollViewCallbacks {

    private static final int SLIDING_STATE_MIDDLE = 1;
    private static final int SLIDING_STATE_BOTTOM = 2;
    ObservableScrollView scrollView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_slidingupscrollview;
    }

    @Override
    protected ObservableScrollView createScrollable() {
        scrollView = (ObservableScrollView) findViewById(R.id.scroll);
        scrollView.setScrollViewCallbacks(this);
        return scrollView;
    }

    @Override
    public void onBackPressed() {




        TextView container = (TextView)findViewById(R.id.container);

        if (SlidingUpBaseActivity.getActionbarStatus == 7){
            finish();
        }else if(SlidingUpBaseActivity.getActionbarStatus == 6 ){
              changeSlidingState(SLIDING_STATE_BOTTOM, true);
        }else if(SlidingUpBaseActivity.getActionbarStatus == 5){
             
            scrollView.fullScroll(View.FOCUS_UP);
            changeSlidingState(SLIDING_STATE_MIDDLE, true);
            container.invalidate();
            changeSlidingState(SLIDING_STATE_BOTTOM, true);

        }

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

}