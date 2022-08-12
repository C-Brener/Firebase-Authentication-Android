package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.databinding.LoginBinding
import br.com.alura.aluraesporte.model.User
import br.com.alura.aluraesporte.ui.viewmodel.ComponentesVisuais
import br.com.alura.aluraesporte.ui.viewmodel.EstadoAppViewModel
import br.com.alura.aluraesporte.ui.viewmodel.LoginViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private var _binding: LoginBinding? = null
    private val binding get() = _binding!!
    private val controlador by lazy {
        findNavController()
    }
    private val viewModel: LoginViewModel by viewModel()
    private val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estadoAppViewModel.temComponentes = ComponentesVisuais()
        setupClickListener()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.authUserResult.observe(viewLifecycleOwner) {
            it?.let { resourceValue ->
                if (resourceValue.data) {
                    vaiParaListaProdutos()
                } else {
                    binding.exceptionRegisterUser.text =
                        getString(R.string.exception, "${resourceValue.information}")
                    binding.exceptionRegisterUser.isVisible = !resourceValue.data
                }
            }
        }

    }

    private fun setupClickListener() {
        binding.loginBotaoLogar.setOnClickListener {

            val email = binding.loginEmail.editText?.text.toString()
            val password = binding.loginSenha.editText?.text.toString()
            if (email.isNotBlank() && password.isNotBlank()) {
                viewModel.autentica(User(email, password))
            }else{
                Unit
            }
        }
        binding.loginBotaoCadastrarUsuario.setOnClickListener {
            val direcao = LoginFragmentDirections
                .acaoLoginParaCadastroUsuario()
            controlador.navigate(direcao)
        }
    }

    private fun vaiParaListaProdutos() {
        val direcao = LoginFragmentDirections.acaoLoginParaListaProdutos()
        controlador.navigate(direcao)
    }

}