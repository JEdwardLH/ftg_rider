package com.foodtogo.rider.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.ViewGroup;

import com.foodtogo.rider.R;
import com.foodtogo.rider.ui.deliveredorder.fragment.DeliveredOrderFragment;
import com.foodtogo.rider.ui.neworderfragment.fragment.NewOrderFragment;
import com.foodtogo.rider.ui.processingorder.fragment.ProcessingOrderFragment;
import com.foodtogo.rider.ui.profile.fragment.EditProfileFragment;

import java.util.List;

/**
 * The type Fragment activity.
 */
public class FragmentActivity extends BaseActivity {

    private static String ARG_OPEN = "arg_open";

    /**
     * The constant OPEN_PROFILE.
     */
    public static final int OPEN_PROFILE = 0;
    /**
     * The constant ORDERS_NEW.
     */
    public static final int ORDERS_NEW = 1;
    /**
     * The constant ORDERS_PROCESSING.
     */
    public static final int ORDERS_PROCESSING = 2;
    /**
     * The constant ORDERS_DELIVERED.
     */
    public static final int ORDERS_DELIVERED = 3;

    /**
     * Create intent intent.
     *
     * @param context the context
     * @param open    the open
     * @return the intent
     */
    public static Intent createIntent(Context context, int open) {
        Intent intent = new Intent(context, FragmentActivity.class);
        intent.putExtra(ARG_OPEN, open);
        return intent;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_fragment;
    }

    /**
     * The Page.
     */
    int page;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolBar();

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
        layoutParams.setMargins(0, getStatusBarHeight(), 0, 0);
        toolbar.setLayoutParams(layoutParams);

        page = getIntent().getIntExtra(ARG_OPEN, OPEN_PROFILE);
        Log.d("CPage", page + "");

        toolbar.post(() -> {
            switch (page) {
                case OPEN_PROFILE:
                    replaceFragment(R.id.flContainer, new EditProfileFragment(), getString(R.string.profile_edit));
                    break;
                case ORDERS_NEW:
                    replaceFragment(R.id.flContainer, new NewOrderFragment(), getString(R.string.new_order));
                    break;
                case ORDERS_PROCESSING:
                    replaceFragment(R.id.flContainer, new ProcessingOrderFragment(), getString(R.string.processing_order));
                    break;
                case ORDERS_DELIVERED:
                    replaceFragment(R.id.flContainer, new DeliveredOrderFragment(), getString(R.string.delivered_order));
                    break;
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragments) {
                fragment.onActivityResult(requestCode, resultCode, data);
                setResult(RESULT_OK);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
