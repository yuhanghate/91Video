package com.yh.video.pirate.ui.history.activity

import android.content.Context
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyf.immersionbar.ImmersionBar
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseActivity
import com.yh.video.pirate.databinding.ActivityHistoryBinding
import com.yh.video.pirate.ui.history.viewmodel.HistoryViewModel
import com.yh.video.pirate.utils.loadFooterAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * 历史记录
 */
class HistoryActivity:BaseActivity<ActivityHistoryBinding,HistoryViewModel>() {

    var isCreate = false
    companion object{
        fun start(context: Context) {
            val intent = Intent(context, HistoryActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onLayoutId(): Int {
        return R.layout.activity_history
    }

    override fun initStatusTool() {
        ImmersionBar.with(this)
            .statusBarView(mBinding.statusBar)
            .navigationBarColor(onNavigationBarColor()) //导航栏颜色，不写默认黑色
            .init()
    }

    override fun initView() {
        super.initView()
        initRefreshLayout()
        initRecyclerView()
    }

    override fun onClick() {
        super.onClick()
        mBinding.btnBack.setOnClickListener { onBackPressedSupport() }
    }

    override fun initData() {
        super.initData()
        lifecycleScope.launch {
            mViewModel.getHistoryList
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
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mBinding.recyclerView.adapter = mViewModel.adapter.loadFooterAdapter()
    }
}