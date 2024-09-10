package com.umitytsr.myapplication.ui.news.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.umitytsr.myapplication.databinding.FragmentProfileBinding
import com.umitytsr.myapplication.ui.authentication.MainActivityViewModel
import com.umitytsr.myapplication.util.applyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileFragmentViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        collectData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            logoutButton.setOnClickListener {
                viewModel.signOut(requireActivity())
            }

            darkModeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                mainActivityViewModel.setDarkModeEnabled(isChecked)
            }
        }
    }

    private fun collectData(){
        viewLifecycleOwner.lifecycleScope.launch {
            mainActivityViewModel.isDarkModeResult.collectLatest {
                binding.darkModeSwitch.isChecked = it
                applyTheme(it)
            }
        }
    }
}