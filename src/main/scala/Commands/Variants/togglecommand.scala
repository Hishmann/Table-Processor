package Commands.Variants

import Commands.command

class togglecommand(override val keyStr: String = "",
                    override val keyStr2: String = "",
                    override val helpStr: String = "",
                    override val inputCount: Int = 0) extends command {
    def deepCopy: togglecommand = new togglecommand(keyStr, keyStr2, helpStr, inputCount)
    override def processCommand(args: List[String]): List[String] = {
        var i: Int = 0
        while (i < args.length) {
            if (args(i) == "--" + keyStr || args(i) == "-" + keyStr2) {
                seen = 1
                return List("")
            }
            i += 1
        }
        return List()
    }
}
