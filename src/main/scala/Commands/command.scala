package Commands

abstract class command(val keyStr: String = "",
                    val keyStr2: String = "",
                    val helpStr: String = "",
                    val inputCount: Int = 1) {

    protected var failed = 0
    protected var seen = 0

    def hasFailed: Boolean = failed == 1
    def hasSeen: Boolean = seen == 1
    def deepCopy: command
    def processCommand(args: List[String]): List[String]
}
