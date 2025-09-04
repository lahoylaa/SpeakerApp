package com.example.speaker_app

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

 data class Song(
     val artist: String = "",
     val cover: String = "",
     val song: String = "",
     val title: String = ""
 )

class FirebaseRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getSongs(): List<Song> {
        return try{
            val snapshot = db.collection("songs").get().await()
            snapshot.documents.mapNotNull{it.toObject(Song::class.java)}
        }catch(e: Exception){
            e.printStackTrace()
            emptyList()
        }
    }
}

