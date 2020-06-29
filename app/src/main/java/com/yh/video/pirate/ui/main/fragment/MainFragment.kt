package com.yh.video.pirate.ui.main.fragment

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseFragment
import com.yh.video.pirate.databinding.FragmentMainBinding
import com.yh.video.pirate.repository.network.exception.convertHttpRes
import com.yh.video.pirate.repository.network.exception.handlingHttpResponse
import com.yh.video.pirate.repository.network.result.MainResult
import com.yh.video.pirate.repository.network.result.base.CaomeiResponse
import com.yh.video.pirate.ui.main.viewmodel.MainViewModel
import com.yh.video.pirate.utils.timelyToast
import com.yh.video.pirate.utils.toast
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment:BaseFragment<FragmentMainBinding, MainViewModel>() ,
    SwipeRefreshLayout.OnRefreshListener {
    companion object{
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

    }

    override fun initData() {
        super.initData()
        lifecycleScope.launch {
            mViewModel.getMainList()
                .catch { activity?.toast("加载失败") }
                .collect {
                    handlingHttpResponse<CaomeiResponse<List<MainResult>>>(
                        it.convertHttpRes(),
                        successBlock = {},
                        failureBlock = {}
                    )
                }
        }
    }


    override fun initRefreshLayout() {
        super.initRefreshLayout()
        mBinding.refreshLayout.setOnRefreshListener(this)
        mBinding.refreshLayout.isRefreshing = true
    }

    override fun initRecyclerView() {
        super.initRecyclerView()
        val gridLayoutManager = GridLayoutManager(activity, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 1
            }
        }
    }

    override fun onRefresh() {
    }
}