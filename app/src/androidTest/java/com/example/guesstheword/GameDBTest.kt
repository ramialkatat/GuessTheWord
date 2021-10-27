package com.example.guesstheword

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.guesstheword.database.Player
import com.example.guesstheword.database.PlayerDB
import com.example.guesstheword.database.PlayerDBDao
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class GameDBTest {

    private lateinit var gameDao: PlayerDBDao
    private lateinit var db: PlayerDB

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, PlayerDB::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        gameDao = db.playerDBDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetPlayer()=runBlocking {
        val player = Player()
        gameDao.insertPlayer(player)
        val current = gameDao.getPlayer()
        assertEquals(current?.P_ID, 1)
    }
}
