package br.com.connectattoo.local.database.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import br.com.connectattoo.data.TattooClientProfile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "clientProfile")
data class TattooClientProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "display_name") val displayName: String? = "",
    @ColumnInfo(name = "user_name") val username: String? = "",
    @ColumnInfo(name = "birth_date") val birthDate: String? = "",
    @ColumnInfo(name = "image_profile") val imageProfile: String? = "",
    var tags: List<TagEntity> = emptyList()
) {

    fun toTattooClientProfile(): TattooClientProfile =
        TattooClientProfile(
            displayName = this.displayName ?: "",
            username = this.username ?: "",
            birthDate = this.birthDate ?: "",
            imageProfile = this.imageProfile ?: "",
            tags = this.tags.toTag()
        )
}
class TattooClientProfileConverters {
    @TypeConverter
    fun fromTagEntityList(tags: List<TagEntity>): String {
        val gson = Gson()
        return gson.toJson(tags)
    }

    @TypeConverter
    fun toTagEntityList(tagsAsString: String): List<TagEntity> {
        val gson = Gson()
        val listType = object : TypeToken<List<TagEntity>>() {}.type
        return gson.fromJson(tagsAsString, listType)
    }


}

