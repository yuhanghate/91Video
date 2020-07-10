package com.yh.video.pirate.ui.main.fragment

import android.graphics.Rect
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseFragment
import com.yh.video.pirate.databinding.FragmentMainBinding
import com.yh.video.pirate.repository.preferences.PreferenceUtil
import com.yh.video.pirate.ui.history.activity.HistoryActivity
import com.yh.video.pirate.ui.main.viewmodel.MainViewModel
import com.yh.video.pirate.ui.search.activity.SearchActivity
import com.yh.video.pirate.utils.AppManagerUtils
import com.yh.video.pirate.utils.BarConfig
import com.yh.video.pirate.utils.addDefaultStateListener
import com.yh.video.pirate.utils.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {

    //第一次加载
    var isCreate = false

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
        initRefreshLayout()
        initRecyclerView()
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

        //延迟弹窗
//        lifecycleScope.launch {
//            flow {
//                delay(1000 * 2L)
//                emit(1)
//            }.collect {
//                val lastLogin = PreferenceUtil.getLong("last_time", System.currentTimeMillis())
//                val showNote = PreferenceUtil.getBoolean("is_show_note", true)
//                if (System.currentTimeMillis() - lastLogin > 86400 * 3L) {
//                    showNoteMaxDialog()
//                } else {
//                    if (showNote) {
//                        showNoteDialog()
//                    }
//
//                }
//
//            }
//        }
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
        mViewModel.adapter.addDefaultStateListener(
            this,
            mBinding.refreshLayout,
            mBinding.stateLayout
        )
        mBinding.recyclerView.adapter = mViewModel.adapterFooter
    }

    override fun onClick() {
        super.onClick()
        //重试
        mBinding.stateLayout.setRetryListener { mViewModel.adapter.retry() }
        //历史记录
        mBinding.btnHistory.setOnClickListener { HistoryActivity.start(requireContext()) }
        //搜索
        mBinding.searchCl.setOnClickListener { SearchActivity.start(requireContext()) }
    }

    private fun showNoteDialog() {
        MaterialDialog(requireContext()).show {
            message(text = "欢迎使用!新用户可以免费观看3天\n")
            cancelable(false)
            positiveButton(text = "确定", click = {
                PreferenceUtil.commitLong("last_time",System.currentTimeMillis())
                PreferenceUtil.commitBoolean("is_show_note" ,false)
                it.dismiss()
            })
        }
    }

    private fun showNoteMaxDialog() {
        MaterialDialog(requireContext()).show {
            message(text = "试用期到已到,无法使用")
            cancelable(false)
            positiveButton(text = "确定", click = {
                AppManagerUtils.getAppManager().AppExit(requireContext())
            })
        }
    }

}