package dapsr.treesofdelhi.Sub_Leaves;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.github.ksoichiro.android.observablescrollview.Scrollable;
import com.github.ksoichiro.android.observablescrollview.TouchInterceptionFrameLayout;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import dapsr.treesofdelhi.Adapter.RecyclerViewAdapter;
import dapsr.treesofdelhi.R;
import dapsr.treesofdelhi.ScrollView.BaseActivity;

/**
 * Created by rajanmaurya on 28/4/15.
 */
public abstract class Jamun_like_leaves<S extends Scrollable> extends BaseActivity implements ObservableScrollViewCallbacks {


        private Toolbar toolbar;
        private TextView tooltitle;
        private RecyclerView mRecyclerView;
        private RecyclerView.LayoutManager mLayoutManager;
        protected RecyclerViewAdapter mAdapter;
        protected String[] mDataset;
        private static final int SPAN_COUNT = 2;

        /*
        *
        * Types of leaves
        *
        * */
        private int icon[] = {R.drawable.tree6, R.drawable.tree6, R.drawable.tree6, R.drawable.tree6,   R.drawable.tree6,R.drawable.tree6, R.drawable.tree6, R.drawable.tree6, R.drawable.tree6, R.drawable.tree6};
        Context context;


        private enum LayoutManagerType {
            GRID_LAYOUT_MANAGER,
            LINEAR_LAYOUT_MANAGER
        }


        protected LayoutManagerType mCurrentLayoutManagerType;


        private static final String STATE_SLIDING_STATE = "slidingState";
        private static final int SLIDING_STATE_TOP = 0;
        private static final int SLIDING_STATE_MIDDLE = 1;
        private static final int SLIDING_STATE_BOTTOM = 2;

        private View mHeader;
        private View mHeaderBar;
        private View mHeaderOverlay;
        private View mHeaderFlexibleSpace;
        private TextView mTitle;
        private TextView mToolbarTitle;
        private View mImageView;
        private View mFab;
        private Toolbar mToolbar;
        private S mScrollable;
        private TouchInterceptionFrameLayout mInterceptionLayout;

        // Fields that just keep constants like resource values
        private int mActionBarSize;
        private int mIntersectionHeight;
        private int mHeaderBarHeight;
        private int mSlidingSlop;
        private int mSlidingHeaderBlueSize;
        private int mColorPrimary;
        private int mFlexibleSpaceImageHeight;
        private int mToolbarColor;
        private int mFabMargin;

        // Fields that needs to saved
        private int mSlidingState;

        // Temporary states
        private boolean mFabIsShown;
        private boolean mMoved;
        private float mInitialY;
        private float mMovedDistanceY;
        private float mScrollYOnDownMotion;

        // These flags are used for changing header colors.
        private boolean mHeaderColorIsChanging;
        private boolean mHeaderColorChangedToBottom;
        private boolean mHeaderIsAtBottom;
        private boolean mHeaderIsNotAtBottom;
        private View.OnClickListener fabClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Jamun_like_leaves.this, "Jamun Activity", Toast.LENGTH_SHORT).show();
            }
        };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(getLayoutResId());


            initDataset();

        /*
        *
        * subgriditem Toolbar implementation
        * */
            toolbar = (Toolbar) findViewById(R.id.uppertool);
            tooltitle = (TextView) findViewById(R.id.too);
            tooltitle.setText("Tree Of Delhi");
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
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
            // mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListner(this, this));


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
            mLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
            mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
            mRecyclerView.setLayoutManager(mLayoutManager);

            int scrollPosition = 0;
            mRecyclerView.scrollToPosition(scrollPosition);

            // specify an adapter (see also next example)
            mAdapter = new RecyclerViewAdapter(mDataset, icon, context);
            // Set CustomAdapter as the adapter for RecyclerView.
            mRecyclerView.setAdapter(mAdapter);


            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            // mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            setSupportActionBar(mToolbar);
            ViewHelper.setScaleY(mToolbar, 0);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");

            mToolbarColor = getResources().getColor(R.color.primary);
            mToolbar.setBackgroundColor(Color.TRANSPARENT);
            mToolbar.setTitle("");

            mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
            mIntersectionHeight = getResources().getDimensionPixelSize(R.dimen.intersection_height);
            mHeaderBarHeight = getResources().getDimensionPixelSize(R.dimen.header_bar_height);
            mSlidingSlop = getResources().getDimensionPixelSize(R.dimen.sliding_slop);
            mActionBarSize = getActionBarSize();
            mColorPrimary = getResources().getColor(R.color.primary);
            mSlidingHeaderBlueSize = getResources().getDimensionPixelSize(R.dimen.sliding_overlay_blur_size);

            mHeader = findViewById(R.id.header);
            mHeaderBar = findViewById(R.id.header_bar);
            mHeaderOverlay = findViewById(R.id.header_overlay);
            mHeaderFlexibleSpace = findViewById(R.id.header_flexible_space);
            mImageView = findViewById(R.id.image);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    slideOnClick();
                }
            });
            mScrollable = createScrollable();

            mFab = findViewById(R.id.fab);
            mFab.setOnClickListener(fabClickListener);
            mFabMargin = getResources().getDimensionPixelSize(R.dimen.margin_standard);

            mInterceptionLayout = (TouchInterceptionFrameLayout) findViewById(R.id.scroll_wrapper);
            mInterceptionLayout.setScrollInterceptionListener(mInterceptionListener);
            mTitle = (TextView) findViewById(R.id.title);

        /*
        *
        * set lower Toolbar Title
        * */
            mTitle.setText("Onclick item grid title");
            mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
            mToolbarTitle.setText(mTitle.getText());
            ViewHelper.setAlpha(mToolbarTitle, 0);
            ViewHelper.setTranslationY(mTitle, (mHeaderBarHeight - mActionBarSize) / 2);

            if (savedInstanceState == null) {
                mSlidingState = SLIDING_STATE_BOTTOM;
            }

            ScrollUtils.addOnGlobalLayoutListener(mInterceptionLayout, new Runnable() {
                @Override
                public void run() {
                    if (mFab != null) {
                        ViewHelper.setTranslationX(mFab, mTitle.getWidth() - mFabMargin - mFab.getWidth());
                        ViewHelper.setTranslationY(mFab, ViewHelper.getX(mTitle) - (mFab.getHeight() / 2));
                    }
                    changeSlidingState(mSlidingState, false);
                }
            });
        }

        @Override
        protected void onRestoreInstanceState(Bundle savedInstanceState) {
            super.onRestoreInstanceState(savedInstanceState);
            // All the related temporary states can be restored by slidingState
            mSlidingState = savedInstanceState.getInt(STATE_SLIDING_STATE);
        }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
            outState.putInt(STATE_SLIDING_STATE, mSlidingState);
            super.onSaveInstanceState(outState);
        }

        protected abstract int getLayoutResId();

        protected abstract S createScrollable();

        @Override
        public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        }

        @Override
        public void onDownMotionEvent() {
        }

        @Override
        public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        }

        private TouchInterceptionFrameLayout.TouchInterceptionListener mInterceptionListener = new TouchInterceptionFrameLayout.TouchInterceptionListener() {
            @Override
            public boolean shouldInterceptTouchEvent(MotionEvent ev, boolean moving, float diffX, float diffY) {
                final int minInterceptionLayoutY = -mIntersectionHeight;

                // slight fix for untappable floating action button for larger screens
                Rect fabRect = new Rect();
                mFab.getHitRect(fabRect);
                // if the user's touch is within the floating action button's touch area, don't intercept
                if (fabRect.contains((int) ev.getX(), (int) ev.getY())) {
                    return false;
                } else {
                    return minInterceptionLayoutY < (int) ViewHelper.getY(mInterceptionLayout)
                            || (moving && mScrollable.getCurrentScrollY() - diffY < 0);
                }
            }

            @Override
            public void onDownMotionEvent(MotionEvent ev) {
                mScrollYOnDownMotion = mScrollable.getCurrentScrollY();
                mInitialY = ViewHelper.getTranslationY(mInterceptionLayout);
            }

            @Override
            public void onMoveMotionEvent(MotionEvent ev, float diffX, float diffY) {
                mMoved = true;
                float translationY = ViewHelper.getTranslationY(mInterceptionLayout) - mScrollYOnDownMotion + diffY;
                if (translationY < -mIntersectionHeight) {
                    translationY = -mIntersectionHeight;
                } else if (getScreenHeight() - mHeaderBarHeight < translationY) {
                    translationY = getScreenHeight() - mHeaderBarHeight;
                }

                slideTo(translationY, true);

                mMovedDistanceY = ViewHelper.getTranslationY(mInterceptionLayout) - mInitialY;
            }

            @Override
            public void onUpOrCancelMotionEvent(MotionEvent ev) {
                if (!mMoved) {
                    // Invoke slide animation only on header view
                    Rect outRect = new Rect();
                    mHeader.getHitRect(outRect);
                    if (outRect.contains((int) ev.getX(), (int) ev.getY())) {
                        slideOnClick();
                    }
                } else {
                    stickToAnchors();
                }
                mMoved = false;
            }
        };

        private void changeSlidingState(final int slidingState, boolean animated) {
            mSlidingState = slidingState;
            float translationY = 0;
            switch (slidingState) {
                case SLIDING_STATE_TOP:
                    translationY = 0;
                    break;
                case SLIDING_STATE_MIDDLE:
                    translationY = getAnchorYImage();
                    break;
                case SLIDING_STATE_BOTTOM:
                    translationY = getAnchorYBottom();
                    break;
            }
            if (animated) {
                slideWithAnimation(translationY);
            } else {
                slideTo(translationY, false);
            }
        }

        private void slideOnClick() {
            float translationY = ViewHelper.getTranslationY(mInterceptionLayout);
            if (translationY == getAnchorYBottom()) {
                changeSlidingState(SLIDING_STATE_MIDDLE, true);
            } else if (translationY == getAnchorYImage()) {
                changeSlidingState(SLIDING_STATE_BOTTOM, true);
            }
        }

        private void stickToAnchors() {
            // Slide to some points automatically
            if (0 < mMovedDistanceY) {
                // Sliding down
                if (mSlidingSlop < mMovedDistanceY) {
                    // Sliding down to an anchor
                    if (getAnchorYImage() < ViewHelper.getTranslationY(mInterceptionLayout)) {
                        changeSlidingState(SLIDING_STATE_BOTTOM, true);
                    } else {
                        changeSlidingState(SLIDING_STATE_MIDDLE, true);
                    }
                } else {
                    // Sliding up(recyccleview_griditem) to an anchor
                    if (getAnchorYImage() < ViewHelper.getTranslationY(mInterceptionLayout)) {
                        changeSlidingState(SLIDING_STATE_MIDDLE, true);
                    } else {
                        changeSlidingState(SLIDING_STATE_TOP, true);
                    }
                }
            } else if (mMovedDistanceY < 0) {
                // Sliding up
                if (mMovedDistanceY < -mSlidingSlop) {
                    // Sliding up to an anchor
                    if (getAnchorYImage() < ViewHelper.getTranslationY(mInterceptionLayout)) {
                        changeSlidingState(SLIDING_STATE_MIDDLE, true);
                    } else {
                        changeSlidingState(SLIDING_STATE_TOP, true);
                    }
                } else {
                    // Sliding down(recyccleview_griditem) to an anchor
                    if (getAnchorYImage() < ViewHelper.getTranslationY(mInterceptionLayout)) {
                        changeSlidingState(SLIDING_STATE_BOTTOM, true);
                    } else {
                        changeSlidingState(SLIDING_STATE_MIDDLE, true);
                    }
                }
            }
        }

        private void slideTo(float translationY, final boolean animated) {
            ViewHelper.setTranslationY(mInterceptionLayout, translationY);

            if (translationY < 0) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mInterceptionLayout.getLayoutParams();
                lp.height = (int) -translationY + getScreenHeight();
                mInterceptionLayout.requestLayout();
            }

            // Translate title
            float hiddenHeight = translationY < 0 ? -translationY : 0;
            ViewHelper.setTranslationY(mTitle, Math.min(mIntersectionHeight, (mHeaderBarHeight + hiddenHeight - mActionBarSize) / 2));

            // Translate image
            float imageAnimatableHeight = getScreenHeight() - mHeaderBarHeight;
            float imageTranslationScale = imageAnimatableHeight / (imageAnimatableHeight - mImageView.getHeight());
            float imageTranslationY = Math.max(0, imageAnimatableHeight - (imageAnimatableHeight - translationY) * imageTranslationScale);
            ViewHelper.setTranslationY(mImageView, imageTranslationY);

            // Show/hide FAB
            if (ViewHelper.getTranslationY(mInterceptionLayout) < mFlexibleSpaceImageHeight) {
                hideFab(animated);
            } else {
                if (animated) {
                    ViewPropertyAnimator.animate(mToolbar).scaleY(0).setDuration(200).start();
                } else {
                    ViewHelper.setScaleY(mToolbar, 0);
                }
                showFab(animated);
            }
            if (ViewHelper.getTranslationY(mInterceptionLayout) <= mFlexibleSpaceImageHeight) {
                if (animated) {
                    ViewPropertyAnimator.animate(mToolbar).scaleY(1).setDuration(200).start();
                } else {
                    ViewHelper.setScaleY(mToolbar, 1);
                }
                mToolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, mToolbarColor));
            }

            changeToolbarTitleVisibility();
            changeHeaderBarColorAnimated(animated);
            changeHeaderOverlay();
        }

        private void slideWithAnimation(float toY) {
            float layoutTranslationY = ViewHelper.getTranslationY(mInterceptionLayout);
            if (layoutTranslationY != toY) {
                ValueAnimator animator = ValueAnimator.ofFloat(ViewHelper.getTranslationY(mInterceptionLayout), toY).setDuration(200);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        slideTo((float) animation.getAnimatedValue(), true);
                    }
                });
                animator.start();
            }
        }

        private void changeToolbarTitleVisibility() {
            if (ViewHelper.getTranslationY(mInterceptionLayout) <= mIntersectionHeight) {
                if (ViewHelper.getAlpha(mToolbarTitle) != 1) {
                    ViewPropertyAnimator.animate(mToolbarTitle).cancel();
                    ViewPropertyAnimator.animate(mToolbarTitle).alpha(1).setDuration(200).start();
                }
            } else if (ViewHelper.getAlpha(mToolbarTitle) != 0) {
                ViewPropertyAnimator.animate(mToolbarTitle).cancel();
                ViewPropertyAnimator.animate(mToolbarTitle).alpha(0).setDuration(200).start();
            } else {
                ViewHelper.setAlpha(mToolbarTitle, 0);
            }
        }

        private void changeHeaderBarColorAnimated(boolean animated) {
            if (mHeaderColorIsChanging) {
                return;
            }
            boolean shouldBeWhite = getAnchorYBottom() == ViewHelper.getTranslationY(mInterceptionLayout);
            if (!mHeaderIsAtBottom && !mHeaderColorChangedToBottom && shouldBeWhite) {
                mHeaderIsAtBottom = true;
                mHeaderIsNotAtBottom = false;
                if (animated) {
                    ValueAnimator animator = ValueAnimator.ofFloat(0, 1).setDuration(100);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float alpha = (float) animation.getAnimatedValue();
                            mHeaderColorIsChanging = (alpha != 1);
                            changeHeaderBarColor(alpha);
                        }
                    });
                    animator.start();
                } else {
                    changeHeaderBarColor(1);
                }
            } else if (!mHeaderIsNotAtBottom && !shouldBeWhite) {
                mHeaderIsAtBottom = false;
                mHeaderIsNotAtBottom = true;
                if (animated) {
                    ValueAnimator animator = ValueAnimator.ofFloat(1, 0).setDuration(100);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float alpha = (float) animation.getAnimatedValue();
                            mHeaderColorIsChanging = (alpha != 0);
                            changeHeaderBarColor(alpha);
                        }
                    });
                    animator.start();
                } else {
                    changeHeaderBarColor(0);
                }
            }
        }

        /*
        *
        * Changing Toolbar color while scrolling or At bottom or At Top of Screen
        *
        * */
        private void changeHeaderBarColor(float alpha) {
            mHeaderBar.setBackgroundColor(ScrollUtils.mixColors(mColorPrimary, mColorPrimary, alpha));
            mTitle.setTextColor(ScrollUtils.mixColors(Color.WHITE, Color.WHITE, alpha));
            mHeaderColorChangedToBottom = (alpha == 1);
        }

        private void changeHeaderOverlay() {
            final float translationY = ViewHelper.getTranslationY(mInterceptionLayout);
            if (translationY <= mToolbar.getHeight() - mSlidingHeaderBlueSize) {
                mHeaderOverlay.setVisibility(View.VISIBLE);
                mHeaderFlexibleSpace.getLayoutParams().height = (int) (mToolbar.getHeight() - mSlidingHeaderBlueSize - translationY);
                mHeaderFlexibleSpace.requestLayout();
                mHeaderOverlay.requestLayout();
            } else {
                mHeaderOverlay.setVisibility(View.INVISIBLE);
            }
        }

        private void showFab(boolean animated) {
            if (mFab == null) {
                return;
            }
            if (!mFabIsShown) {
                if (animated) {
                    ViewPropertyAnimator.animate(mFab).cancel();
                    ViewPropertyAnimator.animate(mFab).scaleX(1).scaleY(1).setDuration(200).start();
                } else {
                    ViewHelper.setScaleX(mFab, 1);
                    ViewHelper.setScaleY(mFab, 1);
                }
                mFabIsShown = true;
            } else {
                // Ensure that FAB is shown
                ViewHelper.setScaleX(mFab, 1);
                ViewHelper.setScaleY(mFab, 1);
            }
        }

        private void hideFab(boolean animated) {
            if (mFab == null) {
                return;
            }
            if (mFabIsShown) {
                if (animated) {
                    ViewPropertyAnimator.animate(mFab).cancel();
                    ViewPropertyAnimator.animate(mFab).scaleX(0).scaleY(0).setDuration(200).start();
                } else {
                    ViewHelper.setScaleX(mFab, 0);
                    ViewHelper.setScaleY(mFab, 0);
                }
                mFabIsShown = false;
            } else {
                // Ensure that FAB is hidden
                ViewHelper.setScaleX(mFab, 0);
                ViewHelper.setScaleY(mFab, 0);
            }
        }

        private float getAnchorYBottom() {
            return getScreenHeight() - mHeaderBarHeight;
        }

        private float getAnchorYImage() {
            return mImageView.getHeight();
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
                if (parent.getChildPosition(view) == 0) {
                    outRect.top = space;
                    outRect.right = 0;
                }

                if (parent.getChildPosition(view) == 1) {
                    outRect.top = space;
                }

                if (parent.getChildPosition(view) == 2) {
                    outRect.right = 0;
                }
                if (parent.getChildPosition(view) == 4) {
                    outRect.right = 0;
                }
                if (parent.getChildPosition(view) == 6) {
                    outRect.right = 0;
                }
                if (parent.getChildPosition(view) == 8) {
                    outRect.right = 0;
                }


            }
        }


    }
