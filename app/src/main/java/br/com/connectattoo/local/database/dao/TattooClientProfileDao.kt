package br.com.connectattoo.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.connectattoo.local.database.entity.TattooClientProfileEntity

@Dao
interface TattooClientProfileDao {
    @Insert
    suspend fun insertTattooClientProfile(tattooClientProfile: TattooClientProfileEntity)

    @Query("SELECT * FROM clientProfile LIMIT 1")
    suspend fun getTattooClientProfile(): TattooClientProfileEntity?

    @Query("DELETE FROM clientProfile")
    suspend fun dellTattooClientProfile()

}
