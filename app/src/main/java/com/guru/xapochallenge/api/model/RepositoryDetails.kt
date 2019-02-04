package com.guru.xapochallenge.api.model

data class RepositoryDetails(
    var repoName: String? = "",
    var avatarUrl: String? = "",
    var ownerName: String? = "",
    var subscribersCount: Int? = 0,
    var subscribersUrl: String? = ""
)