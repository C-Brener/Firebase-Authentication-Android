package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.databinding.FragmentMinhaContaBinding
import br.com.alura.aluraesporte.ui.viewmodel.MinhaContaViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MinhaContaFragment : BaseFragment() {
    private var _binding: FragmentMinhaContaBinding? = null
    private val binding get() = _binding!!
    private val minhaContaViewModel: MinhaContaViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMinhaContaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        minhaContaViewModel.email.observe(viewLifecycleOwner) {
            it?.let { myaccount->
                binding.minhaContaEmail.text = getString(R.string.email_do_usuario_, myaccount)
            }
        }
    }
}