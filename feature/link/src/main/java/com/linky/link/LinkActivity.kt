package com.linky.link

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.linky.design_system.animation.slideOut
import com.linky.design_system.ui.theme.LinkyLinkTheme
import com.linky.common.activity_stack_counter.ActivityStackObserver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LinkActivity : ComponentActivity() {

    @Inject
    lateinit var activityStackObserver: com.linky.common.activity_stack_counter.ActivityStackObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navHostController = rememberAnimatedNavController()
            val scaffoldState = rememberScaffoldState()

            LinkyLinkTheme {
                Scaffold(scaffoldState = scaffoldState) { paddingValues ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        LinkNavHost(
                            navHostController = navHostController,
                            scaffoldState = scaffoldState,
                            activityStackObserver = activityStackObserver,
                            onFinishAndResult = {
                                setResult(RESULT_OK, Intent().putExtras(it))
                                finish()
                            },
                            onBack = {
                                if (!navHostController.popBackStack()) {
                                    setResult(RESULT_CANCELED)
                                    finish()
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    override fun onPause() {
        if (isFinishing) {
            slideOut()
        }
        super.onPause()
    }

}