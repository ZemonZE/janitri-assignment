package com.pregnancy.vitals.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AddVitalsDialog(
    onDismiss: () -> Unit,
    onSubmit: (sys: Int, dia: Int, hr: Int, weight: Double, kicks: Int) -> Unit
) {
    var sys by remember { mutableStateOf("") }
    var dia by remember { mutableStateOf("") }
    var hr by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var kicks by remember { mutableStateOf("") }

    val isValid = remember(sys, dia, hr, weight, kicks) {
        sys.toIntOrNull() != null &&
                dia.toIntOrNull() != null &&
                hr.toIntOrNull() != null &&
                weight.toDoubleOrNull() != null &&
                kicks.toIntOrNull() != null
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Vitals") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {

                // Sys / Dia side by side
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = sys,
                        onValueChange = { sys = it.filter { ch -> ch.isDigit() } },
                        label = { Text("Sys BP") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    OutlinedTextField(
                        value = dia,
                        onValueChange = { dia = it.filter { ch -> ch.isDigit() } },
                        label = { Text("Dia BP") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = hr,
                    onValueChange = { hr = it.filter { ch -> ch.isDigit() } },
                    label = { Text("Heart Rate") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    label = { Text("Weight (in kg)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = kicks,
                    onValueChange = { kicks = it.filter { ch -> ch.isDigit() } },
                    label = { Text("Baby Kicks") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSubmit(
                        sys.toInt(),
                        dia.toInt(),
                        hr.toInt(),
                        weight.toDouble(),
                        kicks.toInt()
                    )
                },
                enabled = isValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp)
            ) {
                Text("Submit")
            }
        },
        dismissButton = {}
    )
}
