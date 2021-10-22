package com.example.guesstheword.screens.player


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.guesstheword.R
import com.example.guesstheword.database.Player
import com.example.guesstheword.database.PlayerDB
import com.example.guesstheword.databinding.PlayerActivityBinding

class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: PlayerActivityBinding
    private lateinit var playerViewModel: PlayerViewModel
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.player_activity)
        val dao = PlayerDB.getInstance(application).playerDBDao
        val repository = PlayerRepository(dao)
        val factory = PlayerViewModelFactory(repository)
        playerViewModel = ViewModelProvider(this, factory).get(PlayerViewModel::class.java)
        binding.myViewModel = playerViewModel
        binding.lifecycleOwner = this

        adapter = RecyclerViewAdapter { x: Player ->
            Toast.makeText(
                this,
                "Player ${x.P_ID} clicked",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.playerRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.playerRecyclerView.adapter = adapter
        (binding.playerRecyclerView.layoutManager as LinearLayoutManager).reverseLayout = true
        displayPlayersList()
    }

//    private fun listItemClicked(player: Player){
//        playerViewModel.initUpdateAndDelete(player)
//    }
//
//    private fun initRecyclerView(){
//        adapter = RecyclerViewAdapter({selectedItem: Player ->listItemClicked(selectedItem)})
//        binding.playerRecyclerView.layoutManager = LinearLayoutManager(this)
//        binding.playerRecyclerView.adapter = adapter
//        displayPlayersList()
//    }


    private fun displayPlayersList() {
        playerViewModel._savedPlayers.observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }


}
