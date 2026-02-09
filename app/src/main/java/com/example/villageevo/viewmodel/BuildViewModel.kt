package com.example.villageevo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villageevo.domain.building.BuildDataEntity
import com.example.villageevo.domain.building.MapBuildEntity
import com.example.villageevo.domain.building.SourceEntity
import com.example.villageevo.repository.BuildRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BuildViewModel(private val repositoryBuild: BuildRepository) : ViewModel() {
    fun insertBuild(
        mapBuild: MapBuildEntity,
        buildData: BuildDataEntity
    ){
        try {
            viewModelScope.launch ( Dispatchers.IO ){
                repositoryBuild.saveBuildData(mapBuild, buildData)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun insertBuildEvo(
        source: SourceEntity
    ){
        try{
            viewModelScope.launch (Dispatchers.IO){
                repositoryBuild.saveSource(source)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}