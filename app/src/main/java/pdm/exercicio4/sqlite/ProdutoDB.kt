package pdm.exercicio4.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import pdm.exercicio4.model.Produto

class ProdutoDB(context: Context) {
    private val TABLE_NAME = "Produto"
    private var dbObject: DB? = null

    init{
        dbObject = DB(context, TABLE_NAME, null, 1)
    }

    fun Adicionar(produto: Produto){
        dbObject?.add(produto)
    }

    fun Deletar(produto: Produto){
        dbObject?.del(produto)
    }

    fun Atualizar(produto: Produto){
        dbObject?.update(produto)
    }

    fun Listar(): ArrayList<Produto>?{
        return dbObject?.selectAll()
    }

    private class DB(context: Context, tableName: String, cursorFactory: SQLiteDatabase.CursorFactory?, version: Int) :
        SQLiteOpenHelper(context, tableName, cursorFactory, version){

        private val tableName: String = tableName

        private val COL_CODIGO = "IntCodigo"
        private val COL_DESCRICAO = "StrDescricao"
        private val COL_QUANTIDADE = "DblQuantidade"
        private val COL_PRECO = "DblPreco"

        override fun onCreate(db: SQLiteDatabase?) {
            val createString = "CREATE TABLE " + tableName + " (" + COL_CODIGO + " INT PRIMARY KEY, " +
                                                                COL_DESCRICAO + " TEXT, " +
                                                                COL_QUANTIDADE + " REAL, " +
                                                                COL_PRECO + " REAL)"

            db?.execSQL(createString)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL("DROP IF TABLE EXISTS " + tableName)
            onCreate(db)
        }

        fun add(produto: Produto){
            val db = this.writableDatabase
            val contentValues = ContentValues()

            contentValues.put(COL_CODIGO, produto.Codigo)
            contentValues.put(COL_DESCRICAO, produto.Descricao)
            contentValues.put(COL_QUANTIDADE, produto.Quantidade)
            contentValues.put(COL_PRECO, produto.Preco)

            db.insert(tableName, null, contentValues)
            db.close()
        }

        fun del(produto: Produto){
            val db = this.writableDatabase
            db.delete(tableName, COL_CODIGO + " = " + produto.Codigo, null)
        }

        fun update(produto: Produto){
            val db = this.writableDatabase
            val contentValues = ContentValues()

            contentValues.put(COL_CODIGO, produto.Codigo)
            contentValues.put(COL_DESCRICAO, produto.Descricao)
            contentValues.put(COL_QUANTIDADE, produto.Quantidade)
            contentValues.put(COL_PRECO, produto.Preco)

            db.update(tableName, contentValues, COL_CODIGO + " = " + produto.Codigo, null)
            db.close()
        }

        fun selectAll() : ArrayList<Produto>{
            val db = this.writableDatabase
            val data = db.rawQuery("SELECT * FROM " + tableName, null)
            var list = ArrayList<Produto>()

            if (data.moveToFirst()){
                do {
                    val produto = Produto()
                    produto.Codigo = data.getInt(0)
                    produto.Descricao = data.getString(1)
                    produto.Quantidade = data.getDouble(2)
                    produto.Preco = data.getFloat(3)

                    list.add(produto)
                }
                while (data.moveToNext())
            }

            return list
        }
    }
}