package br.com.connectattoo.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.connectattoo.local.database.entitys.ClientProfileEntity

@Dao
interface ClientProfileDao {
    @Insert
    suspend fun insertClientProfile(clientProfile: ClientProfileEntity)

    @Query("DELETE FROM clientProfile")
    suspend fun delAllInformationClientProfile()
}
