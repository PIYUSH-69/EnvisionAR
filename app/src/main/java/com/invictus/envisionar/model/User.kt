package com.invictus.envisionar.model


data class User(
    val _id: String, // Contextual serialization for ObjectId
    val name: String,
    val email: String,
    val password: String // Store hashed password in production
)
