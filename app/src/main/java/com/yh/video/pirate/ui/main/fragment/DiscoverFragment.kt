package com.yh.video.pirate.ui.main.fragment

import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseFragment
import com.yh.video.pirate.databinding.FragmentDiscoverBinding
import com.yh.video.pirate.ui.main.viewmodel.DiscoverViewModel
import com.yh.video.pirate.utils.BarConfig
import com.yh.video.pirate.utils.addDefaultStateListener
import com.yh.video.pirate.utils.dp
import com.yh.video.pirate.utils.loadFooterAdapter
import com.yh.video.pirate.widget.DoubleClickListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DiscoverFragment : BaseFragment<FragmentDiscoverBinding, DiscoverViewModel>() {

    //第一次加载
    var isCreate = false

    companion object {
        fun newInstance(): DiscoverFragment {
            return DiscoverFragment()
        }
    }

    override fun onLayoutId(): Int {
        return R.layout.fragment_discover
    }


    override fun initView() {
        super.initView()
        initStatusBar()
        initRefreshLayout()
        initRecyclerView()
    }

    /**
     * 初始化状态栏
     */
    private fun initStatusBar() {
        val statusBarHeight = BarConfig(requireActivity()).statusBarHeight
        val layoutParams = mBinding.statusBar.layoutParams
        layoutParams.height = statusBarHeight
        mBinding.statusBar.layoutParams = layoutParams

        mBinding.recyclerView.updatePadding(top = statusBarHeight + 48.dp)

        //设置下拉偏移量
        val endOffset = mBinding.refreshLayout.progressViewEndOffset
        val startOffset = mBinding.refreshLayout.progressViewStartOffset
        val offset = statusBarHeight + 48.dp
        mBinding.refreshLayout.setProgressViewOffset(
            false,
            startOffset + offset,
            endOffset + 48.dp
        )
    }

    override fun initData() {
        super.initData()
        lifecycleScope.launch {
            mViewModel.videoList.collect {
                mViewModel.adapter.submitData(lifecycle, it)
            }
        }
    }

    override fun initRefreshLayout() {
        super.initRefreshLayout()
        mBinding.refreshLayout.setOnRefreshListener { mViewModel.adapter.refresh() }
    }

    override fun initRecyclerView() {
        super.initRecyclerView()
        mViewModel.adapter.addDefaultStateListener(this,mBinding.refreshLayout,mBinding.stateLayout)
        mBinding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        mBinding.recyclerView.adapter = mViewModel.adapter.loadFooterAdapter()
    }

    override fun onClick() {
        super.onClick()

        /**
         * 双击置顶
         */
        mBinding.titleTv.setOnClickListener(DoubleClickListener(DoubleClickListener.DoubleClickCallBack {
            onTopRecyclerView(mBinding.refreshLayout, mBinding.recyclerView, 10)
        }))

        mBinding.stateLayout.setRetryListener { mViewModel.adapter.retry() }
    }
}