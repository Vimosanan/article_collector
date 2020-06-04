package com.vimosanan.articlecollectorapplication.service.model

import com.google.gson.annotations.SerializedName

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
    val title: String?,
    @SerializedName("last_update")
    val lastUpdate: String?,
    @SerializedName("short_description")
    val shortDescription: String?,
    @SerializedName("avatar")
    val avatarUrl: String?,
    @SerializedName("text")
    val description: String?
)
