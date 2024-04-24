package br.com.connectattoo.local.database.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import br.com.connectattoo.data.ClientProfile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "clientProfile")
data class ClientProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "display_name") val displayName: String? = "",
    @ColumnInfo(name = "user_name") val username: String? = "",
    @ColumnInfo(name = "birth_date") val birthDate: String? = "",
    @ColumnInfo(name = "image_profile") val imageProfile: String? = "",
    var tags: List<ClientProfileTagEntity> = emptyList()
) {

    fun toClientProfile(): ClientProfile =
        ClientProfile(
            displayName = this.displayName ?: "",
            username = this.username ?: "",
            birthDate = this.birthDate ?: "",
            imageProfile = this.imageProfile ?: "",
            tags = this.tags.toClientProfileTag()
        )
}
class ClientProfileConverters {
    @TypeConverter
    fun fromClientProfileTagEntityList(tags: List<ClientProfileTagEntity>): String {
        val gson = Gson()
        return gson.toJson(tags)
    }

    @TypeConverter
    fun toClientProfileTagEntityList(tagsAsString: String): List<ClientProfileTagEntity> {
        val gson = Gson()
        val listType = object : TypeToken<List<ClientProfileTagEntity>>() {}.type
        return gson.fromJson(tagsAsString, listType)
    }


}

