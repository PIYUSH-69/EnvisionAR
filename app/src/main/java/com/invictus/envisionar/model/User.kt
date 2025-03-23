package com.invictus.envisionar.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class User(
    @BsonId val id: ObjectId = ObjectId(), // MongoDB uses _id as the primary key
    val name: String,
    val email: String,
    val password: String // Note: Passwords should be hashed before storing in the database
)