package com.pregnancy.vitals

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.pregnancy.vitals.data.local.VitalsDatabase
import com.pregnancy.vitals.data.repository.VitalsRepositoryImpl
import com.pregnancy.vitals.di.AppModule
import com.pregnancy.vitals.ui.screen.VitalsScreen
import com.pregnancy.vitals.ui.screen.VitalsViewModel
import com.pregnancy.vitals.ui.screen.VitalsViewModelFactory
import com.pregnancy.vitals.ui.theme.PregnancyVitalsTrackerTheme

class MainActivity : ComponentActivity() {
    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            // nothing to do here
        }

    private val db by lazy{
        Room.databaseBuilder(
            applicationContext,
            VitalsDatabase::class.java,
            "vitals.db").build()
    }
    private val repo by lazy {
        VitalsRepositoryImpl(db.vitalsDao())
    }

    private val factory by lazy {
        VitalsViewModelFactory(repo)
    }
    private val vm : VitalsViewModel by viewModels{factory}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val openDialog = intent?.getBooleanExtra("open_add_dialog", false) == true
        
        // Request notification permission on Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
        
        // Schedule reminder (testMode = true for 1 minute testing, false for 5 hours production)
        AppModule.scheduleVitalsReminder(this, testMode = false)
        
        setContent {
            PregnancyVitalsTrackerTheme {
                VitalsScreen(vm = vm , startWithDialog = openDialog)
            }
        }
    }
}
