package com.yh.video.pirate.ui.category.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseFragment
import com.yh.video.pirate.databinding.FragmentCategoryListBinding
import com.yh.video.pirate.ui.category.viewmodel.CategoryListViewModel
import com.yh.video.pirate.utils.dp
import com.yh.video.pirate.utils.loadFooterAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CategoryListFragment : BaseFragment<FragmentCategoryListBinding, CategoryListViewModel>()
     {

    /**
     * 第一次加载
     */
    var isCreate = false

    companion object {
        const val ID = "ID"
        const val TYPE = "TYPE"

        const val TYPE_ALL = ""//综合
        const val TYPE_NEW = "new"//最新
        const val TYPE_HOT = "hot"//最热
        const val TYPE_LIKE = "like"//最多喜欢

        fun newInstance(id: Int, type: String): CategoryListFragment {
            val args = Bundle()
            args.putInt(ID, id)
            args.putString(TYPE, type)
            val fragment = CategoryListFragment()
            fragment.arguments = args
            return fragment
        }
    }


    private fun getID() = arguments?.getInt(ID, 0)!!
    private fun getType() = arguments?.getString(TYPE, TYPE_ALL)!!

    override fun onLayoutId(): Int {
        return R.layout.fragment_category_list
    }

    override fun initView() {
        super.initView()
        initRefreshLayout()
        initRecyclerView()
    }

    override fun onClick() {
        super.onClick()
        mBinding.stateLayout.setRetryListener { mViewModel.adapter.retry() }
    }

    override fun initData() {
        super.initData()

        lifecycleScope.launch {
            mViewModel.getCategoryList(getID(), getType())
                .collect {
                    mViewModel.adapter.submitData(it)
                }
        }
    }

    override fun initRefreshLayout() {
        super.initRefreshLayout()
        mBinding.refreshLayout.setOnRefreshListener { mViewModel.adapter.refresh() }
    }

    override fun initRecyclerView() {
        super.initRecyclerView()
        val gridLayoutManager = GridLayoutManager(activity, 2)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (mViewModel.adapter.itemCount == position)  2 else 1
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

                outRect.top = 10.dp
                //如果不是占满一行的
                //如果不是占满一行的
                if (spanSize != gridLayoutManager.spanCount) {
                    if (spanIndex == 0) {
                        outRect.left = 10.dp
                    }
                    outRect.right = 10.dp
                }
            }
        })
        mBinding.recyclerView.layoutManager = gridLayoutManager
        mViewModel.adapter.addLoadStateListener { listener ->
            when (listener.refresh) {
                is LoadState.Error -> { // 加载失败
                    mBinding.refreshLayout.isRefreshing = false
                    mBinding.stateLayout.showError()
                }
                is LoadState.Loading -> { // 正在加载
                    if (!isCreate) {
                        mBinding.stateLayout.showLoading()
                        isCreate = true
                    } else {
                        mBinding.refreshLayout.isRefreshing = true
                    }
                }
                is LoadState.NotLoading -> { // 当前未加载中
                    mBinding.refreshLayout.isRefreshing = false
                    mBinding.stateLayout.showContent()
                }
            }

        }
        mBinding.recyclerView.adapter = mViewModel.adapter.loadFooterAdapter()
    }
}