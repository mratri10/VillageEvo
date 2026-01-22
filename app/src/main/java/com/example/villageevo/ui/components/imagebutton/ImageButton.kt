package com.example.villageevo.ui.components.imagebutton

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ImageButton(
    painter: Painter,
    modifier: Modifier = Modifier,
    onClick:()->Unit,
    contentDescription: String?=null,
    backgroundPainter: Painter?=null,
    backgroundColor: Color=Color(0xFFDCDDE1),
    borderColor: Color=Color(0xFF2F3640),
    borderSize:Int=3,
    borderRounded:Int=0
){
    var isPressed by remember { mutableStateOf(false) }
    val buttonShape = RoundedCornerShape(borderRounded.dp)
    val scale by animateFloatAsState(
        targetValue = if (isPressed)0.9f else 1f,
        label = "ButtonScale"
    )

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .scale(scale)
            .clip(buttonShape)
            .background(backgroundColor, buttonShape)
            .border(width = borderSize.dp, color=borderColor, shape = buttonShape)
            .pointerInput(Unit){
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        val startTime = System.currentTimeMillis()
                        try{
                            awaitRelease()
                        }finally {
                            val elapsed = System.currentTimeMillis() - startTime
                            if(elapsed<100) delay(100-elapsed)
                            isPressed = false
                        }
                    },
                    onTap = {onClick()}
                )
            },
        contentAlignment = Alignment.Center
    ){
        if(backgroundPainter!=null){
            Image(
                painter = backgroundPainter,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
            )
        }
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier= Modifier.fillMaxSize(0.7f)
        )
    }
}