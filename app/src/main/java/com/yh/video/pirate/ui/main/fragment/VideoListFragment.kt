package com.yh.video.pirate.ui.main.fragment

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseFragment
import com.yh.video.pirate.databinding.FragmentVideoListBinding
import com.yh.video.pirate.ui.main.viewmodel.VideoListViewModel
import kotlinx.coroutines.launch

/**
 * 分类视频列表
 */
class VideoListFragment : BaseFragment<FragmentVideoListBinding, VideoListViewModel>() {


    companion object {
        const val VIDEO_ID = "VIDEO_ID"
        fun newInstance(id:Int): VideoListFragment {
            val args = Bundle()
            args.putInt(VIDEO_ID, id)
            val fragment = VideoListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    fun getVideoId() = arguments?.getInt(VIDEO_ID)

    override fun onLayoutId(): Int {
        return R.layout.fragment_video_list
    }

    override fun initView() {
        super.initView()
        mBinding.stateLayout.showLoading()
    }

    override fun initData() {
        super.initData()


    }
}