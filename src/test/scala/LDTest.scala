import org.scalatest.FunSuite
import java.io.FileNotFoundException
import Models.Parameters.LoadDataTableParameters
import LoadData.LoadDataTable
import TableCreator.TableCreatorFromListStrings

class LDTest extends FunSuite {

    val TCL = new TableCreatorFromListStrings()

    val LDTParams1 = new LoadDataTableParameters("src/test/FilesTest/test1.csv", ",", "csv")
    test("Loading a csv file") {
        val LDT = new LoadDataTable(LDTParams1)
        assert(LDT.load.getData == TCL.dataInitializer(
            List(List("1","=C1","=C2"),
            List("10000","=C1+10","=B1"),
            List("2","", "=A1+B2+(-10*6/2)"))
        ).getData)
    }


    val LDTParams2 = new LoadDataTableParameters("src/test/FilesTest/test2.csv", ",", "csv")
    test("Checking if row sizes are the same and if default settings work") {
        val LDT = new LoadDataTable(LDTParams2)
        assertThrows[RuntimeException] {
            LDT.load
        }
    }

}
