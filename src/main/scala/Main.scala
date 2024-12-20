import TableProcessor.TableProcessor

package Main {}

object Main extends App {

    private def customMain(args: Array[String]): Unit = {
        // Encapsulates the Table Processor Program into this Facade
        // It only takes the List[String] as input which comes from the CLI
        // Commands
        val tableProcessor = new TableProcessor(args.toList)
        tableProcessor.run()

    }

    customMain(args)
}