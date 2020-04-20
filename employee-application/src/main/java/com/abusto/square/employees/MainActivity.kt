package com.abusto.square.employees

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.abusto.square.employees.ui.EmployeeDirectoryFragment

class MainActivity : AppCompatActivity(), LifecycleOwner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)
            ?: run {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container,
                        EmployeeDirectoryFragment.newInstance(),
                        FRAGMENT_TAG)
                    .commit()
            }
    }
    companion object {
        private const val FRAGMENT_TAG = "EmployeeDirectoryFragment"
    }
}