package com.yh.video.pirate.base

import android.view.SoundEffectConstants
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.yh.video.pirate.listenter.OnClickItemListener
import com.yh.video.pirate.listenter.OnClickItemLongListener
import com.yh.video.pirate.utils.clickWithTrigger
import com.yuhang.novel.pirate.base.BaseViewHolder


abstract class BaseAdapter<T : Any>(diffCallback: DiffUtil.ItemCallback<T>) :
    PagingDataAdapter<T, BaseViewHolder<T, *>>(diffCallback = diffCallback) {

    private var mListener: Any? = null

    /**
     * 最后一次点击item角标
     */
    private var lastClickItemPosition = -1


    /**
     * 数据集
     */
    private var mList: ArrayList<T> = arrayListOf()

//    /**
//     * 获取列表
//     */
//    fun getList() = mList

    private var mLastClickTime: Long = 0


//    override fun getItemCount(): Int {
//        return mList.size
//    }

    fun setListener(listener: Any?): BaseAdapter<T> {
        mListener = listener
        return this
    }

//    /**
//     * 加载更多
//     */
//    fun loadMore(list: List<T>) {
//        if (list.isEmpty()) return
//        val startPosition = mList.size
//        mList.addAll(list)
//        notifyItemRangeInserted(startPosition, mList.size)
//        notifyItemChanged(startPosition - 1)
//    }

//    /**
//     * 下拉刷新
//     */
//    fun setRefersh(list: List<T>) {
////        if (list.isEmpty()) return
//        mList.clear()
//        mList.addAll(list)
//        notifyDataSetChanged()
//    }

//    /**
//     * 初始化数据
//     */
//    open fun initData(list: List<T>) {
//        mList.addAll(list)
//    }

    open fun getObj(position: Int): T {
        return getItem(position) as T
    }




    /**
     * 绑定ViewHolder
     */
    override fun onBindViewHolder(holder: BaseViewHolder<T, *>, position: Int) {
        //设置Item点击事件
        holder.itemView.clickWithTrigger {
            lastClickItemPosition = position

            (mListener as? OnClickItemListener)?.onClickItemListener(it, position)
        }

        //长按事件
        holder.itemView.setOnLongClickListener {
            it.playSoundEffect(SoundEffectConstants.CLICK)
            (mListener as? OnClickItemLongListener)?.onClickItemLongListener(it, position)
            true
        }

        holder.lastClickItemPosition = lastClickItemPosition
        //绑定View
//        holder.setListener(mListener).bindData(getObj(position), position)
        holder.setListener(mListener).onBindViewHolder(getObj(position), position)
    }



}

