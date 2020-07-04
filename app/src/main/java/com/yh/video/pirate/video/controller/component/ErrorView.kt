package com.yh.video.pirate.video.controller.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.animation.Animation
import android.widget.LinearLayout
import com.dueeeke.videoplayer.controller.ControlWrapper
import com.dueeeke.videoplayer.controller.IControlComponent
import com.dueeeke.videoplayer.player.VideoView
import com.yh.video.pirate.R
import com.yh.video.pirate.databinding.LayoutErrorBinding
import com.yh.video.pirate.databinding.LayoutVideoErrorBinding

/**
 * 播放出错提示界面
 * Created by dueeeke on 2017/4/13.
 */
class ErrorView : LinearLayout, IControlComponent {
    private var mDownX = 0f
    private var mDownY = 0f
    private var mControlWrapper: ControlWrapper? = null

    lateinit var mBinding : LayoutVideoErrorBinding

    @JvmOverloads
    constructor(
        context: Context?,
        attrs: AttributeSet? = null
    ) : super(context, attrs) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    override fun attach(controlWrapper: ControlWrapper) {
        mControlWrapper = controlWrapper
    }

    override fun getView(): View {
        return this
    }

    override fun onVisibilityChanged(
        isVisible: Boolean,
        anim: Animation
    ) {
    }

    override fun onPlayStateChanged(playState: Int) {
        if (playState == VideoView.STATE_ERROR) {
            bringToFront()
            visibility = View.VISIBLE
        } else if (playState == VideoView.STATE_IDLE) {
            visibility = View.GONE
        }
    }

    override fun onPlayerStateChanged(playerState: Int) {}
    override fun setProgress(duration: Int, position: Int) {}
    override fun onLockStateChanged(isLock: Boolean) {}
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mDownX = ev.x
                mDownY = ev.y
                // True if the child does not want the parent to intercept touch events.
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val absDeltaX = Math.abs(ev.x - mDownX)
                val absDeltaY = Math.abs(ev.y - mDownY)
                if (absDeltaX > ViewConfiguration.get(context).scaledTouchSlop ||
                    absDeltaY > ViewConfiguration.get(context).scaledTouchSlop
                ) {
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    init {
        visibility = View.GONE
        mBinding = LayoutVideoErrorBinding.inflate(LayoutInflater.from(context), this, true)
        mBinding.statusBtn.setOnClickListener {
            visibility = View.GONE
            mControlWrapper!!.replay(false)
        }
        isClickable = true
    }
}