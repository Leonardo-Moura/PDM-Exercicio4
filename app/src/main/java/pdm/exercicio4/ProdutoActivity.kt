package pdm.exercicio4

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_produto.*
import pdm.exercicio4.model.Produto

class ProdutoActivity : AppCompatActivity() {
    var produtoInicial: Produto? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produto)

        IniciarCampos()
        IncluirListeners()

        if (intent.hasExtra(PRODUTO_RETORNO)) intent.removeExtra(PRODUTO_RETORNO)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_produto, menu)

        if (produtoInicial == null){
            menu?.getItem(0)?.isEnabled = false
            menu?.getItem(1)?.isEnabled = false
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.Men_Atualizar -> {
                Concluir()
            }
            R.id.Men_Deletar -> {
                Deletar()
            }
        }
        return true
    }

    private fun IniciarCampos(){
        TxtCodigo.text = ""
        TxtDescricao.setText("")
        TxtPreco.setText("")
        TxtQuantidade.setText("")

        if (intent.hasExtra(PRODUTO_SELECIONADO)){
            produtoInicial = intent.getSerializableExtra(PRODUTO_SELECIONADO) as Produto

            TxtCodigo.text = produtoInicial?.Codigo.toString()
            TxtDescricao.setText(produtoInicial?.Descricao)
            TxtPreco.setText("%.2f".format(produtoInicial?.Preco))
            TxtQuantidade.setText(produtoInicial?.Quantidade.toString())

            intent.removeExtra(PRODUTO_SELECIONADO)
        }
    }

    private fun IncluirListeners(){
        BtnOk.setOnClickListener(View.OnClickListener {
            Concluir()
        })

        BtnCancelar.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun Concluir(){
        var produto: Produto? = null

        if (produtoInicial == null)
            produto = CriarItem()
        else
            produto = AtualizarItem(produtoInicial)

        if (produto != null){
            var returnIntent = Intent()
            returnIntent.putExtra(PRODUTO_RETORNO, produto)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
        else{
            Toast.makeText(this, R.string.StrPreencherCampos, Toast.LENGTH_LONG).show()
        }
    }

    private fun Deletar(){
        var returnIntent = Intent()
        returnIntent.putExtra(ITEM_DELETADO, produtoInicial)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    private fun CriarItem(): Produto?{
        var produto = Produto()

        if (TxtDescricao.text.toString() == "") return null
        if (TxtPreco.text.toString() == "") return null
        if (TxtQuantidade.text.toString() == "") return null

        produto.Descricao = TxtDescricao.text.toString()
        produto.Preco = "%.2f".format(TxtPreco.text.toString().toFloat()).toFloat()
        produto.Quantidade = "%.4f".format(TxtQuantidade.text.toString().toDouble()).toDouble()

        return produto
    }

    private fun AtualizarItem(produto: Produto?): Produto?{
        if (TxtDescricao.text.toString() == "") return null
        if (TxtPreco.text.toString() == "") return null
        if (TxtQuantidade.text.toString() == "") return null

        produto?.Descricao = TxtDescricao.text.toString()
        produto?.Preco = "%.2f".format(TxtPreco.text.toString().toFloat()).toFloat()
        produto?.Quantidade = "%.4f".format(TxtQuantidade.text.toString().toDouble()).toDouble()

        return produto
    }
}