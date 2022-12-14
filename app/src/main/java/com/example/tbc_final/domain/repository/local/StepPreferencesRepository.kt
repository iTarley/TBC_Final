package com.example.tbc_final.domain.repository.local

interface StepPreferencesRepository {

    suspend fun putStep(
        steps: String,

    )
    suspend fun putTotalStep(
        steps: String
    )

    suspend fun putPoints(
        points:String
    )

    suspend fun getStep():Result<String>

    suspend fun getTotalStep():Result<String>

    suspend fun getPoints():Result<String>
}