package hsgpf.some.model.datasource.remote.retrofit.data.github

import com.google.gson.annotations.SerializedName

data class GithubOwnerData(val login: String, @SerializedName("avatar_url") val avatarUrl: String)