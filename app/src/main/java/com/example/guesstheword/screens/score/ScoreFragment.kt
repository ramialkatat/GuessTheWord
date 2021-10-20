package com.example.guesstheword.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.guesstheword.R
import com.example.guesstheword.database.Player
import com.example.guesstheword.database.PlayerDB
import com.example.guesstheword.database.Score
import com.example.guesstheword.databinding.PlayerActivityBinding
import com.example.guesstheword.databinding.ScoreFragmentBinding
import com.example.guesstheword.screens.player.PlayerRepository
import com.example.guesstheword.screens.player.PlayerViewModel
import com.example.guesstheword.screens.player.PlayerViewModelFactory
import com.example.guesstheword.screens.player.RecyclerViewAdapter

/**
 * Fragment where the final score is shown, after the game is over
 */
class ScoreFragment : Fragment() {
    private lateinit var binding: ScoreFragmentBinding
    private lateinit var adapter: ScoreRecyclerViewAdapter
    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class.
        val binding: ScoreFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.score_fragment,
            container,
            false
        )
        val dao = PlayerDB.getInstance(requireNotNull(this.activity).application).playerDBDao
        val repository = ScoreRepository(dao)
        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()
        viewModelFactory = ScoreViewModelFactory(scoreFragmentArgs.score, repository)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ScoreViewModel::class.java)

        // Add observer for score
//        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
//            binding.scoreText.text = newScore.toString()
//        }) //not needed anymore
        binding.scoreViewModel = viewModel
        binding.lifecycleOwner = this
        initRecyclerView()

        // Navigates back to title when button is pressed
        viewModel.eventPlayAgain.observe(viewLifecycleOwner, Observer { playAgain ->
            if (playAgain) {
                findNavController().navigate(ScoreFragmentDirections.actionRestart())
                viewModel.onPlayAgainComplete()
            }
        })

        return binding.root
    }

    private fun initRecyclerView() {
        binding.scoreRecyclerView.layoutManager =
            LinearLayoutManager(requireNotNull(this.activity).application)
        binding.scoreRecyclerView.adapter = adapter
        displayScoresList()
    }

    private fun displayScoresList() {
        viewModel.getSavedScores().observe(viewLifecycleOwner, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }
}