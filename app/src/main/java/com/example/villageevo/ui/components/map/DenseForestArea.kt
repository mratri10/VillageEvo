package com.example.villageevo.ui.components.map

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.villageevo.R
import com.google.android.material.shape.MaterialShapeDrawable

@Composable
fun DenseForestArea(
    modifier: Modifier = Modifier,
    sum:Int = 0,
    workers: Int = 0
) {
    Box (modifier=modifier.fillMaxSize()){
        FlowRow(
            modifier= Modifier
                .fillMaxSize()
                .padding(top = 5.dp).align(Alignment.Center),

            maxItemsInEachRow =
                if(sum <31)6
                else if(sum<150)15
                else Int.MAX_VALUE,
            itemVerticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                (-12).dp, Alignment.CenterHorizontally
                ),
            verticalArrangement = Arrangement.spacedBy(
                (-12).dp, Alignment.CenterVertically
            ),
        ) {
            for(i in 0 until sum) {
                Image(
                    painter = painterResource(id = R.drawable.tree),
                    contentDescription = "tree_$i",
                    modifier = Modifier
                        .size(20.dp)
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
                painter = painterResource(id = R.drawable.tree),
                contentDescription = "tree",
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
            verticalAlignment = Alignment.CenterVertically) {

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