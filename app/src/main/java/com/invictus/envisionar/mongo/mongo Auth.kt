import com.invictus.envisionar.model.User
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId

class MongoDBAuthRepository private constructor() {

    // Database and collection names
    private val databaseName = "UserData"
    private val collectionName = "Users"

    // MongoDB connection settings
    private val connectionString = ConnectionString(
        "mongodb://dalvipiyu:2WqkqPpqG1Aq4tlk@envisionar.qsm74.mongodb.net/?retryWrites=true&w=majority&appName=EnvisionAR"
    )

    private val serverApi = ServerApi.builder()
        .version(ServerApiVersion.V1)
        .build()

    private val mongoClientSettings = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .serverApi(serverApi)
        .build()

    // Create a new client
    private val mongoClient = MongoClient.create(mongoClientSettings)

    // Get a reference to the database and collection
    private val database: MongoDatabase
        get() = mongoClient.getDatabase(databaseName)

    private val userCollection: MongoCollection<User>
        get() = database.getCollection(collectionName)

    // Singleton instance
    companion object {
        @Volatile
        private var instance: MongoDBAuthRepository? = null

        fun getInstance(): MongoDBAuthRepository {
            return instance ?: synchronized(this) {
                instance ?: MongoDBAuthRepository().also { instance = it }
            }
        }
    }

    // Register a new user
    suspend fun registerUser(name: String, email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                // Check if the user already exists
                val existingUser = userCollection.find(Filters.eq("email", email)).firstOrNull()
                if (existingUser != null) {
                    throw Exception("User with this email already exists")
                }

                // Create a new user (hash the password before storing in a real app)
                val user = User(name = name, email = email, password = password)
                userCollection.insertOne(user)
                true
            } catch (e: Exception) {
                println("Error registering user: ${e.message}")
                false
            }
        }
    }

    // Login a user
    suspend fun loginUser(email: String, password: String): User? {
        return withContext(Dispatchers.IO) {
            try {
                // Find the user by email
                val user = userCollection.find(Filters.eq("email", email)).firstOrNull()
                if (user != null && user.password == password) { // Compare hashed passwords in a real app
                    user
                } else {
                    null
                }
            } catch (e: Exception) {
                println("Error logging in user: ${e.message}")
                null
            }
        }
    }

    // Get user by ID
    suspend fun getUserById(userId: ObjectId): User? {
        return withContext(Dispatchers.IO) {
            try {
                userCollection.find(Filters.eq("_id", userId)).firstOrNull()
            } catch (e: Exception) {
                println("Error fetching user: ${e.message}")
                null
            }
        }
    }

    // Update user details
    suspend fun updateUser(userId: ObjectId, name: String, email: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val updateResult = userCollection.updateOne(
                    Filters.eq("_id", userId),
                    Updates.combine(
                        Updates.set("name", name),
                        Updates.set("email", email)
                    )
                )
                updateResult.modifiedCount > 0
            } catch (e: Exception) {
                println("Error updating user: ${e.message}")
                false
            }
        }
    }

    // Delete a user
    suspend fun deleteUser(userId: ObjectId): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val deleteResult = userCollection.deleteOne(Filters.eq("_id", userId))
                deleteResult.deletedCount > 0
            } catch (e: Exception) {
                println("Error deleting user: ${e.message}")
                false
            }
        }
    }
}