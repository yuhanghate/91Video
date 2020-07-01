/*
 * Copyright 2016 czy1121
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yh.video.pirate.widget;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;


import com.yh.video.pirate.R;

import java.util.HashMap;
import java.util.Map;


public class StateLayout extends FrameLayout {

    private boolean isShowError = false;
    private boolean isShowLoading = false;
    private boolean isShowContent = false;
    private boolean isShowEmpty = false;
    public interface OnInflateListener {
        void onInflate(View inflated);
    }

    public static StateLayout wrap(Activity activity) {
        return wrap(((ViewGroup)activity.findViewById(android.R.id.content)).getChildAt(0));
    }
    public static StateLayout wrap(Fragment fragment) {
        return wrap(fragment.getView());
    }
    public static StateLayout wrap(View view) {
        if (view == null) {
            throw new RuntimeException("content view can not be null");
        }
        ViewGroup parent = (ViewGroup)view.getParent();
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        int index = parent.indexOfChild(view);
        parent.removeView(view);

        StateLayout layout = new StateLayout(view.getContext());
        parent.addView(layout, index, lp);
        layout.addView(view);
        layout.setContentView(view);
        return layout;
    }


    CharSequence mErrorText, mRetryText;
    View.OnClickListener mRetryButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mRetryListener != null) {
                mRetryListener.onClick(v);
            }
        }
    };
    View.OnClickListener mRetryListener;

    OnInflateListener mOnEmptyInflateListener;
    OnInflateListener mOnErrorInflateListener;

    int mEmptyResId = NO_ID, mLoadingResId = NO_ID, mErrorResId = NO_ID;
    int mContentId = NO_ID;

    Map<Integer, View> mLayouts = new HashMap<>();


    public StateLayout(Context context) {
        this(context, null, R.attr.styleLoadingLayout);
    }

    public StateLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.styleLoadingLayout);
    }

    public StateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mInflater = LayoutInflater.from(context);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingLayout, defStyleAttr, R.style.LoadingLayout_Style);

        mErrorText = a.getString(R.styleable.LoadingLayout_llErrorText);
        mRetryText = a.getString(R.styleable.LoadingLayout_llRetryText);



        mEmptyResId = a.getResourceId(R.styleable.LoadingLayout_llEmptyResId, R.layout.layout_empty);
        mLoadingResId = a.getResourceId(R.styleable.LoadingLayout_llLoadingResId, R.layout.layout_loading);
        mErrorResId = a.getResourceId(R.styleable.LoadingLayout_llErrorResId, R.layout.layout_error);
        a.recycle();
    }



    LayoutInflater mInflater;
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 0) {
            return;
        }
        if (getChildCount() > 1) {
            removeViews(1, getChildCount() - 1);
        }
        View view = getChildAt(0);
        setContentView(view);
        showLoading();
    }

    private void setContentView(View view) {
        mContentId = view.getId();
        mLayouts.put(mContentId, view);
    }

    public StateLayout setLoading(@LayoutRes int id) {
        if (mLoadingResId != id) {
            remove(mLoadingResId);
            mLoadingResId = id;
        }
        return this;
    }
    public StateLayout setEmpty(@LayoutRes int id) {
        if (mEmptyResId != id) {
            remove(mEmptyResId);
            mEmptyResId = id;
        }
        return this;
    }
    public StateLayout setOnEmptyInflateListener(OnInflateListener listener) {
        mOnEmptyInflateListener = listener;
        if (mOnEmptyInflateListener != null && mLayouts.containsKey(mEmptyResId)) {
            listener.onInflate(mLayouts.get(mEmptyResId));
        }
        return this;
    }
    public StateLayout setOnErrorInflateListener(OnInflateListener listener) {
        mOnErrorInflateListener = listener;
        if (mOnErrorInflateListener != null && mLayouts.containsKey(mErrorResId)) {
            listener.onInflate(mLayouts.get(mErrorResId));
        }
        return this;
    }

    public StateLayout setErrorText(String value) {
        mErrorText = value;
        text(mErrorResId, R.id.error_text, mErrorText);
        return this;
    }

    public StateLayout setRetryText(String text) {
        mRetryText = text;
        text(mErrorResId, R.id.retry_button, mRetryText);
        return this;
    }

    public StateLayout setRetryListener(OnClickListener listener) {
        mRetryListener = listener;
        return this;
    }


//    public LoadingLayout setTextColor(@ColorInt int color) {
//        mTextColor = color;
//        return this;
//    }
//    public LoadingLayout setTextSize(@ColorInt int dp) {
//        mTextColor = dp2px(dp);
//        return this;
//    }
//    public LoadingLayout setButtonTextColor(@ColorInt int color) {
//        mButtonTextColor = color;
//        return this;
//    }
//    public LoadingLayout setButtonTextSize(@ColorInt int dp) {
//        mButtonTextColor = dp2px(dp);
//        return this;
//    }
//    public LoadingLayout setButtonBackground(Drawable drawable) {
//        mButtonBackground = drawable;
//        return this;
//    }

    public boolean isLoading() {
        return isShowLoading;
    }

    public boolean isEmpty() {
        return isShowEmpty;
    }

    public boolean isError() {
        return isShowError;
    }

    public boolean isContent() {
        return isShowContent;
    }

    public void showLoading() {
        isShowLoading = true;
        isShowEmpty = false;
        isShowError = false;
        isShowContent = false;
        show(mLoadingResId);
    }

    public void showEmpty() {
        isShowLoading = false;
        isShowEmpty = true;
        isShowError = false;
        isShowContent = false;
        show(mEmptyResId);
    }

    public void showError() {
        isShowLoading = false;
        isShowEmpty = false;
        isShowError = true;
        isShowContent = false;
        show(mErrorResId);
    }

    public void showContent() {
        isShowLoading = false;
        isShowEmpty = false;
        isShowError = false;
        isShowContent = true;
        show(mContentId);
    }

    private void show(int layoutId) {
        for (View view : mLayouts.values()) {
            view.setVisibility(GONE);
        }
        layout(layoutId).setVisibility(VISIBLE);
    }

    private void remove(int layoutId) {
        if (mLayouts.containsKey(layoutId)) {
            View vg = mLayouts.remove(layoutId);
            removeView(vg);
        }
    }

    private View layout(int layoutId) {
        if (mLayouts.containsKey(layoutId)) {
            return mLayouts.get(layoutId);
        }
        View layout = mInflater.inflate(layoutId, this, false);
        layout.setVisibility(GONE);
        addView(layout);
        mLayouts.put(layoutId, layout);

        if (layoutId == mEmptyResId) {
            if (mOnEmptyInflateListener != null) {
                mOnEmptyInflateListener.onInflate(layout);
            }
        } else if (layoutId == mErrorResId) {
            TextView txt = (TextView) layout.findViewById(R.id.error_text);
            if (txt != null) {
                txt.setText(mErrorText);
            }
            TextView btn = (TextView) layout.findViewById(R.id.retry_button);
            if (btn != null) {
                btn.setText(mRetryText);
                btn.setOnClickListener(mRetryButtonClickListener);
            }
            if (mOnErrorInflateListener != null) {
                mOnErrorInflateListener.onInflate(layout);
            }
        }
        return layout;
    }

    private void text(int layoutId, int ctrlId, CharSequence value) {
        if (mLayouts.containsKey(layoutId)) {
            TextView view = (TextView) mLayouts.get(layoutId).findViewById(ctrlId);
            if (view != null) {
                view.setText(value);
            }
        }
    }

}
