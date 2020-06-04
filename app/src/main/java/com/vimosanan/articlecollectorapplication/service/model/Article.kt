package com.vimosanan.articlecollectorapplication.service.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

/*
    {
        "id": 1,
        "title": "article 1 title",
        "last_update": 1586404611,
        "short_description": "This is article 1 short description. She wholly fat who window extent either formal. Removing welcomed civility or hastened is.",
        "avatar": "https://minotar.net/avatar/user2"
    }

    {
        "id": 1,
        "text": "this is article 1 long text. Sitting mistake towards his few country ask... ....above are and but. "
    }
*/

data class Article (
    val id: Int,
    var title: String?,
    @SerializedName("last_update")
    val lastUpdate: String?,
    @SerializedName("short_description")
    val shortDescription: String?,
    @SerializedName("avatar")
    val avatarUrl: String?,
    @SerializedName("text")
    var description: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(lastUpdate)
        parcel.writeString(shortDescription)
        parcel.writeString(avatarUrl)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }

}

fun toStringDate(timestamp: String): String {
    return "2020-02-17"
}