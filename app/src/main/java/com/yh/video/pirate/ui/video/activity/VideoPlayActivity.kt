package com.yh.video.pirate.ui.video.activity

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.dueeeke.videoplayer.player.VideoView
import com.gyf.immersionbar.ImmersionBar
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseActivity
import com.yh.video.pirate.databinding.ActivityVideoPlayBinding
import com.yh.video.pirate.databinding.LayoutVideoLabelBinding
import com.yh.video.pirate.repository.network.exception.catchCode
import com.yh.video.pirate.repository.network.result.Label
import com.yh.video.pirate.repository.network.result.VideoPlay
import com.yh.video.pirate.ui.main.viewholder.intChange2Str
import com.yh.video.pirate.ui.video.viewmodel.VideoPlayViewModel
import com.yh.video.pirate.utils.loadImage
import com.yh.video.pirate.video.cache.ProgressManagerImpl
import com.yh.video.pirate.video.controller.StandardVideoController
import com.yh.video.pirate.video.controller.component.*
import kotlinx.android.synthetic.main.activity_video_play.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * 视频播放页面
 */
class VideoPlayActivity : BaseActivity<ActivityVideoPlayBinding, VideoPlayViewModel>() {
    private val THUMB =
        "https://cms-bucket.nosdn.127.net/eb411c2810f04ffa8aaafc42052b233820180418095416.jpeg"

    lateinit var controller: StandardVideoController

    lateinit var mConverView: ImageView
    lateinit var mTitleView: TextView

    companion object {
        private const val VIDEO_ID = "VIDEO_ID"
        fun start(context: Context, videoId: Long) {
            val intent = Intent(context, VideoPlayActivity::class.java)
            intent.putExtra(VIDEO_ID, videoId)
            context.startActivity(intent)
        }
    }

    private fun getVideoId() = intent.getLongExtra(VIDEO_ID, 0)

    override fun onLayoutId(): Int {
        return R.layout.activity_video_play
    }


    private val mOnStateChangeListener = object : VideoView.SimpleOnStateChangeListener() {
        override fun onPlayerStateChanged(playerState: Int) {
            super.onPlayerStateChanged(playerState)
            when (playerState) {
                VideoView.PLAYER_NORMAL -> {//小屏
                }
                VideoView.PLAYER_FULL_SCREEN -> {//全屏
                }
            }
        }
    }

    override fun initStatusTool() {
        ImmersionBar.with(this)
            .statusBarColor(android.R.color.transparent)     //状态栏颜色，不写默认透明色
            .navigationBarColor(onNavigationBarColor()) //导航栏颜色，不写默认黑色
            .init()
        ImmersionBar.hideStatusBar(window)
    }

    override fun initView() {
        super.initView()

        controller = StandardVideoController(this)
        //根据屏幕方向自动进入/退出全屏
        //根据屏幕方向自动进入/退出全屏
        controller.setEnableOrientation(true)


        val prepareView = PrepareView(this@VideoPlayActivity) //准备播放界面
        mConverView = prepareView.findViewById(R.id.thumb) //封面图

        controller.addControlComponent(prepareView)

        controller.addControlComponent(CompleteView(this)) //自动完成播放界面


        controller.addControlComponent(ErrorView(this)) //错误界面


        val titleView = TitleView(this) //标题栏
        mTitleView = titleView.findViewById(R.id.title)

        controller.addControlComponent(titleView)

        //根据是否为直播设置不同的底部控制条

        val vodControlView = VodControlView(this) //点播控制条
        //是否显示底部进度条。默认显示
//                vodControlView.showBottomProgress(false);
        controller.addControlComponent(vodControlView)

        val gestureControlView = GestureView(context = this) //滑动控制视图

        controller.addControlComponent(gestureControlView)
        //根据是否为直播决定是否需要滑动调节进度
        controller.setCanChangePosition(true)


        //竖屏也开启手势操作，默认关闭
        controller.setEnableInNormal(true);


        //如果你不想要UI，不要设置控制器即可
        mBinding.videoView.setVideoController(controller)


        //保存播放进度
        mBinding.videoView.setProgressManager(ProgressManagerImpl())
        //播放状态监听
        mBinding.videoView.addOnStateChangeListener(mOnStateChangeListener)

        //临时切换播放核心，如需全局请通过VideoConfig配置，详见MyApplication
        //使用IjkPlayer解码
//            mBinding.videoView.setPlayerFactory(IjkPlayerFactory.create());
        //使用ExoPlayer解码
//            mBinding.videoView.setPlayerFactory(ExoMediaPlayerFactory.create());
        //使用MediaPlayer解码
//        mBinding.videoView.setPlayerFactory(AndroidMediaPlayerFactory.create());


        mBinding.videoView.setCacheEnabled(true)
    }

    override fun initData() {
        super.initData()
        lifecycleScope.launchWhenCreated {
            mViewModel.getVideoPlay(getVideoId())
                .onStart { mBinding.stateLayout.showLoading() }
                .catch { mBinding.stateLayout.showError() }
                .onCompletion { mBinding.stateLayout.showContent() }
                .collect {
                    it.catchCode<VideoPlay>(
                        success = {
                            startPlay(it)
                            initMovieInfoView(it)
                        },
                        error = {
                            mBinding.stateLayout.showError()
                        }
                    )
                }

        }
    }

    override fun onClick() {
        super.onClick()
        mBinding.stateLayout.setRetryListener {
            initData()
        }
    }

    override fun onResume() {
        super.onResume()
        mBinding.videoView.resume()
    }

    override fun onPause() {
        super.onPause()
        mBinding.videoView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.videoView.release()
    }

    override fun onBackPressedSupport() {
        if (!videoView.onBackPressed()) {
            super.onBackPressedSupport()
        }
    }

    /**
     * 播放视频
     */
    private fun startPlay(data:VideoPlay) {
        mConverView.loadImage(this@VideoPlayActivity, data.coverpath)
        mTitleView.text = data.title
        mBinding.videoView.setUrl(data.videopath)
        mBinding.videoView.start()

    }

    /**
     * 加载影片信息
     */
    private fun initMovieInfoView(data:VideoPlay) {
//        mBinding.nameTv.isSelected = true
        mBinding.nameTv.text = data.title
        mBinding.synopsisTv.text = data.introduction
        mBinding.typeValueTv.text = data.authername
        mBinding.createTimeValueTv.text = data.created_at?.split(" ")?.get(0)
        mBinding.playAmountValueTv.text = data.pageviews?.intChange2Str(8089)

        initLabelView(data.labls)
        Handler(Looper.getMainLooper()).postDelayed({
            mBinding.nameTv.isSelected = true
        },1000)
    }

    /**
     * 加载标签
     */
    private fun initLabelView(list:List<Label>?) {
        mBinding.flexboxLayout.removeAllViews()
        list?.forEach {
            val inflate = LayoutVideoLabelBinding.inflate(layoutInflater, null, false)
            inflate.contentTv.text = it.name
            mBinding.flexboxLayout.addView(inflate.root)
        }


    }
}