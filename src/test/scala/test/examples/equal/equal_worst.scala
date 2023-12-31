package test.examples.equal

import buildinfo.BuildInfo
import datalog.dsl.{Constant, Program}
import test.ExampleTestGenerator

import java.nio.file.Paths
class equal_test_worst extends ExampleTestGenerator("equal") with equal_worst
trait equal_worst {
  val factDirectory = s"${BuildInfo.baseDirectory}/src/test/scala/test/examples/equal/facts"
  val toSolve: String = "isEqual"
  
  def pretest(program: Program): Unit = {
    val equal = program.relation[Constant]("equal")

    val isEqual = program.relation[Constant]("isEqual")

    val succ = program.relation[Constant]("succ")

    val m, n, r, pn, pm = program.variable()

    
    equal("0", "0", "1") :- ()
    equal(m, n, r) :- (succ(pn, n), succ(pm, m), equal(pm, pn, r) )
    
    isEqual(r) :- equal("5", "7", r)
    
    succ("0", "1") :- ()
    succ("1", "2") :- ()
    succ("2", "3") :- ()
    succ("3", "4") :- ()
    succ("4", "5") :- ()
    succ("5", "6") :- ()
    succ("6", "7") :- ()
    succ("7", "8") :- ()
    succ("8", "9") :- ()
    succ("9", "10") :- ()
    succ("10", "11") :- ()
    succ("11", "12") :- ()
    succ("12", "13") :- ()
    succ("13", "14") :- ()
    succ("14", "15") :- ()
    succ("15", "16") :- ()
    succ("16", "17") :- ()
    succ("17", "18") :- ()
    succ("18", "19") :- ()
    succ("19", "20") :- ()
  }
}
