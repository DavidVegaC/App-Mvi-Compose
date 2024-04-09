package com.davega.ibkapp

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.davega.data.auth.utils.SessionTimer
import com.davega.ibkapp.ui.session_expired.SessionExpiredDialog
import com.davega.ui.base.BaseActivity
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {

    private val sessionTimer: SessionTimer by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        lifecycleScope.launchWhenStarted {
            sessionTimer().collectLatest {
                navController.setGraph(R.navigation.main_graph)
                SessionExpiredDialog().show()
            }
        }
    }

}