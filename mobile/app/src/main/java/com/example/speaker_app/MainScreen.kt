package com.example.speaker_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import coil.compose.AsyncImage

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect

import com.example.speaker_app.Song

// Debug
import android.util.Log

//@Composable
//fun MainScreen(){
//    val navController = rememberNavController()
//
//    /* Different Pages */
//    NavHost(navController = navController, startDestination = "library"){
//        composable("library"){
//            LibraryScreen(onSongClick = { songId ->
//                navController.navigate("player/$songId")
//            })
//        }
//
//        composable("player/{songId}"){ backStackEntry ->
//            val songId = backStackEntry.arguments?.getString("songId")
//            PlayerScreen(songId = songId, onBack = { navController.popBackStack() })
//        }
//
//        composable("settings"){
//            SettingsScreen()
//        }
//    }
//}

@Composable
fun LibraryScreen(onSongClick: (String) -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Library Screen")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onSongClick("song1")}){
            Text("Play Song 1")
        }
    }
}

//@Composable
//fun PlayerScreen(songId: String?, onBack: () -> Unit){
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ){
//        Text("Playing: $songId")
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = onBack){ Text("Back")
//        }
//    }
//}


@Composable
fun MainScreen(){
    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = "home"){
        composable("home"){ HomeScreen(navHostController) }
        composable("player") { PlayerScreen(navHostController) }
        composable("playlist") { PlaylistScreen(navHostController) }
    }
}

@Composable
fun HomeScreen(navController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE0B2)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Listen to your favorite music!",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("player")}){
            Text("Get Started")
        }
    }
}

@Composable
fun PlayerScreen(navController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFCC80))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        // Make the Image Placeholder
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.Red)
        )

            Spacer(modifier = Modifier.height(20.dp))

        Text("Song Title", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text("Artist Name * Album Title", fontSize = 14.sp, color = Color.DarkGray)

        Spacer(modifier = Modifier.height(20.dp))

        // Playback Controls
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
            Button(onClick = {}) {Text("Prev")}
            Button(onClick = {}) {Text("Play")}
            Button(onClick = {}) {Text("Next")}
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Playlist
        Button(onClick = {navController.navigate("playlist")}){
            Text("Go to Playlist")
        }
    }
}

@Composable
fun PlaylistScreen(navController: NavHostController){
    // val songs = remember {
    //     listOf("Song 1", "Song 2", "Song 3", "Song 4", "Song 5")
    // }

    // Add screen UI
    var songs by remember { mutableStateOf(listOf<Song>()) }
    val repo = remember { FirebaseRepository() }

    LaunchedEffect(Unit){
        Log.d("PlaylistScreen", "Fetching songs from Firebase...")
        songs = repo.getSongs()
        Log.d("PlaylistScreen", "Fetched ${songs.size} songs")
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Playlist", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))

        songs.forEach {song ->

            Log.d("Debug", song.cover)

            Row(Modifier.padding(vertical = 8.dp)){

                // Adds image to the screen
                AsyncImage(
                    model = song.cover,
                    contentDescription = "${song.title} cover",
                    modifier = Modifier.size(64.dp)
                )

                Spacer(Modifier.width(12.dp))
                Column{
                    Text(song.title, style = MaterialTheme.typography.bodyLarge)
                    Text(song.artist, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Settings Screen")
    }
}