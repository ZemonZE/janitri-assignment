package com.pregnancy.vitals.ui.screen

import android.R.attr.padding
import android.app.Dialog
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.pregnancy.vitals.ui.components.AddVitalsDialog
import com.pregnancy.vitals.ui.components.VitalsItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VitalsScreen(
    vm: VitalsViewModel,
    startWithDialog: Boolean = false
){
    val vitals = vm.vitals.collectAsState().value
    var showDialog by remember { mutableStateOf(startWithDialog) }

    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = {
                    Text(
                        text = "Track My Pregnancy",
                        style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                },
                colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                    containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                    titleContentColor = androidx.compose.ui.graphics.Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Text(text = "+")
            }
        }
    ) {
            padding ->
        LazyColumn(contentPadding = padding) {
            items( items=vitals) { item ->
                VitalsItem(item)
            }
        }

        if (showDialog) {
            AddVitalsDialog(
                onDismiss = { showDialog = false },
                onSubmit = { sys, dia, hr, w, kicks ->
                    vm.addVitals(sys, dia, hr, w, kicks)
                    showDialog = false
                }
            )
        }
    }
}
