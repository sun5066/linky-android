package com.linky.android

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.linky.common.activity_stack_counter.ActivityStackObserver
import com.linky.process_lifecycle.ProcessLifecycleObserver
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class LinkyApplication : Application() {

    @Inject
    lateinit var processLifecycleObserver: ProcessLifecycleObserver

    @Inject
    lateinit var activityStackObserver: ActivityStackObserver

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(processLifecycleObserver.lifecycleObserver)
        registerActivityLifecycleCallbacks(activityStackObserver)
    }

}