package com.riopermana.common.helper

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringResHelper @Inject constructor(
    @ApplicationContext private val context: Context,
) : ResourcesHelper<String> {
    override fun fromResId(resId: Int): String {
        return context.getString(resId)
    }
}