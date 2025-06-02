package com.example.api.dto

data class InfoDto (
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)