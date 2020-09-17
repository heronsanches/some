package hsgpf.some.model.datasource.remote.exceptions

import java.lang.Exception

class GithubExceptions(message: String) : Exception(message)

fun headerNotFound() = "Header not found."
