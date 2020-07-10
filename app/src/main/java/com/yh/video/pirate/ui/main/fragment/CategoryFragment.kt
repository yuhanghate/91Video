package com.yh.video.pirate.ui.main.fragment

import android.graphics.Rect
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseFragment
import com.yh.video.pirate.databinding.FragmentCategoryBinding
import com.yh.video.pirate.ui.main.viewmodel.CategoryViewModel
import com.yh.video.pirate.utils.BarConfig
import com.yh.video.pirate.utils.addDefaultStateListener
import com.yh.video.pirate.utils.dp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CategoryFragment : BaseFragment<FragmentCategoryBinding, CategoryViewModel>() {

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
        mBinding.refreshLayout.setOnRefreshListener { mViewModel.adapter.refresh() }
    }

    override fun onClick() {
        super.onClick()
        mBinding.stateLayout.setRetryListener { mViewModel.adapter.retry() }
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

                outRect.top = 15.dp
                //如果不是占满一行的
                if (spanSize != gridLayoutManager.spanCount) {
                    if (spanIndex == 0) {
                        outRect.left = 15.dp
                    }
                    if (spanIndex == gridLayoutManager.spanCount) {
                        outRect.right = 15.dp
                    } else {
                        outRect.right = 15.dp
                    }


                }
            }
        })
        mBinding.recyclerView.layoutManager = gridLayoutManager
        mViewModel.adapter.addDefaultStateListener(
            this,
            mBinding.refreshLayout,
            mBinding.stateLayout
        )
        mBinding.recyclerView.adapter = mViewModel.adapter
    }

}