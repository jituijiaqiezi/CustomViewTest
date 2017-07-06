package custom.calendar;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lcp.customviewtest.R;

import java.util.ArrayList;
import java.util.List;

import util.StringUtils;

/**
 * Created by linchenpeng on 2017/6/26.
 */

public class TimeSelectViewGroup extends RelativeLayout implements CircleView.LayoutChangeListener {
    private final static String TAG = TimeSelectViewGroup.class.getSimpleName();
    RectView rectView;
    TopCircleView topCircle;
    BottomCircleView bottomCircle;
    RecyclerView recyclerViewContent;
    List<Integer> contents;
    List<String> times;
    RecyclerView recyclerViewTime;
    int contentPadding;
    boolean isViewAdd = false;
    OnCustomTouchEvent onCustomTouchEvent;

    public TimeSelectViewGroup(Context context) {
        this(context, null);
    }

    public TimeSelectViewGroup(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeSelectViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setOnCustomTouchEvent(OnCustomTouchEvent onCustomTouchEvent) {
        this.onCustomTouchEvent = onCustomTouchEvent;
    }

    private void init(Context context) {
        View view = View.inflate(context, R.layout.view_group_time_select, this);
        contentPadding = getResources().getDimensionPixelSize(R.dimen.time_height_half);
        rectView = new RectView(getContext());
        topCircle = new TopCircleView(getContext());
        bottomCircle = new BottomCircleView(getContext());
        /*rectView = (RectView) view.findViewById(R.id.rectView);
        topCircle = (TopCircleView) view.findViewById(R.id.topCircle);
        bottomCircle = (BottomCircleView) view.findViewById(R.id.bottomCircle);*/

        topCircle.setLayoutChangeListener(this);
        bottomCircle.setLayoutChangeListener(this);

        contents = new ArrayList<>();
        for (int i = 0; i < 24 * 7; i++) {
            contents.add(i);
        }

        recyclerViewContent = (RecyclerView) view.findViewById(R.id.recyclerView_content);
        recyclerViewContent.setLayoutManager(new GridLayoutManager(getContext(), 7));
        CalendarContentAdapter contentAdapter = new CalendarContentAdapter(contents);
        contentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //Toast.makeText(getContext(), "left:" + view.getLeft() + ",firstTop:" + view.getTop(), Toast.LENGTH_SHORT).show();
                drawSelectArea(view);
            }
        });
        recyclerViewContent.setAdapter(contentAdapter);

        times = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            times.add(StringUtils.getTimeString(i) + "点");
        }

        recyclerViewTime = (RecyclerView) view.findViewById(R.id.recyclerView_time);
        recyclerViewTime.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTime.setAdapter(new CalendarTimeAdapter(getContext(), times));
        recyclerViewTime.addItemDecoration(new TimeItemDecoration(getContext()));
    }

    private void addView() {
        addView(rectView);
        addView(topCircle);
        addView(bottomCircle);
    }

    private void removeView() {

    }

    private void drawSelectArea(final View view) {
        if (isViewAdd) {
            removeView(rectView);
            removeView(topCircle);
            removeView(bottomCircle);
        } else {
            rectView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    rectView.getViewTreeObserver().removeOnPreDrawListener(this);
                    rectView.reLayout(view, recyclerViewTime.getWidth());
                    return false;
                }
            });
            topCircle.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    topCircle.getViewTreeObserver().removeOnPreDrawListener(this);
                    topCircle.reLayout(view, recyclerViewTime.getWidth());
                    return false;
                }
            });
            bottomCircle.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    bottomCircle.getViewTreeObserver().removeOnPreDrawListener(this);
                    bottomCircle.reLayout(view, recyclerViewTime.getWidth());
                    return false;
                }
            });
            addView(rectView);
            addView(topCircle);
            addView(bottomCircle);
        }
        isViewAdd = !isViewAdd;
    }

    private void setVisible() {
        rectView.setVisibility(isViewAdd ? VISIBLE : GONE);
        topCircle.setVisibility(isViewAdd ? VISIBLE : GONE);
        bottomCircle.setVisibility(isViewAdd ? VISIBLE : GONE);
    }

    @Override
    public Point reLayoutTop(int deltaX, int deltaY) {
        return rectView.reLayoutTop(deltaX, deltaY);
    }

    @Override
    public Point reLayoutBottom(int deltaX, int deltaY) {
        return rectView.reLayoutBottom(deltaX, deltaY);
    }

    @Override
    public void disallowInterceptTouchEvent(boolean disallow) {
        onCustomTouchEvent.disallowInterceptTouchEvent(disallow);
    }

    @Override
    public boolean onScroll(boolean up) {
        return onCustomTouchEvent.onScroll(up);
    }

    @Override
    public boolean minHeight() {
        return rectView.getHeight() <= rectView.getMinHeight();
    }

    interface OnCustomTouchEvent {
        void disallowInterceptTouchEvent(boolean disallow);

        boolean onScroll(boolean up);
    }
}
