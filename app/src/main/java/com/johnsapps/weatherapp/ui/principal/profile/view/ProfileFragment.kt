package com.johnsapps.weatherapp.ui.principal.profile.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.johnsapps.weatherapp.R
import com.johnsapps.weatherapp.databinding.FragmentProfileBinding
import com.johnsapps.weatherapp.ui.login.LoginActivity
import com.johnsapps.weatherapp.ui.principal.profile.viewModel.ProfileUIState
import com.johnsapps.weatherapp.ui.principal.profile.viewModel.ProfileViewModel
import com.johnsapps.weatherapp.ui.principal.profile.viewModel.UserUI
import com.johnsapps.weatherapp.ui.uitils.makeToastError
import com.johnsapps.weatherapp.ui.uitils.makeToastFast
import com.johnsapps.weatherapp.ui.uitils.resetError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initState()
        initUI()
        initObservers()
    }

    private fun initUI() {
        binding.run {
            btnLogOut.setOnClickListener {
                viewModel.logOut()
            }

            tilEmail.resetError()
            tilPhoneNumber.resetError()

            btnUpdateData.setOnClickListener {
                val email = etEmail.text.toString().trim()
                val phoneNumber = etPhoneNumber.text.toString().trim()
                if (viewModel.checkFields(email, phoneNumber)) {
                    viewModel.upDateUser(email, phoneNumber)
                }
            }
        }
    }

    private fun initObservers() {
        viewModel.emailError.observe(viewLifecycleOwner) {
            binding.tilEmail.error = resources.getString(it)
        }
        viewModel.phoneNumberError.observe(viewLifecycleOwner) {
            binding.tilPhoneNumber.error = resources.getString(it)
        }
    }

    private fun initState() {
        lifecycleScope.launch() {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is ProfileUIState.Initial -> {}

                        is ProfileUIState.Loading -> {
                            binding.progressBar.isVisible = true
                        }

                        is ProfileUIState.Success -> {
                            binding.progressBar.isVisible = false
                            setUserData(uiState.userUI)
                            if (uiState.isUpdateSuccessfully) {
                                this@ProfileFragment.makeToastFast(resources.getString(R.string.label_profile_update_successfully))
                            }
                        }

                        is ProfileUIState.Error -> {
                            binding.progressBar.isVisible = false
                            this@ProfileFragment.makeToastError(uiState.msg)
                        }

                        is ProfileUIState.Logout -> {
                            binding.progressBar.isVisible = false
                            goToLogin()
                        }
                    }
                }
            }
        }
        viewModel.getUserData()
    }

    private fun setUserData(user: UserUI) {
        binding.run {
            tvTitleWelcomeUser.text =
                String.format(resources.getString(R.string.label_welcome_user), user.name)
            etEmail.setText(user.email)
            etPhoneNumber.setText(user.phoneNumber)
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.title = requireContext().getString(R.string.label_profile_screen_name)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun goToLogin() {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}