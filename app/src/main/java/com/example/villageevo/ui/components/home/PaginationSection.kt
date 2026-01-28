package com.example.villageevo.ui.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PaginationSection(
    currentPage: Int,
    maxPage: Int,
    onBack: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Tombol Kembali
        IconButton(
            enabled = currentPage > 1,
            onClick = onBack,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Cyan,
                contentColor = Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.DarkGray
            ),
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Back"
            )
        }

        // Indikator Halaman (misal: 1 / 9)
        Column (horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$currentPage / $maxPage",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White
            )
        }

        // Tombol Lanjut
        IconButton(
            enabled = currentPage < maxPage,
            onClick = onNext,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Cyan,
                contentColor = Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.DarkGray
            ),
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Next"
            )
        }
    }
}