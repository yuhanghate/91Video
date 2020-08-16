package com.yh.video.pirate.exception

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.yh.video.pirate.utils.AppManagerUtils
import com.yh.video.pirate.utils.application
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 全局异常捕获
 * Created by zly on 2016/7/3.
 */
class CrashHandler private constructor() : Thread.UncaughtExceptionHandler {
    /**
     * 系统默认UncaughtExceptionHandler
     */
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null

    /**
     * context
     */
    private var mContext: Context? = null

    /**
     * 存储异常和参数信息
     */
    private val paramsMap: MutableMap<String, String> =
        HashMap()

    /**
     * 格式化时间
     */
    private val format =
        SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
    private val TAG = this.javaClass.simpleName
    fun init(context: Context?) {
        mContext = context
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * uncaughtException 回调函数
     */
    override fun uncaughtException(
        thread: Thread,
        ex: Throwable
    ) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler!!.uncaughtException(thread, ex)
        } else {
            try {
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
                Log.e(TAG, "error : ", e)
            }
            AppManagerUtils.getAppManager().AppExit(mContext)
        }
    }

    /**
     * 收集错误信息.发送到服务器
     * @return 处理了该异常返回true,否则false
     */
    private fun handleException(ex: Throwable?): Boolean {
        if (ex == null) {
            return false
        }
        collectDeviceInfo(mContext)
        addCustomInfo()
        object : Thread() {
            override fun run() {
                Looper.prepare()
                Toast.makeText(mContext, "程序开小差了呢..", Toast.LENGTH_SHORT).show()
                Looper.loop()
            }
        }.start()
        saveCrashInfo2File(ex)
        return true
    }

    /**
     * 收集设备参数信息
     * @param ctx
     */
    fun collectDeviceInfo(ctx: Context?) {
        try {
            val pm = ctx!!.packageManager
            val pi =
                pm.getPackageInfo(ctx.packageName, PackageManager.GET_ACTIVITIES)
            if (pi != null) {
                val versionName =
                    if (pi.versionName == null) "null" else pi.versionName
                val versionCode = pi.versionCode.toString() + ""
                paramsMap["versionName"] = versionName
                paramsMap["versionCode"] = versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "an error occured when collect package info", e)
        }
        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                paramsMap[field.name] = field[null].toString()
            } catch (e: Exception) {
                Log.e(TAG, "an error occured when collect crash info", e)
            }
        }
    }

    /**
     * 添加自定义参数
     */
    private fun addCustomInfo() {}

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return  返回文件名称,便于将文件传送到服务器
     */
    private fun saveCrashInfo2File(ex: Throwable): String? {
        val sb = StringBuffer()
        for ((key, value) in paramsMap) {
            sb.append("$key=$value\n")
        }
        val writer: Writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        sb.append(result)
        try {
            val timestamp = System.currentTimeMillis()
            val time = format.format(Date())
            val fileName = "crash-$time-$timestamp.log"
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                val path = Environment.getExternalStorageDirectory()
                    .absolutePath + "/crash/"
                val dir = File(path)
                copyPath(dir.absolutePath + File.separator + fileName)
                Toast.makeText(application,"异常信息已保存系统剪贴板", Toast.LENGTH_LONG).show()
                if (!dir.exists()) {
                    dir.mkdirs()
                }
                val fos = FileOutputStream(path + fileName)
                fos.write(sb.toString().toByteArray())
                fos.close()
            }
            return fileName
        } catch (e: Exception) {
            Log.e(TAG, "an error occured while writing file...", e)
        }
        return null
    }

    private fun copyPath(path: String) {
        //获取剪贴板管理器：
        val cm = application.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // 创建普通字符型ClipData
        val mClipData = ClipData.newPlainText("Label", path);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

    companion object {
        private var mInstance: CrashHandler? = null

        /**
         * 获取CrashHandler实例
         */
        @get:Synchronized
        val instance: CrashHandler?
            get() {
                if (null == mInstance) {
                    mInstance = CrashHandler()
                }
                return mInstance
            }
    }
}