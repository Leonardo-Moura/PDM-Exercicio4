package pdm.exercicio4.model

import java.io.Serializable

class Produto : Serializable {
    var Codigo :Int = 0
    var Descricao :String = ""
    var Quantidade :Double = 0.0
    var Preco :Float = 0f

    constructor(Codigo :Int, Descricao :String){
        this.Codigo = Codigo
        this.Descricao = Descricao
    }

    constructor(){

    }
}