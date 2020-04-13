package com.abusto.square.employees.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.abusto.square.base_arch.ViewEffect

data class NavigationViewEffect(private val destination: Class<*>,
                                private val args: Bundle? = null) : ViewEffect<Pair<Class<*>, Bundle?>>() {

    override val value: Pair<Class<*>, Bundle?> = destination to args

    fun execute(context: Context) = consume { ( destination, bundle) ->
        context.startActivity(Intent(context, destination), bundle)
    }

}