import org.scalatest.FunSuite
import LoadData.formats._
import LoadData.LD
import java.io.FileNotFoundException

class LDTest extends FunSuite {

    val testload1: loadFormats = new file("src/test/FilesTest/test1.csv")
    val testinput1: inputFormats = new csv()

    test("Loading a csv file") {
        testinput1.parse(testload1.load) ==
            List(List("1","4","20"),
            List("10000","=C1+10","=6/2*(1+2)"),
            List("2","", "=A1+B2+(-10*6/2)"))
    }

    val testload2: loadFormats = new file("")
    test("File not found Exception") {
        assertThrows[FileNotFoundException] {
            testload2.load
        }
    }

    test("LD class test") {
        val ld = new LD(new inputFormatParameters(), new loadFormatParameters(inputFileDest="src/test/FilesTest/test1.csv"))
        ld.load() ==
            List(List("1","4","20"),
            List("10000","=C1+10","=6/2*(1+2)"),
            List("2","", "=A1+B2+(-10*6/2)"))
    }

//    val params2: Map[String, List[String]] = Map(
//        (new inputFileCommand()).keyStrReturn() -> List("src/test/FilesTest/test2.csv"),
//    )
//
//    test("Checking if row sizes are the same and if default settings work") {
//        assertThrows[RuntimeException] {
//            val LP = new LP(new CommandArgs(params2))
//        }
//    }

}
