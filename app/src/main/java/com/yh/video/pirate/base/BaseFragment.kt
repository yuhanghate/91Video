package com.yh.video.pirate.base

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.yh.video.pirate.R
import com.yh.video.pirate.utils.TopSmoothScroller
import com.yh.video.pirate.utils.viewbinding.FragmentViewBinderUtils
import me.yokeyword.fragmentation.SupportFragment
import org.greenrobot.eventbus.EventBus
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<D : ViewBinding, VM : BaseViewModel> : SupportFragment() {

    var mActivity: BaseActivity<*, *>? = null

    lateinit var mBinding: D

    lateinit var mViewModel: VM

    /**
     * 是否首次加载数据
     */
    var isCreateLoading = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initContentView(container)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (!::mViewModel.isInitialized) {
            mViewModel = ViewModelProvider(this)[getViewModelClass()]
            mViewModel.mFragment = this
            initView()
            initData()
            onClick()
        }


    }

    private fun getViewModelClass(): Class<out VM> {
        return (this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<out VM>
    }

    private fun initContentView(container: ViewGroup?): View {

        if (::mBinding.isInitialized) {
            return mBinding.root
        }

        container?.setBackgroundResource(android.R.color.transparent)


        val view  =  layoutInflater.inflate(onLayoutId(), null, false)
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        view.layoutParams = params

        //利用反射，调用指定ViewBinding中的inflate方法填充视图
        val type = javaClass.genericSuperclass
        val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<D>
        val fragmentViewBinder = FragmentViewBinderUtils(clazz)
        mBinding = fragmentViewBinder.bind(view)
        return mBinding.root
    }

    /**
     * 赋值Activity
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as BaseActivity<*, *>
    }

    /**
     * 回收Activity
     */
    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }

    /**************************** 子类继承 start **************************/

    /**
     * 布局文件
     */
    @LayoutRes
    abstract fun onLayoutId(): Int

    /**
     * 初始化View
     */
    open fun initView() {

    }

    open fun initData(){

    }

    /**
     * 点击事件
     */
    open fun onClick() {

    }


    open fun isSelect(select: Boolean) {

    }

    /**
     * 沉浸式颜色
     */
    open fun onStatusColor(): Int {
        return R.color.primary_dark
    }

    /**************************** 子类继承 end **************************/

    /**************************** 子类使用 start **************************/

    /**
     * 替换Fragment
     */
    fun replaceFragment(
        id: Int,
        fragment: SupportFragment
    ) {
        mActivity?.supportFragmentManager?.beginTransaction()
            ?.replace(id, fragment)
            ?.commitNow()
    }


    /**
     * 添加所有fragment
     */
    fun addFragmentList(
        id: Int,
        fragmentList: List<SupportFragment>, showIndex: Int
    ) {
        val beginTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentList.forEach { beginTransaction?.add(id, it) }
        (fragmentList.indices).forEachIndexed { index, _ ->
            if (index == showIndex) {
                beginTransaction?.show(fragmentList[index])
            } else {
                beginTransaction?.hide(fragmentList[index])
            }
        }
        beginTransaction?.commit()

    }

    /**
     * 显示Fragment
     */
    fun showFragment(
        fragmentList: List<SupportFragment>,
        showIndex: Int
    ) {
        val beginTransaction = activity?.supportFragmentManager?.beginTransaction()
        (fragmentList.indices).forEachIndexed { index, i ->
            if (index == showIndex) {
                beginTransaction?.show(fragmentList[index])
            } else {
                beginTransaction?.hide(fragmentList[index])
            }
        }
        beginTransaction?.commit()
    }



    /**
     * 打开Popup框
     */
    fun showPopupMenu(
        view: View, @MenuRes layout: Int,
        itemListener: PopupMenu.OnMenuItemClickListener? = null,
        dismissListener: PopupMenu.OnDismissListener? = null
    ): PopupMenu {
        // 这里的view代表popupMenu需要依附的view
        val popupMenu = PopupMenu(requireContext(), view)
        // 获取布局文件
        popupMenu.menuInflater.inflate(layout, popupMenu.menu)
        popupMenu.gravity = Gravity.END
        // 通过上面这几行代码，就可以把控件显示出来了
        popupMenu.setOnMenuItemClickListener(itemListener)
        popupMenu.setOnDismissListener(dismissListener)

        popupMenu.show()

        return popupMenu

    }

    /**
     * 注册Eventbus
     */
    fun onCreateEventbus(any: Any) {
        if (!EventBus.getDefault().isRegistered(any)) {
            EventBus.getDefault()
                .register(any)
        }
    }

    /**
     * 回收Eventbus
     */
    fun onDestryEventbus(any: Any) {
        if (EventBus.getDefault().isRegistered(any)) {
            EventBus.getDefault()
                .unregister(any)
        }
    }

    /**
     * 获取状态栏高度
     */
    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    /**
     * 初始化RecyclerView列表数据
     */
    open fun initRecyclerView() {

    }

    /**
     * RecyclerView
     */
    fun addOnScrollListener(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(this@BaseFragment).resumeRequests()
                } else {
                    Glide.with(this@BaseFragment).pauseRequests()
                }
            }
        })

    }


    /**
     * 双击置顶
     */
    fun onTopRecyclerView(
        refreshLayout: SwipeRefreshLayout,
        recyclerView: RecyclerView,
        position: Int
    ) {

        val manager = recyclerView.layoutManager as? LinearLayoutManager
        val firstItem = manager?.findFirstVisibleItemPosition()
        //刷新
        if (firstItem == 0) {
            refreshLayout.isRefreshing = true
            return
        }

        //置顶
        val scroller = TopSmoothScroller(mActivity!!)
        scroller.smoothMoveToPosition(recyclerView, position)
    }

    /**
     * 初始化下拉刷新
     */
    open fun initRefreshLayout() {
    }

    /**************************** 子类使用 end **************************/

}