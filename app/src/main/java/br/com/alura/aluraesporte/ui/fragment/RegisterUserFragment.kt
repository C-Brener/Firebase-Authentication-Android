package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.databinding.CadastroUsuarioBinding
import br.com.alura.aluraesporte.ui.viewmodel.ComponentesVisuais
import br.com.alura.aluraesporte.ui.viewmodel.EstadoAppViewModel
import br.com.alura.aluraesporte.ui.viewmodel.RegisterUserViewModel
import kotlinx.android.synthetic.main.cadastro_usuario.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class RegisterUserFragment : Fragment() {
    private var _binding: CadastroUsuarioBinding? = null
    private val binding get() = _binding!!
    private val controller by lazy {
        findNavController()
    }
    private val stateAppViewModel: EstadoAppViewModel by sharedViewModel()
    private val registerUserViewModel: RegisterUserViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CadastroUsuarioBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stateAppViewModel.temComponentes = ComponentesVisuais()
        setupClickListener()
        setupObserver()
    }

    private fun setupClickListener() {
        register_button.setOnClickListener {
            val email = register_user_email.editText?.text.toString()
            val pass = register_password.editText?.text.toString()
            registerUserViewModel.cadastra(email, pass)
        }
    }

    private fun setupObserver() {
        registerUserViewModel.registrationResourceLiveData.observe(viewLifecycleOwner) {
            it?.let { resource ->
                if (resource.data) {
                    binding.exceptionRegisterUser.isVisible = !resource.data
                    controller.popBackStack()
                } else {
                    val messageError = resource.information ?: "Error not found"
                    with(binding.exceptionRegisterUser) {
                        isVisible = !resource.data
                        text = getString(R.string.exception, messageError)
                    }
                }
            }
        }
    }
}

