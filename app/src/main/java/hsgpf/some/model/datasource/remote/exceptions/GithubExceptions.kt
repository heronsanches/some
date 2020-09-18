package hsgpf.some.model.datasource.remote.exceptions

class GithubExceptions(message: String) : Exception(message)

fun headerNotFound(headerName: String) = "$headerName not found."

fun headerPropertyNotFound(headerName: String, property: String) =
   "Header $headerName: $property not found or could not be processed."
