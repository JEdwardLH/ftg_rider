package com.foodtogo.rider.customview;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.material.appbar.AppBarLayout;
import android.util.AttributeSet;

/**
 * The type Fixed app bar layout behavior.
 */
public class FixedAppBarLayoutBehavior extends AppBarLayout.Behavior {

    /**
     * Instantiates a new Fixed app bar layout behavior.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public FixedAppBarLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

        setDragCallback(new DragCallback() {
            @Override
            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                return false;
            }
        });
    }
}