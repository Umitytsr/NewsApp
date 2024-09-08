package com.umitytsr.myapplication.ui.authentication.signInScreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umitytsr.myapplication.databinding.FragmentSignInBinding
import com.umitytsr.myapplication.ui.news.NewsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater,container,false)

        binding.habereGit.setOnClickListener {
            val intent = Intent(activity,NewsActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}