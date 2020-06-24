package com.yh.video.pirate.utils.viewbinding

import android.view.View
import androidx.annotation.RestrictTo
import androidx.viewbinding.ViewBinding

@RestrictTo(RestrictTo.Scope.LIBRARY)
@PublishedApi
internal class ActivityViewBinderUtils<T : ViewBinding>(
    private val viewBindingClass: Class<T>

) {


    private val bindViewMethod  by lazy (LazyThreadSafetyMode.NONE)  {
        viewBindingClass.getMethod("bind", View::class.java)
    }


    fun bind(view : View):T{
        return bindViewMethod(null, view) as T
    }
}
