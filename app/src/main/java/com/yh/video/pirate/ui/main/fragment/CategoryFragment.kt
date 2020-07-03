package com.yh.video.pirate.ui.main.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseFragment
import com.yh.video.pirate.databinding.FragmentCategoryBinding
import com.yh.video.pirate.repository.network.exception.catchCode
import com.yh.video.pirate.repository.network.result.VideoTypeResult
import com.yh.video.pirate.ui.main.viewmodel.CategoryViewModel
import com.yh.video.pirate.utils.BarConfig
import com.yh.video.pirate.utils.bindViewPager
import com.yh.video.pirate.utils.dp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView

class CategoryFragment : BaseFragment<FragmentCategoryBinding, CategoryViewModel>(),
    OnRefreshListener {

    private var isCreate = false

    companion object {
        fun newInstance(): CategoryFragment {
            return CategoryFragment()
        }
    }

    override fun onLayoutId(): Int {
        return R.layout.fragment_category
    }

    override fun initView() {
        super.initView()

        initStatusBar()
        initRefreshLayout()
        initRecyclerView()
    }

    override fun initData() {
        super.initData()
        lifecycleScope.launch {
            mViewModel.mVideoType.collect {
                mViewModel.adapter.submitData(it)
            }
        }
    }

    private fun initStatusBar() {
        val statusBarHeight = BarConfig(requireActivity()).statusBarHeight
        val layoutParams = mBinding.statusBar.layoutParams
        layoutParams.height = statusBarHeight
        mBinding.statusBar.layoutParams = layoutParams
    }

    override fun initRefreshLayout() {
        super.initRefreshLayout()
        mBinding.refreshLayout.setOnRefreshListener(this)
        mBinding.refreshLayout.setEnableLoadMore(false)
    }

    override fun onClick() {
        super.onClick()
        mBinding.stateLayout.setRetryListener { mViewModel.adapter.retry() }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mViewModel.adapter.refresh()
    }


    override fun initRecyclerView() {
        super.initRecyclerView()
        val gridLayoutManager = GridLayoutManager(activity, 2)
        mBinding.recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
                val spanSize = layoutParams.spanSize //占用的跨度数
                val spanIndex = layoutParams.spanIndex //最终跨度位置。应介于0（含）和 spanCount之间

                outRect.top = 10.dp
                //如果不是占满一行的
                if (spanSize != gridLayoutManager.spanCount) {
                    if (spanIndex == 0) {
                        outRect.left = 15.dp
                    }
                    if (spanIndex == gridLayoutManager.spanCount) {
                        outRect.right = 15.dp
                    } else {
                        outRect.right = 8.dp
                    }


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
                    if (!isCreate) {
                        mBinding.stateLayout.showLoading()
                        isCreate = true
                    } else {
                        mBinding.refreshLayout.autoRefreshAnimationOnly()
                    }
                }
                is LoadState.NotLoading -> { // 当前未加载中
                    mBinding.refreshLayout.finishRefresh()
                    mBinding.stateLayout.showContent()
                }
            }

        }
        mBinding.recyclerView.adapter = mViewModel.adapter
    }

}