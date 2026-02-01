package com.example.villageevo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villageevo.domain.soldier.NpcAbilityEntity
import com.example.villageevo.domain.soldier.NpcAssignEntity
import com.example.villageevo.domain.soldier.NpcEntity
import com.example.villageevo.repository.NpcRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NpcViewModel(private val repository: NpcRepository): ViewModel(){

    private val _getNpcList = MutableStateFlow(emptyList<NpcEntity>())
    val getNpcList = _getNpcList.asStateFlow()

    fun saveNpcFirst(){
        try{
            viewModelScope.launch (Dispatchers.IO){
                for(i in 1..5){
                    val npc = NpcEntity(i,0)
                    val npcAbility = NpcAbilityEntity(i,0,0,0)
                    repository.saveNpcWithAbility(npc, npcAbility)
                }
            }
        }catch(e: Exception){
            e.printStackTrace()
        }
    }

    fun dataNpcList(){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                _getNpcList.value = repository.getNpc()
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun saveNpcAssign(listNpcId:List<Int>, idMapUser:Int){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                for(id in listNpcId){
                    val npcAssign = NpcAssignEntity(0,id,idMapUser)
                    repository.saveNpcAssign(npcAssign)
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}