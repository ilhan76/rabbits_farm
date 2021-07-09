package com.kudashov.rabbits_farm.utilits

import android.content.res.Resources
import androidx.appcompat.content.res.AppCompatResources

class RH {
    companion object{
        fun string(id: Int): String = APP_ACTIVITY.getString(id)
        fun color(id: Int) = Resources.getSystem().getColor(id)
        fun drawable(id: Int) = AppCompatResources.getDrawable(APP_ACTIVITY, id)
    }
}