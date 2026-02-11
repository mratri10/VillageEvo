package com.example.villageevo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villageevo.domain.map.MapDataEntity
import com.example.villageevo.domain.npc.NpcAbilityEntity
import com.example.villageevo.domain.npc.NpcAssignEntity
import com.example.villageevo.domain.npc.NpcEntity
import com.example.villageevo.domain.npc.NpcMap
import com.example.villageevo.domain.npc.TotalNpcAssign
import com.example.villageevo.repository.NpcRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NpcViewModel(private val repository: NpcRepository) : ViewModel() {

    private val _getNpcAbilityList = MutableStateFlow<List<NpcMap>>(emptyList())
    val getNpcAbilityList = _getNpcAbilityList.asStateFlow()

    private val _getTotalNpc = MutableStateFlow(emptyList<TotalNpcAssign>())
    val getTotalNpc = _getTotalNpc.asStateFlow()

    fun saveNpcFirst() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                for (i in 1..5) {
                    val npc = NpcEntity(id = i, year = 0)
                    val npcAbility = NpcAbilityEntity(idNpc = i, idAbility = 0, level = 0, id = i)
                    repository.saveNpcWithAbility(npc, npcAbility)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadNpcList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _getTotalNpc.value = repository.getNpcCount()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun loadNpcAbilityList(currentPage: Int, pageSize: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _getNpcAbilityList.value = repository.getNpcAbility(currentPage, pageSize)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveNpcAssign(listNpcId: List<Int>, idMapUser: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val listNpcAssign = listNpcId.map { NpcAssignEntity(idNpc = it, idMapUser = idMapUser) }
                repository.saveNpcAssign(listNpcAssign)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateAssignToNol(){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateAssignToNol()
        }
    }
}