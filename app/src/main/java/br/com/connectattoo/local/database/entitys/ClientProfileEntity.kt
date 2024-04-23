package br.com.connectattoo.local.database.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientProfile")
data class ClientProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "display_name") val displayName: String? = "",
    @ColumnInfo(name = "user_name") val username: String? = "",
    @ColumnInfo(name = "birth_date") val birthDate: String? = "",
    @ColumnInfo(name = "image_profile") val imageProfile: String? = ""
)
