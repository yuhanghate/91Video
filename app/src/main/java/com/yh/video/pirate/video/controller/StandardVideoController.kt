package com.yh.video.pirate.video.controller

import android.content.Context
import android.content.pm.ActivityInfo
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.AttrRes
import com.dueeeke.videoplayer.controller.GestureVideoController
import com.dueeeke.videoplayer.player.VideoView
import com.dueeeke.videoplayer.util.PlayerUtils
import com.yh.video.pirate.R
import com.yh.video.pirate.databinding.LayoutStandardControllerBinding
import com.yh.video.pirate.video.controller.component.*

/**
 * 直播/点播控制器
 * 注意：此控制器仅做一个参考，如果想定制ui，你可以直接继承GestureVideoController或者BaseVideoController实现
 * 你自己的控制器
 * Created by dueeeke on 2017/4/7.
 */
class StandardVideoController @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : GestureVideoController(context, attrs, defStyleAttr), View.OnClickListener {
//    protected var mLockButton: ImageView? = null
//    protected var mLoadingProgress: ProgressBar? = null

    lateinit var mBinding:LayoutStandardControllerBinding
    override fun getLayoutId(): Int {
        return R.layout.layout_standard_controller
    }

    override fun initView() {
        super.initView()
        mBinding = LayoutStandardControllerBinding.bind(this)
        mBinding.lock.setOnClickListener(this)
    }

    /**
     * 快速添加各个组件
     * @param title  标题
     */
    fun addDefaultControlComponent(title: String?) {
        val completeView = CompleteView(context)
        val errorView = ErrorView(context)
        val prepareView = PrepareView(context)
        prepareView.setClickStart()
        val titleView = TitleView(context)
        titleView.setTitle(title)
        addControlComponent(completeView, errorView, prepareView, titleView)
        addControlComponent(VodControlView(context))
        addControlComponent(GestureView(context))
        setCanChangePosition(false)
    }

    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.lock) {
            mControlWrapper.toggleLockState()
        }
    }

    override fun onLockStateChanged(isLocked: Boolean) {
        if (isLocked) {
            mBinding.lock.isSelected = true
            Toast.makeText(context, "已锁定", Toast.LENGTH_SHORT).show()
        } else {
            mBinding.lock.isSelected = false
            Toast.makeText(context, "已解锁", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onVisibilityChanged(
        isVisible: Boolean,
        anim: Animation
    ) {
        if (mControlWrapper.isFullScreen) {
            if (isVisible) {
                if (mBinding.lock.visibility == View.GONE) {
                    mBinding.lock.visibility = View.VISIBLE
                    if (anim != null) {
                        mBinding.lock.startAnimation(anim)
                    }
                }
            } else {
                mBinding.lock.visibility = View.GONE
                if (anim != null) {
                    mBinding.lock.startAnimation(anim)
                }
            }
        }
    }

    override fun onPlayerStateChanged(playerState: Int) {
        super.onPlayerStateChanged(playerState)
        when (playerState) {
            VideoView.PLAYER_NORMAL -> {
                layoutParams = LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                mBinding.lock.visibility = View.GONE
            }
            VideoView.PLAYER_FULL_SCREEN -> if (isShowing) {
                mBinding.lock.visibility = View.VISIBLE
            } else {
                mBinding.lock.visibility = View.GONE
            }
        }
        if (mActivity != null && hasCutout()) {
            val orientation = mActivity!!.requestedOrientation
            val dp24 = PlayerUtils.dp2px(context, 24f)
            val cutoutHeight = cutoutHeight
            if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                val lblp =
                    mBinding.lock.layoutParams as LayoutParams
                lblp.setMargins(dp24, 0, dp24, 0)
            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                val layoutParams =
                    mBinding.lock.layoutParams as LayoutParams
                layoutParams.setMargins(dp24 + cutoutHeight, 0, dp24 + cutoutHeight, 0)
            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                val layoutParams =
                    mBinding.lock.layoutParams as LayoutParams
                layoutParams.setMargins(dp24, 0, dp24, 0)
            }
        }
    }

    override fun onPlayStateChanged(playState: Int) {
        super.onPlayStateChanged(playState)
        when (playState) {
            VideoView.STATE_IDLE -> {
                mBinding.lock.isSelected = false
                mBinding.loading.visibility = View.GONE
            }
            VideoView.STATE_PLAYING, VideoView.STATE_PAUSED, VideoView.STATE_PREPARED, VideoView.STATE_ERROR, VideoView.STATE_BUFFERED -> mBinding.loading.visibility =
                View.GONE
            VideoView.STATE_PREPARING, VideoView.STATE_BUFFERING -> mBinding.loading.visibility =
                View.VISIBLE
            VideoView.STATE_PLAYBACK_COMPLETED -> {
                mBinding.loading.visibility = View.GONE
                mBinding.lock.visibility = View.GONE
                mBinding.lock.isSelected = false
            }
        }
    }

    override fun onBackPressed(): Boolean {
        if (isLocked) {
            show()
            Toast.makeText(context, "请先解锁屏幕！", Toast.LENGTH_SHORT).show()
            return true
        }
        return if (mControlWrapper.isFullScreen) {
            stopFullScreen()
        } else super.onBackPressed()
    }
}