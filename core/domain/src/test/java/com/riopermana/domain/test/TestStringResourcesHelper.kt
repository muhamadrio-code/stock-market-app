package com.riopermana.domain.test

import com.riopermana.common.helper.ResourcesHelper

class TestStringResourcesHelper : ResourcesHelper<String> {
    override fun fromResId(resId: Int): String {
        return resId.toString()
    }
}