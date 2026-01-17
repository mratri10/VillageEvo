package com.example.villageevo.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.villageevo.domain.city.CityState
import com.example.villageevo.domain.worker.Worker
import com.example.villageevo.domain.worker.WorkerType

@Composable
fun WorkerAssignmentScreen(city: CityState) {
    Column (
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp)
    ) {
        Text("WORKERS", style = MaterialTheme.typography.titleLarge)

        LazyColumn {
            items(city.workers) { worker ->
                WorkerRow(worker)
            }
        }
    }
}

@Composable
fun WorkerRow(worker: Worker) {
    var expanded by remember { mutableStateOf(false) }

    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(worker.id.take(6))

        Spacer(Modifier.width(8.dp))

        Text(worker.type.name)

        Spacer(Modifier.width(8.dp))

        Button (onClick = { expanded = true }) {
            Text("CHANGE")
        }

        DropdownMenu (expanded, onDismissRequest = { expanded = false }) {
            WorkerType.values().forEach {
                DropdownMenuItem(
                    text = { Text(it.name) },
                    onClick = {
                        worker.type = it
                        expanded = false
                    }
                )
            }
        }
    }
}
