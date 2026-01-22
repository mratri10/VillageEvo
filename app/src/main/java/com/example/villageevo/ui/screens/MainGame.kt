package com.example.villageevo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.villageevo.domain.parameter.ParameterRepository
import com.example.villageevo.viewmodel.GameViewModel

@Composable
fun MainGame(modifier: Modifier, viewModel: GameViewModel) {
    val parameterGames = ParameterRepository.getParameters()
    Column(modifier = modifier
        .fillMaxHeight()
        .background(Color.Black)){
        Text("Konoha", modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                textAlign = TextAlign.Center,
                color = Color.White
            )
        )
        Text("1800M", modifier = Modifier
            .fillMaxWidth()
//            .offset(y= (-15).dp)
            .padding(vertical = 10.dp),
            style = MaterialTheme.typography.labelSmall.copy(
                textAlign = TextAlign.Center,
                color = Color.White
            )
        )
        Box(
            modifier = Modifier.weight(1f)
        ){
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(parameterGames){
                        param ->
                    Row(modifier.border(width = 1.dp, color = Color.White),
                        verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(param.image),
                            contentDescription = param.name,
                            modifier = Modifier
                                .padding(10.dp)
                                .size(20.dp)
                        )
                        Text(
                            "= ${param.value}",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color.White)
                        )
                    }
                }
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
        ){
            Button(
                onClick = {viewModel.openHome()},
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text("TURN")
            }
        }
    }

}