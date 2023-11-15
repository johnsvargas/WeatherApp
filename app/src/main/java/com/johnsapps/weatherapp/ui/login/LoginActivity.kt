package com.johnsapps.weatherapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.johnsapps.weatherapp.R
import com.johnsapps.weatherapp.databinding.ActivityLoginBinding
import com.johnsapps.weatherapp.ui.principal.PrincipalActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initState()
    }

    private fun initUI() {
        title = getString(R.string.label_login_screen_name)
        binding.btnLogin.setOnClickListener {
            if(viewModel.checkField(binding.etLogin.text.toString())) {
                viewModel.login(binding.etLogin.text.toString().trim())
            }
        }
    }

    private fun initState() {
        lifecycleScope.launch() {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is LoginUIState.Error -> {
                            binding.backgroundProgress.isVisible = false
                            Toast.makeText(
                                this@LoginActivity,
                                "Ha ocurrido un error: ${uiState.msg}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is LoginUIState.ErrorField -> {
                            binding.backgroundProgress.isVisible = false
                            binding.tilLogin.error = resources.getString(uiState.resource)
                        }

                        LoginUIState.Loading -> {
                            binding.backgroundProgress.isVisible = true
                        }
                        LoginUIState.Begin -> {}
                        is LoginUIState.Success -> {
                            if(uiState.isSessionActive) {
                                goToDashBoard()
                            }
                        }
                    }
                }
            }
        }
        viewModel.isActiveSession()
    }

    private fun goToDashBoard() {
        val intent = Intent(this, PrincipalActivity::class.java)
        startActivity(intent)
        finish()
    }
}