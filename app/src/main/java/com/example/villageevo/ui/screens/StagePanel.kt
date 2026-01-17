package com.example.villageevo.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.villageevo.domain.stage.StageManager

@Composable
fun  StagePanel (stageManager: StageManager){
    val cfg= stageManager.getCurrentStageConfig()
    val state = stageManager.state

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Text("SEASON ${state.currentSeason}")
        Text("STAGE ${state.currentStage}")
        Text("Turn: ${state.turnUsed}/${cfg.maxTurns}")
        Text("Target Gold: ${cfg.targetGold}")
        Text("Target Food: ${cfg.targetFood}")
        Text("Target Lumber: ${cfg.targetLumber}")

        if(state.isFailed){
            Text("FAILED", color = Color.Red)
            Button(onClick = {stageManager.retryStage()}) {
                Text("RETRY")
            }
        }

        if(state.isCompleted){
            Text("COMPLETED", color = Color.Green)
            Button(onClick = {stageManager.nextStageOrSeason()}) {
                Text("NEXT")
            }
        }
    }
}