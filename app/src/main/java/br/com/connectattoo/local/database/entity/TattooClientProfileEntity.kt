package br.com.connectattoo.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import br.com.connectattoo.data.Gallery
import br.com.connectattoo.data.TattooClientProfile
import br.com.connectattoo.data.toGalleryList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "profile")
data class TattooClientProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "display_name") val name: String? = "",
    @ColumnInfo(name = "user_name") val username: String? = "",
    @ColumnInfo(name = "birth_date") val birthDate: String? = "",
    @ColumnInfo(name = "image_profile") val imageProfile: String? = "",
    var tags: List<TagEntity> = emptyList(),
    val email: String? = "",
    var galleries: List<Gallery> = emptyList(),
) {

    fun toTattooClientProfile(): TattooClientProfile =
        TattooClientProfile(
            name = this.name ?: "",
            username = this.username ?: "",
            birthDate = this.birthDate ?: "",
            imageProfile = this.imageProfile ?: "",
            tags = this.tags.toTag(),
            email = this.email,
            galleries = this.galleries.toGalleryList()
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
    @TypeConverter
    fun fromGalleryList(gallery: List<Gallery>): String {
        val gson = Gson()
        return gson.toJson(gallery)
    }
    @TypeConverter
    fun toGalleryList(galleryAsString: String): List<Gallery> {
        val gson = Gson()
        val listType = object : TypeToken<List<Gallery>>() {}.type
        return gson.fromJson(galleryAsString, listType)
    }

}

