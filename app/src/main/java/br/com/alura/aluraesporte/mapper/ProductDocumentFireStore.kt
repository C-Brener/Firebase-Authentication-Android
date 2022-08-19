package br.com.alura.aluraesporte.mapper

import br.com.alura.aluraesporte.model.Produto
import java.math.BigDecimal

class ProductDocumentFireStore(
    val nome: String = "",
    val preço: String = "0.0"
) {
    fun mapperForProductModel(): Produto = Produto(
        nome = nome,
        preco = BigDecimal(preço)
    )
}