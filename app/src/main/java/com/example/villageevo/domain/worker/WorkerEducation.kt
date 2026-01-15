package com.example.villageevo.domain.worker

enum class WorkerEducation(
    val productionMultiplier: Float,
    val costMultiplier: Float
) {
    NONE(1.0f, 1.0f),
    SCHOOL_LV1(1.1F, 0.85F),
    SCHOOL_LV2(1.2F, 0.7F),
    SCHOOL_LV3(1.4F, 0.55F),

}