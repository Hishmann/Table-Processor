package LoadData
import formats._

class LD(val ip: inputFormatParameters, val lp: loadFormatParameters) {
    private val allLoadFormats: List[loadFormats] = List(
        new file(),
        new weblink()
    )
    private val allInputFormats: List[inputFormats] = List(
        new csv(),
        new xml()
    )

    def load(): List[List[String]] = {
        var loadResult: String = ""
        allLoadFormats.foreach(x => {
            x.updateParams(lp)
            if (x.isValidLoadType) {
                loadResult = x.load
            }
        })
        if (loadResult.isEmpty) throw new RuntimeException("Loading input file Error")
        var inputResult: List[List[String]] = List()
        allInputFormats.foreach(x => {
            x.updateParams(ip)
            if (ip.inputFormat == x.formatName) {
                inputResult = x.parse(loadResult)
            }
        })
        inputResult
    }
}


