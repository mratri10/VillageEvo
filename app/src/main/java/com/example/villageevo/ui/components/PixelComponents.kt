package com.example.villageevo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PixelPanel(title:String, content:@Composable ColumnScope.() -> Unit){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color(0xFF00FF00))
            .background(Color(0xFF1A1A1A))
            .padding(8.dp)
    ) {
        Text(title, color = Color(0xFF00FF00))
        Spacer(modifier = Modifier.height(6.dp))
        content()
    }
}

@Composable
fun PixelButton(text: String, onClick:()-> Unit){
    Button (
        onClick=onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
        modifier = Modifier.fillMaxWidth().border(2.dp, Color((0xFF00FF00)))
    ){
        Text(text, color= Color(0xFF00FF00))
    }
}

@Composable
fun PixelInputRow(label: String, value: String, onChange:(String)-> Unit){
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(label, color = Color.Green)
        OutlinedTextField(
            value = value,
            onValueChange = {onChange(it.filter { c ->  c.isDigit() })},
            modifier = Modifier.width(120.dp),
            singleLine = true
        )
    }
}