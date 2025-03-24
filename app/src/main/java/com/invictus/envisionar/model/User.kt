package com.invictus.envisionar.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class User(
    @BsonId @Contextual val _id: ObjectId = ObjectId(), // Contextual serialization for ObjectId
    val name: String,
    val email: String,
    val password: String // Store hashed password in production
)
