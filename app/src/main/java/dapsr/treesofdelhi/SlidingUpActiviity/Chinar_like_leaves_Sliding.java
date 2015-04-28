package dapsr.treesofdelhi.SlidingUpActiviity;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import dapsr.treesofdelhi.R;
import dapsr.treesofdelhi.Sub_Leaves.Chinar_like_leaves;

/**
 * Created by rajanmaurya on 28/4/15.
 */
public class Chinar_like_leaves_Sliding extends Chinar_like_leaves<ObservableScrollView> implements ObservableScrollViewCallbacks {




        @Override
        protected int getLayoutResId() {
            return R.layout.chinar_like_leaves;
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
    }