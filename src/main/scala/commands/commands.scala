
package commands

trait command {
    val toggleDetection = "TOGGLED"
    protected var helpStr: String = ""
    protected var keyStr: String = ""
    protected var keyStr2: String = ""
    protected var inputCount: Int = 1
    protected var failed = 0

    def printHelp(): Unit =  println(helpStr)
    def keyStrReturn: String = keyStr
    def keyStr2Return: String = keyStr2
    def hasFailed: Boolean = { failed == 1 }
    def toggleValueReturn: String = toggleDetection

    def processCommand(args: List[String]): List[String] = {
        var i: Int = 0
        var tempArgStr: List[String] = List()
        while (i < args.length) {
            if (args(i) == keyStr || args(i) == keyStr2) {
                for (j <- 1 to inputCount) {
                    if (i + j < args.length && !args(i + j).startsWith("--") && !args(i + j).startsWith("-")) {
                        tempArgStr = tempArgStr ++ List(args(i+j))
                    }
                }
                if (tempArgStr.length == inputCount) {
                    return tempArgStr
                } else {
                    failed = 1;
                    return List()
                }
            }
            i += 1
        }
        return List()
    }
}

class inputFileCommand extends command {
    helpStr = "--input-file [filepath/html/XML]\n" +
        "Command line option to mention input file location"
    keyStr = "--input-file"
}

class separatorCommand extends command {
    helpStr = "--separator [SEPARATOR]\n" +
        "Command line option to mention input file seperator to read along"
    keyStr = "--separator"
}

class outputSeparatorCommand extends command {
    helpStr = "--output-separator [SEPARATOR] (default ,)\n" +
        "Command line option to mention output seperator where SEPERATOR = , or some one char symbol"
    keyStr = "--output-separator"
}

class formatCommand extends command {
    helpStr = "--format [TYPE] (default csv)\n" +
        "Command line option to mention output file format where TYPE = csv or md"
    keyStr = "--format"
}

class rangeCommand extends command {
    helpStr = "--range [FROM] [TO]\n" +
        "Command line option to mention the range for printing the output table"
    keyStr = "--range"
    inputCount = 2
}

trait commandToggle extends command {
    override def processCommand(args: List[String]): List[String] = {
        var i: Int = 0
        while (i < args.length) {
            if (args(i) == keyStr || args(i) == keyStr2) {
                return List(toggleDetection)
            }
            i += 1
        }
        return List()
    }
}

class helpCommand extends commandToggle {
    helpStr = "--help\n" + "Command line option to show and explain all the options"
    keyStr = "--help"
    keyStr2 = "-h"
}

trait repeatCommand extends command {
    override def processCommand(args: List[String]): List[String] = {
        var i: Int = 0
        var repeats: Int  = 0
        var tempArgStr: List[String] = List()
        while (i < args.length) {
            if (args(i) == keyStr || args(i) == keyStr2) {
                repeats += 1;
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

class filterCommand extends repeatCommand {
    helpStr = "--filter [COLUMN] [OPERATOR] [VALUE]\n" +
        "filter out lines that fail inequality checks on cells in columns (optional, can be repeated)"
    keyStr = "--filter"
    inputCount = 3
}

class filterIsEmptyCommand extends repeatCommand {
    helpStr = "--filter-is-empty [COLUMN]\n" +
        "filter out lines with non-empty cells on column (optional, can be repeated)"
    keyStr = "--filter-is-empty"
    inputCount = 1
}