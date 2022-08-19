package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.model.Produto
import br.com.alura.aluraesporte.ui.viewmodel.ComponentesVisuais
import br.com.alura.aluraesporte.ui.viewmodel.EstadoAppViewModel
import br.com.alura.aluraesporte.ui.viewmodel.FormularioProdutoViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_formulario_produto.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.math.BigDecimal


class FormularioProdutoFragment : BaseFragment() {

    private val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()
    private val viewModel: FormularioProdutoViewModel by viewModel()
    private val controlador by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_formulario_produto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener()
        setupObserver()
        estadoAppViewModel.temComponentes =
            ComponentesVisuais(appBar = true, bottomNavigation = false)
    }

    private fun setupClickListener() {
        formulario_produto_botao_salva.setOnClickListener {
            val nome = formulario_produto_campo_nome.editText?.text.toString()
            val preco = formulario_produto_campo_preco.editText?.text.toString()
            val produto = Produto(nome = nome, preco = BigDecimal(preco))
            viewModel.salva(produto)
        }
    }

    private fun setupObserver() {
        viewModel.getTaskStatus.observe(viewLifecycleOwner) {
            it?.let { salvo ->
                if (salvo) {
                    controlador.popBackStack()
                    return@observe
                }
                Toast.makeText(context,"Não foi possivel salvar",Toast.LENGTH_SHORT)
            }
        }
    }
}