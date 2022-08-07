package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.alura.aluraesporte.databinding.CadastroUsuarioBinding
import br.com.alura.aluraesporte.ui.viewmodel.CadastroUsuarioViewModel
import br.com.alura.aluraesporte.ui.viewmodel.ComponentesVisuais
import br.com.alura.aluraesporte.ui.viewmodel.EstadoAppViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.cadastro_usuario.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class CadastroUsuarioFragment : Fragment() {
    private var _binding: CadastroUsuarioBinding? = null
    private val binding get() = _binding!!
    private val controlador by lazy {
        findNavController()
    }
    private val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()
    private val cadastroUsuarioViewModel: CadastroUsuarioViewModel by viewModel()

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
        estadoAppViewModel.temComponentes = ComponentesVisuais()
        cadastro_usuario_botao_cadastrar.setOnClickListener {
            val email = cadastro_usuario_email.editText?.text.toString()
            val senha = cadastro_usuario_senha.editText?.text.toString()
            cadastroUsuarioViewModel.cadastra(email, senha).observe(viewLifecycleOwner) {
                it?.let { recurso ->
                    if (recurso.dado) {
                        val messageSuccess = recurso.information ?: ""
                        Snackbar.make(view, messageSuccess, Snackbar.LENGTH_SHORT)
                            .show()
                        controlador.popBackStack()
                    } else {
                        val mensagemError = recurso.information ?: "Erro não detectado"
                        Snackbar.make(view, mensagemError, Snackbar.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

}