//package com.yh.video.pirate.video.videoview;
//
//import android.content.Context;
//import android.util.AttributeSet;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.dueeeke.videoplayer.exo.ExoMediaSourceHelper;
//import com.dueeeke.videoplayer.player.PlayerFactory;
//import com.dueeeke.videoplayer.player.VideoView;
//import com.google.android.exoplayer2.LoadControl;
//import com.google.android.exoplayer2.RenderersFactory;
//import com.google.android.exoplayer2.database.ExoDatabaseProvider;
//import com.google.android.exoplayer2.source.MediaSource;
//import com.google.android.exoplayer2.trackselection.TrackSelector;
//import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
//import com.google.android.exoplayer2.upstream.cache.SimpleCache;
//
//import java.io.File;
//import java.util.Map;
//
//public class ExoVideoView extends VideoView<CustomExoMediaPlayer> {
//
//    private MediaSource mMediaSource;
//
//    private boolean mIsCacheEnabled;
//
//    private LoadControl mLoadControl;
//    private RenderersFactory mRenderersFactory;
//    private TrackSelector mTrackSelector;
//
//    private ExoMediaSourceHelper mHelper;
//
//    public ExoVideoView(Context context) {
//        super(context);
//    }
//
//    public ExoVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public ExoVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    {
//        //由于传递了泛型，必须将CustomExoMediaPlayer设置进来，否者报错
//        setPlayerFactory(new PlayerFactory<CustomExoMediaPlayer>() {
//            @Override
//            public CustomExoMediaPlayer createPlayer(Context context) {
//                return new CustomExoMediaPlayer(context);
//            }
//        });
//        mHelper = ExoMediaSourceHelper.getInstance(getContext());
////        mHelper.setCache(new SimpleCache(
////                new File(getContext().getExternalCacheDir(), "exo-video-cache"),//缓存目录
////                new LeastRecentlyUsedCacheEvictor(1024 * 1024 * 1024 * 10L),//缓存大小，默认10G，使用LRU算法实现
////                new ExoDatabaseProvider(getContext())));
//    }
//
//    @Override
//    protected void setInitOptions() {
//        super.setInitOptions();
//        mMediaPlayer.setLoadControl(mLoadControl);
//        mMediaPlayer.setRenderersFactory(mRenderersFactory);
//        mMediaPlayer.setTrackSelector(mTrackSelector);
//    }
//
//    @Override
//    protected boolean prepareDataSource() {
//        if (mMediaSource != null) {
//            mMediaPlayer.setDataSource(mMediaSource);
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 设置ExoPlayer的MediaSource
//     */
//    public void setMediaSource(MediaSource mediaSource) {
//        mMediaSource = mediaSource;
//    }
//
//    @Override
//    public void setUrl(String url, Map<String, String> headers) {
//        mMediaSource = mHelper.getMediaSource(url, headers, mIsCacheEnabled);
//
//    }
//
//    /**
//     * 是否打开缓存
//     */
//    public void setCacheEnabled(boolean isEnabled) {
//        mIsCacheEnabled = isEnabled;
//    }
//
//    public void setLoadControl(LoadControl loadControl) {
//        mLoadControl = loadControl;
//    }
//
//    public void setRenderersFactory(RenderersFactory renderersFactory) {
//        mRenderersFactory = renderersFactory;
//    }
//
//    public void setTrackSelector(TrackSelector trackSelector) {
//        mTrackSelector = trackSelector;
//    }
//}
