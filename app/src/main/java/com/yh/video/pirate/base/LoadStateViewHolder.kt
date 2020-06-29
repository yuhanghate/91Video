package com.yh.video.pirate.base

import android.view.View
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.yh.video.pirate.databinding.ItemNetworkStateBinding
import com.yh.video.pirate.utils.isVisible
import com.yuhang.novel.pirate.base.BaseViewHolder

class LoadStateViewHolder(val view:View, private val retryCallback: () -> Unit): RecyclerView.ViewHolder(view),
    BaseViewHolder.OnBindDataListener<LoadState> {
    private val mBinding = ItemNetworkStateBinding.bind(view)


    override fun onBindData(obj: LoadState, position: Int) {

        // 正在加载，显示进度条
        mBinding.progressBar.isVisible = obj is LoadState.Loading
        // 加载失败，显示并点击重试按钮
        mBinding.retryButton.isVisible = obj is LoadState.Error
        mBinding.retryButton.setOnClickListener { retryCallback }
        // 加载失败显示错误原因
        mBinding.errorMsg.isVisible = !(obj as? LoadState.Error)?.error?.message.isNullOrBlank()
        mBinding.errorMsg.text = (obj as? LoadState.Error)?.error?.message
    }


}