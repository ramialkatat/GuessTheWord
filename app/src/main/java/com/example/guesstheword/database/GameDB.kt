package com.example.guesstheword.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Player::class, Score::class], version = 3, exportSchema = false)
abstract class GameDB :
    RoomDatabase() //This class is abstract because Room creates the implementation for it and not us.An abstract class cannot be instantiated. Means, we cannot create object of abstract class.
{
    abstract val gameDBDao: GameDBDao//telling the database about our DAO so that we can interact with the DB

    companion object { //allows clients to access the methods for creating or getting the DB without instantiating the class. Since the only purpose of this class is to provide us with a database, there's no reason to instantiate it
        @Volatile //this makes us ensure that the value of Instance is always up to date and the same to all execution threads. The value of the Volatile variable will never be cached and all writes and reads will be done to and from the main memory, which means that changes made by one thread to instance are visible to all other threads immediately
        private var INSTANCE: GameDB? =
            null //Instance will keep a reference for the DB once we have one. This will help us avoid repeatedly opening connections to the database, which is expensive.

        fun getInstance(context: Context): GameDB //this method returns a reference to the Player Database. We're going to use a builder which requires a context, so we pass that context in and we want to return a PlayerDB
        {
            synchronized(this) {//multiple threads can potentially ask for a DB instance at the same time in more complex apps. Wrapping our code into synchronized means that only one thread of execution at a time enters this block of code, to ensure that the DB is initialized only once
                var instance =
                    INSTANCE //copying the current value of Instance to a local variable, and using smart casting
                if (instance == null) {
                    instance =
                        Room.databaseBuilder( //creating the database by first invoking the databaseBuilder
                            context.applicationContext, //supply the contexts that we passed in
                            GameDB::class.java, //telling it which DB to build
                            "game_data_database" //giving the DB a name
                        )
                            .build()//building the DB
                }
                return instance //Return instance. Smart cast to be non-null
            }
        }
    }
}