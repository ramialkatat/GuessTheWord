package com.example.guesstheword.screens.registration

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.guesstheword.MainActivity
import com.example.guesstheword.R
import com.example.guesstheword.database.PlayerDB
import com.example.guesstheword.databinding.FragmentRegistrationBinding
import com.example.guesstheword.screens.login.LoginFragmentDirections
import com.example.guesstheword.screens.player.PlayerRepository
import com.example.guesstheword.screens.player.PlayerViewModel
import com.example.guesstheword.screens.player.PlayerViewModelFactory
import com.example.guesstheword.screens.score.ScoreViewModel


class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate view and obtain an instance of the binding class.
        val binding: FragmentRegistrationBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_registration,
            container,
            false
        )
        val application=requireNotNull(this.activity).application

        val dao = PlayerDB.getInstance(application).playerDBDao
        val repository = PlayerRepository(dao)
        val factory = RegisterViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)
        binding.myViewModel = viewModel

        viewModel.message.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
        })
        binding.loginNav.setOnClickListener{
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }


        return binding.root
    }
}