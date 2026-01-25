package com.example.villageevo.ui.components.map

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.villageevo.R

@Composable
fun WildArea(
    modifier: Modifier = Modifier,
    sum:Int = 0,
    workers: Int = 0
) {
    val animalImages = listOf(
        R.drawable.rabbit,
        R.drawable.boar,
        R.drawable.deer
    )
    Box{
        FlowRow(
            modifier.fillMaxSize().padding(top = 5.dp).align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
            maxItemsInEachRow =
                if(sum <31)6
                else if(sum<81)10
                else Int.MAX_VALUE
        ) {
            for(i in 0 until sum){
                val selectedImage = remember { animalImages.random() }
                Image(
                    painter = painterResource(id = selectedImage),
                    contentDescription = "rabbit",
                    modifier = Modifier
                        .size(10.dp)
                        .offset(
                            y = ((-5).dp),
                            x = 0.dp
                        ),
                    contentScale = ContentScale.Fit
                )
            }
        }
        Row(
            modifier=
                Modifier
                    .align(Alignment.BottomStart)
                    .background(color = Color.Cyan)
                    .padding(5.dp)
                    .align(Alignment.CenterStart)
                    .clip(RoundedCornerShape(10.dp)),
            verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.deer),
                contentDescription = "deer",
                modifier = Modifier
                    .size(15.dp)
            )
            Text("$sum",style = MaterialTheme.typography.labelSmall)
        }
        Row(
            modifier=
                Modifier
                    .align(Alignment.BottomEnd)
                    .background(color = Color.Cyan)
                    .padding(5.dp)
                    .align(Alignment.CenterStart)
                    .clip(RoundedCornerShape(10.dp)),
            verticalAlignment = Alignment.CenterVertically,
            ) {
            Text("$workers",style = MaterialTheme.typography.labelSmall)
            Image(
                painter = painterResource(id = R.drawable.farmer),
                contentDescription = "tree",
                modifier = Modifier
                    .size(15.dp)
            )
        }
    }

}