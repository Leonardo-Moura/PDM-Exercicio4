package pdm.exercicio4.extension

import android.content.Context
import pdm.exercicio4.model.Produto
import pdm.exercicio4.sqlite.ProdutoDB

class ListaDeProdutos(context: Context) : ArrayList<Produto>() {

    private val produtoDB = ProdutoDB(context)

    init {
        val produtosSalvos = produtoDB.Listar()

        if (produtosSalvos != null)
            this.addAll(produtosSalvos)
    }

    fun Apagar(produto: Produto) : Int{
        val index = this.indexOfFirst { p -> p.Codigo == produto.Codigo }
        this.removeAt(index)

        produtoDB.Deletar(produto)

        return index
    }

    fun Adicionar(produto: Produto) : Int{
        val index = this.size

        produto.Codigo = if (this.size == 0) 0 else this[index -1].Codigo + 1
        this.add(produto)

        produtoDB.Adicionar(produto)

        return index
    }

    fun Atualizar(produto: Produto) : Int{
        val index = this.indexOfFirst { p -> p.Codigo == produto.Codigo }
        this[index] = produto

        produtoDB.Atualizar(produto)

        return index
    }
}