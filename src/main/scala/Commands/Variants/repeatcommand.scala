package Commands.Variants

import Commands.command

class repeatcommand(override val keyStr: String = "",
                    override val keyStr2: String = "",
                    override val helpStr: String = "",
                    override val inputCount: Int = 1) extends command {
    def deepCopy: repeatcommand = new repeatcommand(keyStr, keyStr2, helpStr, inputCount)
    def processCommand(args: List[String]): List[String] = {
        var i: Int = 0
        var repeats: Int  = 0
        var tempArgStr: List[String] = List()
        while (i < args.length) {
            if (args(i) == "--" + keyStr || args(i) == "-" + keyStr2) {
                repeats += 1;
                seen = 1
                for (j <- 1 to inputCount) {
                    if (i + j < args.length && !args(i + j).startsWith("--") && !args(i + j).startsWith("-")) {
                        tempArgStr = tempArgStr ++ List(args(i+j))
                    }
                }
                if (tempArgStr.length < inputCount * repeats) {
                    failed = 1;
                    return List()
                }
            }
            i += 1
        }
        if (repeats != 0) {
            return tempArgStr
        } else {
            return List()
        }
    }

}
