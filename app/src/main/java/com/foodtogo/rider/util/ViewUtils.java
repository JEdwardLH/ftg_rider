package com.foodtogo.rider.util;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.provider.Settings;
import com.google.android.material.snackbar.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.EditText;

import com.foodtogo.rider.base.BaseApplication;

/**
 * The type View utils.
 */
public final class ViewUtils {

    private ViewUtils() {
        // This utility class is not publicly instantiable
    }


    /**
     * Gets device id.
     *
     * @return the device id
     */
    public static String getDeviceId() {
        return Settings.Secure.getString(BaseApplication.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);

    }

    /**
     * Px to dp float.
     *
     * @param px the px
     * @return the float
     */
    public static float pxToDp(float px) {
        float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        return px / (densityDpi / 160f);
    }

    /**
     * Dp to px int.
     *
     * @param dp the dp
     * @return the int
     */
    public static int dpToPx(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    /**
     * Gets edit text value.
     *
     * @param editText the edit text
     * @return the edit text value
     */
    public static String getEditTextValue(EditText editText) {
        String value = "";
        if (!TextUtils.isEmpty(editText.getText().toString())) {
            value = editText.getText().toString();
        }
        return value;
    }

    /**
     * Show snack bar.
     *
     * @param view the view
     * @param msg  the msg
     */
    public static void showSnackBar(View view, String msg) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    /**
     * Force ripple animation.
     *
     * @param views the views
     */
    public static void forceRippleAnimation(View... views) {
        for (View view : views) {

            Drawable background = view.getBackground();

            //  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && background instanceof RippleDrawable) {
            /*  final RippleDrawable rippleDrawable = (RippleDrawable) background;*/

            background.setState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled});

            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    background.setState(new int[]{});
                }
            }, 200);
            //  }

        }
    }

    /**
     * Expand boolean.
     *
     * @param v the v
     * @return the boolean
     */
// expand/collapse view - returns isExpanded
    public static boolean expand(final View v) {
        if (v.getVisibility() == View.GONE) {
            v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            //v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            int height = v.getTag() != null ? (int) v.getTag() : v.getMeasuredHeight();


            // Older versions of android (pre API 21) cancel animations for views with a height of 0.
            //  int height = v.getLayoutParams().height = 1;
/*            ViewGroup.LayoutParams layoutParams =  v.getLayoutParams();
            layoutParams.height = 0;
            v.setLayoutParams(layoutParams);

            v.requestLayout();
            v.setVisibility(View.VISIBLE);*/
            Animation a = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {

                    v.getLayoutParams().height = interpolatedTime == 1
                            ? ViewGroup.LayoutParams.WRAP_CONTENT
                            : (int) (height * interpolatedTime);
                    v.requestLayout();
                    if (v.getLayoutParams().height > 5)
                        v.setVisibility(View.VISIBLE);
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            //    a.setInterpolator(new AccelerateInterpolator(5));
            // 1dp/ms
            // a.setDuration((int) (height / v.getContext().getResources().getDisplayMetrics().density));
            //smoother
            a.setDuration(((int) (height / v.getContext().getResources().getDisplayMetrics().density)) * 2);
            v.startAnimation(a);


            return true;
        } else {
            final int initialHeight = v.getMeasuredHeight();
            v.setTag(initialHeight);
            Animation a = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    if (interpolatedTime == 1) {
                        v.setVisibility(View.GONE);
                    } else {
                        v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                        v.requestLayout();
                    }
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };
            //    a.setInterpolator(new AccelerateInterpolator(3));
            // 1dp/ms
            //a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));

            //smoother
            a.setDuration(((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density)) * 2);

            v.startAnimation(a);
            return false;
        }
    }
}
