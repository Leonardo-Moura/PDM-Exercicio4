package pdm.exercicio4.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pdm.exercicio4.R
import pdm.exercicio4.model.Produto

class ProdutoAdapter(Produtos: List<Produto>) : RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder>(){
    private var lstProduto = listOf<Produto>()
    public var onItemClick : ((Produto) -> Unit)? = null

    init{lstProduto = Produtos}

    override fun getItemCount(): Int {
        return lstProduto.size
    }

    override fun onBindViewHolder(p0: ProdutoViewHolder, p1: Int) {
        var produto = lstProduto.get(p1)
        p0.setCodigo(produto.Codigo.toString())
        p0.setDescricao(produto.Descricao)
        p0.setQtde(produto.Quantidade.toString())
        p0.setPreco(produto.Preco)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProdutoViewHolder {
        var itemView = LayoutInflater.from(p0.context).inflate(R.layout.adapter_produto, p0, false)
        return ProdutoViewHolder(itemView)
    }

    inner class ProdutoViewHolder(itView: View) : RecyclerView.ViewHolder(itView){
        private var tvwCodigo : TextView? = null
        private var tvwDescricao : TextView? = null
        private var tvwQtde: TextView? = null
        private var tvwPreco: TextView? = null

        public fun setCodigo(strCodigo: String){
            tvwCodigo?.text = strCodigo
        }

        public fun setDescricao(strDescricao: String){
            tvwDescricao?.text = strDescricao
        }

        public fun setQtde(strQtde: String){
            tvwQtde?.text = strQtde
        }

        public fun setPreco(fltPreco: Float){
            tvwPreco?.text = "%.2f".format(fltPreco)
        }

        init{
            tvwCodigo = itView.findViewById(R.id.txtCodigo)
            tvwDescricao = itView.findViewById(R.id.txtDescricao)
            tvwQtde = itView.findViewById(R.id.txtQtde)
            tvwPreco = itView.findViewById(R.id.txtPreco)

            itView.setOnClickListener{
                onItemClick?.invoke(lstProduto[adapterPosition])
            }
        }
    }
}