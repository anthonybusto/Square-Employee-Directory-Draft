package com.abusto.square.employees.ui

import android.os.Bundle
import com.abusto.square.base_arch.ViewEffect

data class NavigationViewEffect(private val destination: Class<*>,
                                private val args: Bundle? = null) : ViewEffect<Pair<Class<*>, Bundle?>>() {

    override val value: Pair<Class<*>, Bundle?> = destination to args

}