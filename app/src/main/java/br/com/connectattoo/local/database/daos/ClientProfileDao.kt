package br.com.connectattoo.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.connectattoo.local.database.entitys.ClientProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientProfileDao {
    @Insert
    suspend fun insertClientProfile(clientProfile: ClientProfileEntity)

    @Query("SELECT * FROM clientProfile LIMIT 1")
    suspend fun getClientProfile(): ClientProfileEntity?

    @Query("DELETE FROM clientProfile")
    suspend fun dellClientProfile()

}
