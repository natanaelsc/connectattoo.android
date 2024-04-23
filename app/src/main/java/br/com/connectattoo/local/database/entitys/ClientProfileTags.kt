package br.com.connectattoo.local.database.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "client_profile_tags",
    foreignKeys = [ForeignKey(
        entity = ClientProfileEntity::class,
        parentColumns = ["idRoom"],
        childColumns = ["clientProfileId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("clientProfileId")]
)
data class ClientProfileTag (
    @PrimaryKey(autoGenerate = true) val idRoom: Long = 0,
    val clientProfileId: Long,
    @ColumnInfo("id_api") val id: String? = "",
    @ColumnInfo("tag_name") val name: String? = ""
)
