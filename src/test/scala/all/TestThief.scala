package all
import datalog.dsl.{Program, Relation}
import datalog.execution.{ExecutionEngine, NaiveExecutionEngine, SemiNaiveExecutionEngine}
import datalog.storage.{CollectionsStorageManager, IndexedCollStorageManager, RelationalStorageManager}
import graphs.{EDBFromFile, TestGraph}

import java.nio.file.*

abstract class TestThief(p: () => Program, t: String, isNaive: Boolean = false) extends munit.FunSuite {
  private val srcDir = Paths.get("src", "test", "scala", "graphs", "fromFile", t)

  val graph: Fixture[TestGraph] = new Fixture[TestGraph]("Graph") {
    var graph: TestGraph = null
    var program: Program = null
    def apply(): TestGraph = graph

    override def beforeEach(context: BeforeEach): Unit = {
      program = p()
      graph = EDBFromFile(program, Paths.get(srcDir.toString, context.test.name))
    }
  }
  override def munitFixtures = List(graph)

    srcDir.toFile
    .listFiles
    .filter(_.isDirectory)
    .map(_.getName)
    .foreach(testdir => {
      test(testdir) {
        val g = graph()
        g.queries.map((hint, query) => {
          if (!(query.skipNaive && isNaive)) {
            assertEquals(
              query.relation.solve(),
              query.solution,
              s"relation '$hint' did not match'"
            )
            println(s"passed: relation $testdir.$hint") // get around munit lack of nesting
          } else {
            println(s"skipped: $testdir.$hint")
          }
        })
      }
    })
}

// better way to instantiate type w reflection?
class TT_PARTIAL_SemiNaive_Relational extends TestThief(() => new Program(
  new SemiNaiveExecutionEngine(
    new RelationalStorageManager())), "partial")
class TT_PARTIAL_Naive_Relational extends TestThief(() => new Program(
  new NaiveExecutionEngine(
    new RelationalStorageManager())), "partial", true)
class TT_PARTIAL_SemiNaive_IdxCollections extends TestThief(() => new Program(
  new SemiNaiveExecutionEngine(
    new IndexedCollStorageManager())), "partial")
class TT_PARTIAL_Naive_IdxCollections extends TestThief(() => new Program(
  new NaiveExecutionEngine(
    new IndexedCollStorageManager())), "partial", true)

class TT_COMPLETE_SemiNaive_Relational extends TestThief(() => new Program(
  new SemiNaiveExecutionEngine(
    new RelationalStorageManager())), "complete")
class TT_COMPLETE_Naive_Relational extends TestThief(() => new Program(
  new NaiveExecutionEngine(
    new RelationalStorageManager())), "complete", true)
class TT_COMPLETE_SemiNaive_IdxCollections extends TestThief(() => new Program(
  new SemiNaiveExecutionEngine(
    new IndexedCollStorageManager())), "complete")
class TT_COMPLETE_Naive_IdxCollections extends TestThief(() => new Program(
  new NaiveExecutionEngine(
    new IndexedCollStorageManager())), "complete", true)
