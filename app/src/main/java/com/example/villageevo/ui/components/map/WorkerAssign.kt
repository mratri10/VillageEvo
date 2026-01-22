package com.example.villageevo.ui.components.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.villageevo.R

@Composable
fun WorkerAssign(modifier: Modifier,workerSize: Dp, workers: Int) {
    Box(modifier = modifier.height(workerSize)){
        for(worker in 0 until workers){
            val yFarmer1 = 0.dp
            val yFarmer2 = yFarmer1-workerSize
            if(worker>23){
                Image(
                    painter = painterResource(id = R.drawable.farmer),
                    contentDescription = "farmer_$worker",
                    modifier = Modifier
                        .size(20.dp*0.8f)
                        .offset(
                            y = yFarmer2,
                            x = (worker-24).dp*7f
                        )
                )
            }else{
                Image(
                    painter = painterResource(id = R.drawable.farmer),
                    contentDescription = "farmer_$worker",
                    modifier = Modifier
                        .size(20.dp*0.8f)
                        .offset(
                            y = yFarmer1,
                            x = worker.dp*7f
                        )
                )
            }
        }
    }
}