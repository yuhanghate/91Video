package com.yh.video.pirate.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.gyf.immersionbar.OSUtils;
import com.yh.video.pirate.R;


/**
 * The type Bar config.
 *
 * @author geyifeng
 * @date 2017 /5/11
 */
public class BarConfig {

    /**
     * android 4.4或者emui3状态栏ID识位
     */
    static final int IMMERSION_ID_STATUS_BAR_VIEW = R.id.immersion_status_bar_view;
    /**
     * android 4.4或者emui3导航栏ID识位
     */
    static final int IMMERSION_ID_NAVIGATION_BAR_VIEW = R.id.immersion_navigation_bar_view;
    /**
     * 状态栏高度标识位
     */
    static final String IMMERSION_STATUS_BAR_HEIGHT = "status_bar_height";
    /**
     * 导航栏竖屏高度标识位
     */
    static final String IMMERSION_NAVIGATION_BAR_HEIGHT = "navigation_bar_height";
    /**
     * 导航栏横屏高度标识位
     */
    static final String IMMERSION_NAVIGATION_BAR_HEIGHT_LANDSCAPE = "navigation_bar_height_landscape";
    /**
     * 导航栏宽度标识位位
     */
    static final String IMMERSION_NAVIGATION_BAR_WIDTH = "navigation_bar_width";
    /**
     * MIUI导航栏显示隐藏标识位
     */
    static final String IMMERSION_MIUI_NAVIGATION_BAR_HIDE_SHOW = "force_fsg_nav_bar";
    /**
     * EMUI导航栏显示隐藏标识位
     */
    static final String IMMERSION_EMUI_NAVIGATION_BAR_HIDE_SHOW = "navigationbar_is_min";
    /**
     * MIUI状态栏字体黑色与白色标识位
     */
    static final String IMMERSION_MIUI_STATUS_BAR_DARK = "EXTRA_FLAG_STATUS_BAR_DARK_MODE";
    /**
     * MIUI导航栏图标黑色与白色标识位
     */
    static final String IMMERSION_MIUI_NAVIGATION_BAR_DARK = "EXTRA_FLAG_NAVIGATION_BAR_DARK_MODE";

    /**
     * 自动改变字体颜色的临界值标识位
     */
    static final int IMMERSION_BOUNDARY_COLOR = 0xFFBABABA;

    /**
     * 修复状态栏与布局重叠标识位，默认不修复
     */
    static final int FLAG_FITS_DEFAULT = 0X00;
    /**
     * 修复状态栏与布局重叠标识位，使用titleBar方法修复
     */
    static final int FLAG_FITS_TITLE = 0X01;
    /**
     * 修复状态栏与布局重叠标识位，使用titleBarMarginTop方法修复
     */
    static final int FLAG_FITS_TITLE_MARGIN_TOP = 0X02;
    /**
     * 修复状态栏与布局重叠标识位，使用StatusBarView方法修复
     */
    static final int FLAG_FITS_STATUS = 0X03;
    /**
     * 修复状态栏与布局重叠标识位，使用fitsSystemWindows方法修复
     */
    static final int FLAG_FITS_SYSTEM_WINDOWS = 0X04;

    private final int mStatusBarHeight;
    private final int mActionBarHeight;
    private final boolean mHasNavigationBar;
    private final int mNavigationBarHeight;
    private final int mNavigationBarWidth;
    private final boolean mInPortrait;
    private final float mSmallestWidthDp;

    /**
     * Instantiates a new Bar config.
     *
     * @param activity the activity
     */
    public BarConfig(Activity activity) {
        Resources res = activity.getResources();
        mInPortrait = (res.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
        mSmallestWidthDp = getSmallestWidthDp(activity);
        mStatusBarHeight = getInternalDimensionSize(activity, IMMERSION_STATUS_BAR_HEIGHT);
        mActionBarHeight = getActionBarHeight(activity);
        mNavigationBarHeight = getNavigationBarHeight(activity);
        mNavigationBarWidth = getNavigationBarWidth(activity);
        mHasNavigationBar = (mNavigationBarHeight > 0);
    }

    @TargetApi(14)
    private int getActionBarHeight(Activity activity) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            View actionBar = activity.getWindow().findViewById(R.id.action_bar_container);
            if (actionBar != null) {
                result = actionBar.getMeasuredHeight();
            }
            if (result == 0) {
                TypedValue tv = new TypedValue();
                activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
                result = TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
            }
        }
        return result;
    }

    @TargetApi(14)
    private int getNavigationBarHeight(Context context) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (hasNavBar((Activity) context)) {
                String key;
                if (mInPortrait) {
                    key = IMMERSION_NAVIGATION_BAR_HEIGHT;
                } else {
                    key = IMMERSION_NAVIGATION_BAR_HEIGHT_LANDSCAPE;
                }
                return getInternalDimensionSize(context, key);
            }
        }
        return result;
    }

    @TargetApi(14)
    private int getNavigationBarWidth(Context context) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (hasNavBar((Activity) context)) {
                return getInternalDimensionSize(context, IMMERSION_NAVIGATION_BAR_WIDTH);
            }
        }
        return result;
    }

    @TargetApi(14)
    private boolean hasNavBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //判断小米手机是否开启了全面屏，开启了，直接返回false
            if (Settings.Global.getInt(activity.getContentResolver(), IMMERSION_MIUI_NAVIGATION_BAR_HIDE_SHOW, 0) != 0) {
                return false;
            }
            //判断华为手机是否隐藏了导航栏，隐藏了，直接返回false
            if (OSUtils.isEMUI()) {
                if (OSUtils.isEMUI3_x() || Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    if (Settings.System.getInt(activity.getContentResolver(), IMMERSION_EMUI_NAVIGATION_BAR_HIDE_SHOW, 0) != 0) {
                        return false;
                    }
                } else {
                    if (Settings.Global.getInt(activity.getContentResolver(), IMMERSION_EMUI_NAVIGATION_BAR_HIDE_SHOW, 0) != 0) {
                        return false;
                    }
                }
            }
        }
        //其他手机根据屏幕真实高度与显示高度是否相同来判断
        WindowManager windowManager = activity.getWindowManager();
        Display d = windowManager.getDefaultDisplay();

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            d.getRealMetrics(realDisplayMetrics);
        }

        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);

        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;

        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }

    private int getInternalDimensionSize(Context context, String key) {
        int result = 0;
        try {
            int resourceId = Resources.getSystem().getIdentifier(key, "dimen", "android");
            if (resourceId > 0) {
                int sizeOne = context.getResources().getDimensionPixelSize(resourceId);
                int sizeTwo = Resources.getSystem().getDimensionPixelSize(resourceId);

                if (sizeTwo >= sizeOne) {
                    return sizeTwo;
                } else {
                    float densityOne = context.getResources().getDisplayMetrics().density;
                    float densityTwo = Resources.getSystem().getDisplayMetrics().density;
                    float f = sizeOne * densityTwo / densityOne;
                    return (int) ((f >= 0) ? (f + 0.5f) : (f - 0.5f));
                }
            }
        } catch (Resources.NotFoundException ignored) {
            return 0;
        }
        return result;
    }

    @SuppressLint("NewApi")
    private float getSmallestWidthDp(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        } else {
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        }
        float widthDp = metrics.widthPixels / metrics.density;
        float heightDp = metrics.heightPixels / metrics.density;
        return Math.min(widthDp, heightDp);
    }

    /**
     * Should a navigation bar appear at the bottom of the screen in the current
     * device configuration? A navigation bar may appear on the right side of
     * the screen in certain configurations.
     *
     * @return True if navigation should appear at the bottom of the screen, False otherwise.
     */
    boolean isNavigationAtBottom() {
        return (mSmallestWidthDp >= 600 || mInPortrait);
    }

    /**
     * Get the height of the system status bar.
     *
     * @return The height of the status bar (in pixels).
     */
    public int getStatusBarHeight() {
        return mStatusBarHeight;
    }

    /**
     * Get the height of the action bar.
     *
     * @return The height of the action bar (in pixels).
     */
    int getActionBarHeight() {
        return mActionBarHeight;
    }

    /**
     * Does this device have a system navigation bar?
     *
     * @return True if this device uses soft key navigation, False otherwise.
     */
    boolean hasNavigationBar() {
        return mHasNavigationBar;
    }

    /**
     * Get the height of the system navigation bar.
     *
     * @return The height of the navigation bar (in pixels). If the device does not have soft navigation keys, this will always return 0.
     */
    int getNavigationBarHeight() {
        return mNavigationBarHeight;
    }

    /**
     * Get the width of the system navigation bar when it is placed vertically on the screen.
     *
     * @return The width of the navigation bar (in pixels). If the device does not have soft navigation keys, this will always return 0.
     */
    int getNavigationBarWidth() {
        return mNavigationBarWidth;
    }
}