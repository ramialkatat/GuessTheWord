package com.example.guesstheword.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

//The DAO is the main component of Room and includes methods that offer access to your apps database it has to be annotated with @Dao. DAOs are used instead of query builders and let you separate different components of your database e.g. current data and statistics, which allows you to easily test your database.
@Dao
interface PlayerDBDao {
    @Insert
     suspend fun insertPlayer(player: Player): Long //when insert is called, Room creates the row from the entity object and inserts it into the DB

    @Insert
    suspend fun insertScore(score: Score): Long

    @Update
    suspend fun updatePlayer(player: Player): Int

    @Delete
    suspend fun deletePlayer(player: Player) : Int

    @Query("DELETE FROM Player")//Query that deletes everything from the table
    suspend fun deleteAll():Int

//    @Query("SELECT * FROM Player ORDER BY P_ID DESC") //return all the rows of the table sorted by ID in descending order
//    fun getAllPlayers(): LiveData<List<Player>> //Room allows us to get back LiveData, and it ensures LiveData's updated whenever the DB is updated.
    //However, considering MVVM architecture, getting data as a Flow is the best practice. We can easily convert the Flow into LiveData inside the ViewModel. Since LiveData needs a lifecycle, using LiveData inside repository or below classes can cause unexpected errors.
@Query("SELECT * FROM Player") //return all the rows of the table
    fun getAllPlayers(): LiveData<List<Player>>
    @Query("SELECT * FROM Score") //return all the rows of the Score table
    fun getAllScores(): LiveData<List<Score>>
}