package com.yh.video.pirate.ui.main.fragment

import android.graphics.Rect
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseFragment
import com.yh.video.pirate.databinding.FragmentMainBinding
import com.yh.video.pirate.ui.main.viewmodel.MainViewModel
import com.yh.video.pirate.utils.BarConfig
import com.yh.video.pirate.utils.dp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(), OnRefreshListener {
    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun onStatusColor(): Int {
        return R.color.md_grey_900
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
    }

    override fun initData() {
        super.initData()
        lifecycleScope.launch {
            mViewModel.mMainList
                .collect {
                    mViewModel.adapter.submitData(lifecycle, it)
                }
        }
    }

    override fun initRefreshLayout() {
        super.initRefreshLayout()
        mBinding.refreshLayout.setOnRefreshListener(this)
        mBinding.refreshLayout.setEnableLoadMore(false)
//        mBinding.refreshLayout.autoRefreshAnimationOnly()
    }

    override fun initRecyclerView() {
        super.initRecyclerView()
        val gridLayoutManager = GridLayoutManager(activity, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val obj = mViewModel.adapter.getObj(position)
                return if (obj.id != null) 1 else 2
            }
        }
        mBinding.recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
                val spanSize = layoutParams.spanSize //占用的跨度数
                val spanIndex = layoutParams.spanIndex //最终跨度位置。应介于0（含）和 spanCount之间

                outRect.top = 15.dp
                //如果不是占满一行的
                //如果不是占满一行的
                if (spanSize != gridLayoutManager.spanCount) {
                    if (spanIndex == 0) {
                        outRect.left = 15.dp
                    }
                    outRect.right = 15.dp
                }
            }
        })
        mBinding.recyclerView.layoutManager = gridLayoutManager
        mViewModel.adapter.addLoadStateListener { listener ->
            when (listener.refresh) {
                is LoadState.Error -> { // 加载失败
                    mBinding.refreshLayout.finishRefresh()
                    mBinding.stateLayout.showError()
                }
                is LoadState.Loading -> { // 正在加载
                    if (mBinding.refreshLayout.state == RefreshState.None) {
                        mBinding.refreshLayout.autoRefreshAnimationOnly()
                    }else if (mBinding.stateLayout.isContent) {
                        mBinding.stateLayout.showLoading()
                    }
                }
                is LoadState.NotLoading -> { // 当前未加载中
                    mBinding.refreshLayout.finishRefresh()
                    mBinding.stateLayout.showContent()
                }
            }

        }
//        lifecycleScope.launch {
//            @OptIn(ExperimentalCoroutinesApi::class)
//            mViewModel.adapter.loadStateFlow.collectLatest {
//                Logger.t("addLoadStateListener").i("loadStateFlow ${it.refresh.toString()}")
//                if(it.refresh !is LoadState.Loading){
//                    mBinding.refreshLayout.finishRefresh()
//                }
//            }
//        }

        mBinding.recyclerView.adapter = mViewModel.adapter
    }

    override fun onClick() {
        super.onClick()
        mBinding.stateLayout.setRetryListener { mViewModel.adapter.retry() }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mViewModel.adapter.refresh()
    }
}