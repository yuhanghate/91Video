package com.yh.video.pirate.repository.network.exception

import com.orhanobut.logger.Logger
import com.yh.video.pirate.constant.HttpConstant.HTTP_SUCCESS
import com.yh.video.pirate.repository.network.result.base.CaomeiResponse

/**
 * 处理请求层的错误,对可能的已知的错误进行处理
 */
fun handlingExceptions(e: Throwable) {
    when (e) {
        is CancellationException -> {}
        is SocketTimeoutException -> {}
        is JsonParseException -> {}
        else -> {}
    }
}

enum class HttpError(val code: Int, val errorMsg: String?) {
    USER_EXIST(20001, "user does not exist"),
    PARAMS_ERROR(20002, "params is error")
    // ...... more
}


/**
 * 处理响应层的错误
 */
fun handlingApiExceptions(e: HttpError) {
    when (e) {
        HttpError.USER_EXIST -> {}
        HttpError.PARAMS_ERROR -> {}
        // .. more
    }
}


// 简单说明:密封类结合when让可能情况都是已知的,代码维护性更高。
sealed class HttpResponse

data class Success<out T>(val data: T) : HttpResponse()
data class Failure(val error: HttpError) : HttpResponse()




/**
 * 处理HttpResponse
 * @param res
 * @param success 成功
 * @param error 失败
 */
fun <T> CaomeiResponse<*>.catchCode(
    success: (data: T) -> Unit,
    error: ((error: HttpError) -> Unit)? = null
) {
    if (this.code == HTTP_SUCCESS) {
        success.invoke(this.rescont as T)
    } else {
        error?.invoke(HttpError.USER_EXIST) ?: defaultErrorBlock.invoke(HttpError.USER_EXIST)
    }
}


// 默认的处理方案
val defaultErrorBlock: (error: HttpError) -> Unit = { error ->
    Logger.e(error.errorMsg ?: "${error.code}")
}

fun <T : Any> CaomeiResponse<T>.convertHttpRes(): HttpResponse {
    return if (this.code == HTTP_SUCCESS) {
        rescont?.let {
            Success(it)
        } ?: Success(Any())
    } else {
        Failure(HttpError.USER_EXIST)
    }
}

