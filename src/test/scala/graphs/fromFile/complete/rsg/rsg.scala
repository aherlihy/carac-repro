package graphs

import datalog.dsl.{Program, Constant}
class rsg extends TestIDB {

  def run(program: Program): Unit = {
    val DOWN = program.relation[Constant]("DOWN")

    val FLAT = program.relation[Constant]("FLAT")

    val RSG = program.relation[Constant]("RSG")

    val UP = program.relation[Constant]("UP")

    val x, y, a, b = program.variable()
    
    RSG(x,y) :- FLAT(x,y)
    RSG(x,y) :- ( UP(x, a), RSG(b, a), DOWN(b, y) )
    
    UP("a","e") :- ()
    UP("a","f") :- ()
    UP("f","m") :- ()
    UP("g","n") :- ()
    UP("h","n") :- ()
    UP("i","o") :- ()
    UP("j","o") :- ()
    
    FLAT("g","f") :- ()
    FLAT("m","n") :- ()
    FLAT("m","o") :- ()
    FLAT("p","m") :- ()
    
    DOWN("l","f") :- ()
    DOWN("m","f") :- ()
    DOWN("g","b") :- ()
    DOWN("h","c") :- ()
    DOWN("i","d") :- ()
    DOWN("p","k") :- ()
  }
}
