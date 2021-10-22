package com.example.guesstheword.screens.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.guesstheword.R
import com.example.guesstheword.database.Player
import com.example.guesstheword.database.PlayerDB
import com.example.guesstheword.databinding.FragmentPlayerListBinding

class PlayerListFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class.
        val binding: FragmentPlayerListBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_player_list,
            container,
            false
        )
        val application = requireNotNull(this.activity).application


        val dao = PlayerDB.getInstance(application).playerDBDao
        val repository = PlayerRepository(dao)
        val factory = PlayerViewModelFactory(repository)
        val playerViewModel = ViewModelProvider(this, factory).get(PlayerViewModel::class.java)
        binding.myViewModel = playerViewModel
        binding.lifecycleOwner = this

        val adapter = RecyclerViewAdapter { x: Player ->
        }
        binding.playerRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.playerRecyclerView.adapter = adapter
        (binding.playerRecyclerView.layoutManager as LinearLayoutManager).reverseLayout = true
        playerViewModel._savedPlayers.observe(viewLifecycleOwner, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
        return binding.root

    }
}