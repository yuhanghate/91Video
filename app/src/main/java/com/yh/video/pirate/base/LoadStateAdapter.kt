package com.yh.video.pirate.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.yh.video.pirate.R

/**
 * 加载更多底部/加载中/重试
 */
class LoadStateAdapter<T:Any>(private val adapter: BaseAdapter<T>): LoadStateAdapter<LoadStateViewHolder>(){
    override fun onBindViewHolder(holderState: LoadStateViewHolder, loadState: LoadState) {
        holderState.onBindData(loadState, 0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val view = inflateView(parent, R.layout.item_network_state)
        return LoadStateViewHolder(view) { adapter.retry() }
    }

    private fun inflateView(viewGroup: ViewGroup, @LayoutRes viewType: Int): View {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return layoutInflater.inflate(viewType, viewGroup, false)
    }

}