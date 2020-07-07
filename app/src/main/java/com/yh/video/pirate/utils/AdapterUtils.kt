package com.yh.video.pirate.utils

import android.util.Log
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.orhanobut.logger.Logger
import com.yh.video.pirate.base.BaseActivity
import com.yh.video.pirate.base.BaseAdapter
import com.yh.video.pirate.base.BaseFragment
import com.yh.video.pirate.base.LoadStateAdapter
import com.yh.video.pirate.widget.StateLayout

///**
// * 加载底部Adapter
// */
//fun <T:BaseAdapter<*>> T.loadStateAdapter():T{
//    this.withLoadStateHeaderAndFooter(footer = LoadStateAdapter(this), header = LoadStateAdapter(this))
//    return this
//}

/**
 * 加载底部Adapter
 */
fun <T : BaseAdapter<*>> T.loadFooterAdapter(): ConcatAdapter {
    return this.withLoadStateFooter(footer = LoadStateAdapter(this))
}

fun <T : BaseAdapter<*>> T.addDefaultStateListener(
    fragment: BaseFragment<*, *>,
    refreshLayout: SwipeRefreshLayout,
    stateLayout: StateLayout
) {
    addLoadStateListener {
        when (it.refresh) {
            is LoadState.Error -> { // 加载失败
                refreshLayout.isRefreshing = false
                stateLayout.showError()
            }
            is LoadState.Loading -> { // 正在加载
                if (!fragment.isCreateLoading) {
                    stateLayout.showLoading()
                    fragment.isCreateLoading = true
                } else {
                    if (!stateLayout.isLoading) {
                        refreshLayout.isRefreshing = true
                    }

                }
            }
            is LoadState.NotLoading -> { // 当前未加载中
                if (!stateLayout.isLoading) {
                    refreshLayout.isRefreshing = false
                }

                stateLayout.showContent()
            }
        }
    }
}

fun <T : BaseAdapter<*>> T.addDefaultStateListener(
    activity: BaseActivity<*, *>,
    refreshLayout: SwipeRefreshLayout,
    stateLayout: StateLayout
) {
    addLoadStateListener {
        when (it.refresh) {
            is LoadState.Error -> { // 加载失败
                refreshLayout.isRefreshing = false
                stateLayout.showError()
            }
            is LoadState.Loading -> { // 正在加载
                if (!activity.isCreateLoading) {
                    stateLayout.showLoading()
                    activity.isCreateLoading = true
                } else {
                    refreshLayout.isRefreshing = true
                }
            }
            is LoadState.NotLoading -> { // 当前未加载中
                refreshLayout.isRefreshing = false
                stateLayout.showContent()
            }
        }
    }
}