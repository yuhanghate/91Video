package com.yh.video.pirate.ui.main.fragment

import android.view.View
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseFragment
import com.yh.video.pirate.databinding.FragmentDiscoverBinding
import com.yh.video.pirate.ui.main.viewmodel.DiscoverViewModel
import com.yh.video.pirate.utils.BarConfig
import com.yh.video.pirate.utils.dp
import com.yh.video.pirate.utils.toDp
import com.yh.video.pirate.widget.DoubleClickListener
import com.yh.video.pirate.widget.SpaceItemDecoration
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DiscoverFragment : BaseFragment<FragmentDiscoverBinding, DiscoverViewModel>(),
    OnRefreshListener {

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
        initRecyclerView()
        initRefreshLayout()
        initStatusBar()
    }

    /**
     * 初始化状态栏
     */
    private fun initStatusBar() {
        val statusBarHeight = BarConfig(requireActivity()).statusBarHeight
        val layoutParams = mBinding.statusBar.layoutParams
        layoutParams.height = statusBarHeight
        mBinding.statusBar.layoutParams = layoutParams

        mBinding.recyclerView.updatePadding(top = statusBarHeight / 2 + 56.dp)

        //设置下拉偏移量
        mBinding.refreshLayout.setHeaderInsetStart(statusBarHeight.toDp + 56f)
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
        mBinding.refreshLayout.setOnRefreshListener(this)
        mBinding.refreshLayout.setEnableLoadMore(false)
    }

    override fun initRecyclerView() {
        super.initRecyclerView()
        mViewModel.adapter.addLoadStateListener { listener ->
            when (listener.refresh) {
                is LoadState.Error -> { // 加载失败
                    mBinding.refreshLayout.finishRefresh()
                    mBinding.stateLayout.showError()
                }
                is LoadState.Loading -> { // 正在加载
                    if (mBinding.refreshLayout.state == RefreshState.None) {
                        mBinding.refreshLayout.autoRefreshAnimationOnly()
                    } else if (mBinding.stateLayout.isContent && mBinding.refreshLayout.state != RefreshState.Refreshing) {
                        mBinding.stateLayout.showLoading()
                    }
                }
                is LoadState.NotLoading -> { // 当前未加载中
                    mBinding.refreshLayout.finishRefresh()
                    mBinding.stateLayout.showContent()
                }
            }
        }
        mBinding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        mBinding.recyclerView.adapter = mViewModel.adapter
    }

    override fun onClick() {
        super.onClick()

        /**
         * 双击置顶
         */
        mBinding.titleTv.setOnClickListener(DoubleClickListener(DoubleClickListener.DoubleClickCallBack {
            onTopRecyclerView(mBinding.refreshLayout, mBinding.recyclerView, 10)
        }))
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mViewModel.adapter.refresh()
    }
}