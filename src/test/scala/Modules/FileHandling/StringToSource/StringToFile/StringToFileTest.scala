package Modules.FileHandling.StringToSource.StringToFile

import Modules.FileHandling.SourceToFileData.FileSourceToFileData
import org.scalatest.FunSuite

class StringToFileTest extends FunSuite {

    val StoFT = new StringToFile()
    val FStoFD = new FileSourceToFileData()

    val test1 = "Write this to file"
    val source1 = "src/test/scala/Modules/FileHandling/StringToSource/StringToFile/test.txt"
    test("String to file") {
        StoFT.toSource(test1, source1)
        assert(FStoFD.collectSource(source1).data == test1)
    }
}
