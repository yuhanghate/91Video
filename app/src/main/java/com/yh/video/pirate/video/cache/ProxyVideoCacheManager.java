package com.yh.video.pirate.video.cache;

import android.content.Context;
import android.os.Environment;

import androidx.core.content.ContextCompat;

import com.danikula.videocache.HttpProxyCacheServer;
import com.danikula.videocache.StorageUtils;

import java.io.File;
import java.io.IOException;

public class ProxyVideoCacheManager {

    /**
     * 最大视频缓存
     */
    private static final long MAX_CACHE_SIZE = 1024 * 1024 * 1024;

    private static HttpProxyCacheServer sharedProxy;

    private ProxyVideoCacheManager() {
    }

    public static HttpProxyCacheServer getProxy(Context context) throws IOException {
        return sharedProxy == null ? (sharedProxy = newProxy(context)) : sharedProxy;
    }

    private static HttpProxyCacheServer newProxy(Context context) throws IOException {

        File[] externalCacheDirs = ContextCompat.getExternalCacheDirs(context);
        return new HttpProxyCacheServer.Builder(context)
                .maxCacheSize(MAX_CACHE_SIZE)       // 3GB for cache
//                .maxCacheFilesCount(200)
                //缓存路径，不设置默认在sd_card/Android/data/[app_package_name]/cache中
                .cacheDirectory(externalCacheDirs[0])
//                .cacheDirectory(Environment.getDownloadCacheDirectory())
//                .cacheDirectory(context.getExternalFilesDir())
                .build();
    }


    /**
     * 删除所有缓存文件
     *
     * @return 返回缓存是否删除成功
     */
    public static boolean clearAllCache(Context context) throws IOException {
        getProxy(context);
        return StorageUtils.deleteFiles(sharedProxy.getCacheRoot());
    }

    /**
     * 删除url对应默认缓存文件
     *
     * @return 返回缓存是否删除成功
     */
    public static boolean clearDefaultCache(Context context, String url) throws IOException {
        getProxy(context);
        File pathTmp = sharedProxy.getTempCacheFile(url);
        File path = sharedProxy.getCacheFile(url);
        return StorageUtils.deleteFile(pathTmp.getAbsolutePath()) &&
                StorageUtils.deleteFile(path.getAbsolutePath());

    }
}