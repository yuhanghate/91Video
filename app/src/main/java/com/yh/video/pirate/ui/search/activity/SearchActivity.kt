package com.yh.video.pirate.ui.search.activity

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.text.Editable
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.gyf.immersionbar.ImmersionBar
import com.orhanobut.logger.Logger
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseActivity
import com.yh.video.pirate.databinding.ActivitySearchBinding
import com.yh.video.pirate.databinding.LayoutSearchLabelBinding
import com.yh.video.pirate.listenter.OnClickItemListener
import com.yh.video.pirate.repository.database.entity.SearchHistoryEntity
import com.yh.video.pirate.repository.network.exception.catchCode
import com.yh.video.pirate.repository.network.exception.convertHttpRes
import com.yh.video.pirate.repository.network.result.SearchKeywords
import com.yh.video.pirate.ui.search.viewmodel.SearchViewModel
import com.yh.video.pirate.ui.video.activity.VideoPlayActivity
import com.yh.video.pirate.utils.clickWithTrigger
import com.yh.video.pirate.utils.dp
import com.yh.video.pirate.utils.loadFooterAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 * 搜索
 */
class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>(),
    TextView.OnEditorActionListener, OnClickItemListener {
    private var LONG_SEARCH_SUGGES = System.currentTimeMillis()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initStatusTool() {
        ImmersionBar.with(this)
            .statusBarView(mBinding.statusBar)
            .navigationBarColor(onNavigationBarColor()) //导航栏颜色，不写默认黑色
            .init()
    }

    override fun onClick() {
        super.onClick()
        mBinding.btnBack.setOnClickListener { onBackPressedSupport() }
        mBinding.searchEt.setOnEditorActionListener(this)
        mBinding.btnSearch.clickWithTrigger {
            val keyword = mBinding.searchEt.text.toString()
            //保存关键词
            mViewModel.insertSearchKeywords(keyword)
            netServiceSearchSuggest(keyword)
        }
        mBinding.btnClear.clickWithTrigger { mBinding.searchEt.setText("", null) }
        mBinding.btnHistoryClean.clickWithTrigger { cleanLocalDialog() }
    }

    override fun initView() {
        super.initView()
        initRecyclerView()
        //模糊搜索
        mBinding.searchEt.doAfterTextChanged { s ->
            if (TextUtils.isEmpty(s?.toString())) {
                mBinding.btnClear.visibility = View.GONE
                mBinding.stateLayout.visibility = View.GONE
                return@doAfterTextChanged
            }
            mBinding.btnClear.visibility = View.VISIBLE

            //不能重复点击
            if (System.currentTimeMillis() - LONG_SEARCH_SUGGES > 700 && mViewModel.searchSuggestStr != s?.toString()) {

                netServiceSearchSuggest(s?.toString())
                LONG_SEARCH_SUGGES = System.currentTimeMillis()
            }
        }
    }

    override fun initData() {
        super.initData()

        //加载服务器标签
        lifecycleScope.launch {
            mViewModel.getSearchKeyword()
//                .flowOn(Dispatchers.IO)
                .collect {
                    it.catchCode<List<SearchKeywords>>(
                        success = { list ->
                            initServiceLabel(list)
                        },
                        error = {}
                    )
                }
        }

        //加载本地标签
        lifecycleScope.launch {
            initLocalLabel(mViewModel.querySearchKeywords())
        }
    }

    override fun initRecyclerView() {
        super.initRecyclerView()
        val gridLayoutManager = GridLayoutManager(this, 2)
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

                outRect.top = 20.dp
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

        mBinding.stateLayout.showContent()
        mViewModel.adapter.addLoadStateListener { listener ->
            if (listener.refresh == LoadState.Loading) {
                mBinding.stateLayout.showContent()
            } else {
                mBinding.stateLayout.showContent()
            }
        }
        mViewModel.adapter.setListener(this)
        mBinding.recyclerView.layoutManager = gridLayoutManager
        mBinding.recyclerView.adapter = mViewModel.adapter.loadFooterAdapter()
    }

    /**
     * 从服务器获取标签
     */
    private fun initServiceLabel(list: List<SearchKeywords>) {
        if (list.isNotEmpty()) {
            //热闹搜索
            mBinding.hotFl.removeAllViews()
            list[0].lists?.forEach { label ->
                val inflate = LayoutSearchLabelBinding.inflate(layoutInflater, null, false)
                inflate.labelTv.text = label
                mBinding.hotFl.addView(inflate.root)
                inflate.labelLl.setOnClickListener {
                    mViewModel.insertSearchKeywords(label)
                    mBinding.searchEt.setText(label, null)
                    mBinding.searchEt.clearFocus()
                }
            }
        }
        if (list.size == 2) {
            //推荐
            mBinding.recommendFl.removeAllViews()
            list[1].lists?.forEach { label ->
                val inflate = LayoutSearchLabelBinding.inflate(layoutInflater, null, false)
                inflate.labelTv.text = label
                mBinding.recommendFl.addView(inflate.root)
                inflate.labelLl.setOnClickListener {
                    mViewModel.insertSearchKeywords(label)
                    mBinding.searchEt.setText(label, null)
                    mBinding.searchEt.clearFocus()
                }
            }
        }
    }

    /**
     * 加载本地标签
     */
    private fun initLocalLabel(list: List<SearchHistoryEntity>) {
        mBinding.localFl.removeAllViews()
        list.forEach { entity ->
            val inflate = LayoutSearchLabelBinding.inflate(layoutInflater, null, false)
            inflate.labelTv.text = entity.keyword
            mBinding.localFl.addView(inflate.root)
            inflate.labelLl.setOnClickListener {
                mViewModel.insertSearchKeywords(entity.keyword)
                mBinding.searchEt.setText(entity.keyword, null)
                mBinding.searchEt.clearFocus()
            }
        }
    }


    /**
     * 键盘搜索
     */
    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId === EditorInfo.IME_ACTION_SEARCH) {
            // 当按了搜索之后关闭软键盘
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(
                    this.currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            val keyword = v?.text.toString().trim()
            //保存关键词
            mViewModel.insertSearchKeywords(keyword)
            netServiceSearchSuggest(keyword)
            return true
        }
        return false
    }

    /**
     * 关键字联想
     */
    private fun netServiceSearchSuggest(keyword: String?) {
        if (keyword.isNullOrEmpty()) {
            mBinding.stateLayout.isVisible = false
            return
        }
        mBinding.stateLayout.isVisible = true
        //搜索视频列表
        lifecycleScope.launch {
            mViewModel.getSearch(keyword)
                .collect {
                    mViewModel.adapter.submitData(it)
                }
        }

    }

    /**
     * 是否清空记录
     */
    private fun cleanLocalDialog() {
        MaterialDialog(this).show {
            message(text = "是否清空搜索历史记录?")
            negativeButton(text = "取消")
            positiveButton(text = "确定")
            negativeButton { mViewModel.cleanSearchkeywords() }
        }.show()
    }

    override fun onBackPressedSupport() {
        //隐藏搜索结果页
        if (mBinding.stateLayout.isVisible) {
            mBinding.stateLayout.isVisible = false
            return
        }

        super.onBackPressedSupport()
    }


    /**
     * 列表点击事件
     */
    override fun onClickItemListener(view: View, position: Int) {
        val obj = mViewModel.adapter.getObj(position)
        VideoPlayActivity.start(this, obj.id?.toLong()!!)
    }
}