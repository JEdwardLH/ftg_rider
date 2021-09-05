package com.foodtogo.rider.customview;

import android.content.Context;
import android.graphics.Point;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.TextView;

import com.foodtogo.rider.R;


/**
 * The type Centered title toolbar.
 */
public class CenteredTitleToolbar extends Toolbar {

    private TextView _titleTextView;
    private int _screenWidth;
    private boolean _centerTitle = false;


    /**
     * Instantiates a new Centered title toolbar.
     *
     * @param context the context
     */
    public CenteredTitleToolbar(Context context) {
        super(context);
        init();
    }

    /**
     * Instantiates a new Centered title toolbar.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public CenteredTitleToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Instantiates a new Centered title toolbar.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     */
    public CenteredTitleToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        _screenWidth = getScreenSize().x;
        _titleTextView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.title, this, false);
        addView(_titleTextView);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        _screenWidth = getWidth();
        if (_centerTitle) {
            int[] location = new int[2];
            _titleTextView.getLocationOnScreen(location);
            _titleTextView.setTranslationX((getScreenSize().x - _screenWidth) + _titleTextView.getTranslationX() + (-location[0] + _screenWidth / 2 - _titleTextView.getWidth() / 2));
        } else {
            _titleTextView.setTranslationX(0);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        _titleTextView.setText(title);
        requestLayout();
    }

    @Override
    public void setTitle(int titleRes) {
        _titleTextView.setText(getResources().getString(titleRes));
    }

    /**
     * Sets title centered.
     *
     * @param centered the centered
     */
    public void setTitleCentered(boolean centered) {
        _centerTitle = centered;
        requestLayout();
    }

    private Point getScreenSize() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);

        return screenSize;
    }
}