package com.yh.video.pirate.ui.main.fragment

import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseFragment
import com.yh.video.pirate.databinding.FragmentDiscoverBinding
import com.yh.video.pirate.ui.main.viewmodel.DiscoverViewModel

class DiscoverFragment:BaseFragment<FragmentDiscoverBinding, DiscoverViewModel>() {

    companion object{
        fun newInstance(): DiscoverFragment{
            return DiscoverFragment()
        }
    }
    override fun onLayoutId(): Int {
        return R.layout.fragment_discover
    }
}