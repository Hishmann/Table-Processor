package Modules.FileHandling.FileDataToObject.FileDataToTextTable

import org.scalatest.FunSuite
import Models.Table.TextTable
import Models.FileData

class CSVFileToTextTableTest extends FunSuite {

    val CSVtoTT = new CSVFileDataToTextTable(separator = ",")
    val test1 : FileData = FileData(
        "1, 4, 20" + "\n" +
        "100, 4, =A1+B2" + "\n" +
        "C1, 20, 10000"
    )
    val result1: TextTable = TextTable(List(
        List("1","4","20"),
        List("100","4","=A1+B2"),
        List("C1","20","10000"),
    ))
    test("CSV File to Text Table Test 1") {
        assert(CSVtoTT.getResult(test1) == result1)
    }

    val test2 : FileData = FileData(
        " 1,  20" + "\r\n" +
        "100,  4,   =A1+B2" + "\n" +
        "C1"
    )
    val result2: TextTable = TextTable(List(
        List("1","20"),
        List("100","4","=A1+B2"),
        List("C1"),
    ))
    test("CSV File to Text Table Test 2") {
        assert(CSVtoTT.getResult(test1) == result1)
    }

}
