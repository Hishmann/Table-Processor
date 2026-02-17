package Modules.FileHandling.SourceToFileData

import org.scalatest.FunSuite

class FileSourceToFileDataStringTest extends FunSuite {
    val FStoFD = new FileSourceToFileData()

    test("Correct source type checker") {
        assert(FStoFD.isSourceType("src/test/FilesTest/test1.csv"))
        assert(!FStoFD.isSourceType("src/test/FilesTest/doesntexist.csv"))
        assert(!FStoFD.isSourceType("www.test.com/file.csv"))
    }

    val result1: String = "Testing if it works" + "\n" + "and I hope it does"

    test("Source data extraction to String") {
        val result = FStoFD.collectSource("src/test/scala/Modules/FileHandling/SourceToFileData/test.txt").data
        assert(result == result1)
    }
}
