package com.yh.video.pirate.ui.main.fragment

import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseFragment
import com.yh.video.pirate.databinding.FragmentMeBinding
import com.yh.video.pirate.ui.main.viewmodel.MeViewModel

class MeFragment:BaseFragment<FragmentMeBinding, MeViewModel>() {
    companion object{
        fun newInstance(): MeFragment {
            return MeFragment()
        }
    }

    override fun onLayoutId(): Int {
        return R.layout.fragment_me
    }
}