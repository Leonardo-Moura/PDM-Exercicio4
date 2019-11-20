package pdm.exercicio4

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import pdm.exercicio4.adapter.ProdutoAdapter
import pdm.exercicio4.extension.ListaDeProdutos
import pdm.exercicio4.model.Produto

class MainActivity : AppCompatActivity(){
    var lstProdutos : ListaDeProdutos? = null
    var adaptador : ProdutoAdapter? = null
    var menuPesquisa : MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lstProdutos = ListaDeProdutos(this)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        lsvProdutos.layoutManager = layoutManager

        setupViewAdapter(lstProdutos)
    }

    fun setupViewAdapter(list: List<Produto>?){
        adaptador = ProdutoAdapter(list!!)

        adaptador?.onItemClick = { produto ->
            val i = Intent(this, ProdutoActivity::class.java)
            i.putExtra(PRODUTO_SELECIONADO, produto)
            startActivityForResult(i, PRODUTO_ATUALIZAR_REQUEST)
        }

        lsvProdutos.adapter = adaptador
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)

        menuPesquisa = menu?.findItem(R.id.Men_Pesquisar)
        val searchView = menuPesquisa?.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                Pesquisar(p0!!)
                return true
            }

        })

        menuPesquisa?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                setupViewAdapter(lstProdutos)
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.Men_Cadastrar -> {
                val i = Intent(this, ProdutoActivity::class.java)
                startActivityForResult(i, PRODUTO_NOVO_REQUEST)
            }
        }
        return true
    }

    private fun Atualizar(data: Intent?){
        var produto = data?.getSerializableExtra(PRODUTO_RETORNO) as Produto
        setupViewAdapter(lstProdutos)
        adaptador?.notifyItemChanged(lstProdutos?.Atualizar(produto)!!)
        menuPesquisa?.collapseActionView()
    }

    private fun Cadastrar(data: Intent?){
        if (data?.hasExtra(PRODUTO_RETORNO)!!){
            var produto = data.getSerializableExtra(PRODUTO_RETORNO) as Produto
            setupViewAdapter(lstProdutos)
            adaptador?.notifyItemInserted(lstProdutos?.Adicionar(produto)!!)
            menuPesquisa?.collapseActionView()
        }
    }

    private fun Deletar(data: Intent?){
        var produto = data?.getSerializableExtra(ITEM_DELETADO) as Produto
        setupViewAdapter(lstProdutos)
        adaptador?.notifyItemRemoved(lstProdutos?.Apagar(produto)!!)
        menuPesquisa?.collapseActionView()
    }

    private fun Pesquisar(strPesquisa: String){
        val lstFilt = lstProdutos?.filter { produto -> produto.Descricao.toUpperCase().contains(strPesquisa.toUpperCase()) }
        setupViewAdapter(lstFilt)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            if (requestCode == PRODUTO_NOVO_REQUEST){
                Cadastrar(data)
            }

            if (requestCode == PRODUTO_ATUALIZAR_REQUEST){
                if (data?.hasExtra(PRODUTO_RETORNO)!!)
                    Atualizar(data)

                if (data.hasExtra(ITEM_DELETADO))
                    Deletar(data)
            }
        }
    }
}
