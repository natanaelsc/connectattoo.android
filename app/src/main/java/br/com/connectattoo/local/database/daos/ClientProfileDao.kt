package br.com.connectattoo.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.connectattoo.local.database.entitys.ClientProfileEntity
import br.com.connectattoo.local.database.entitys.ClientProfileTag

@Dao
interface ClientProfileDao {
    @Insert
    suspend fun insertClientProfile(clientProfile: ClientProfileEntity)

    @Query("SELECT * FROM clientProfile LIMIT 1")
    suspend fun getClientProfile(): ClientProfileEntity?

    @Query("DELETE FROM clientProfile")
    suspend fun dellClientProfile()

    @Insert
    suspend fun insertClientProfileTag(clientProfileTags: ClientProfileEntity)
    @Query("SELECT * FROM client_profile_tags")
    suspend fun getClientProfileTags(): ClientProfileTag?

    /*


        @Query("DELETE FROM clientProfile")
        suspend fun dellClientProfile()

     */

}
