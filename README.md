# Table-Processor

A command-line application designed to load, process, evaluate, and format tabular data. This tool supports Excel-like formulas, row filtering, range selection, and output formats such as CSV.

## Features
* **Formula Evaluation**: Supports arithmetic operations (+, -, *, /) and cell references (e.g., `=A1+B2`) with error detection for cyclical dependencies.
* **Row Filtering**: Filter data using numeric comparisons (<, >, <=, >=, ==, !=) or by checking if cells are empty/non-empty.
* **Range Selection**: Extract specific rectangular sections of a table using cell coordinates (e.g., `A1` to `C10`).
* **Pretty Printing**: Export processed data to standard CSV.
* **Extensible Design**: Decoupled architecture allowing for easy addition of new operators, input sources, or output formats.

## CLI Usage
The application is executed via command-line arguments. The `--input-file` argument is the only mandatory parameter.

### Input/Output Options
* `--input-file [FILE]`: Path to the source CSV file.
* `--input-separator [STR]`: Define a custom separator (defaults to `,`).
* `--output-file [FILE]`: Path to save the output; if omitted, results print to stdout.
* `--output-format [csv|md]`: Choose the output style.
* `--stdout` : print the table to the standard output (defaults to true)
* `--headers`: Enable row numbers and column letters in the output.

### Transformation Options
* `--range [FROM] [TO]`: Print a specific rectangle (e.g., `--range B2 D4`).
* `--filter [COL] [OP] [VAL]`: Filter rows by value (e.g., `--filter C > 10`).
* `--filter-is-empty [COL]`: Keep only rows where the specified column is empty.
* `--filter-is-non-empty [COL]`: Keep only rows where the specified column has a value.

## Technical Logic
* **Coordinates**: Columns are labeled alphabetically (A, B, C...) and rows are 1-indexed.
* **Evaluation**: Formulas are evaluated from left to right. Invalid operations or circular references result in an error state.
* **Filtering**: Row indices remain persistent after filtering (e.g., row 2 stays row 2 even if row 1 is hidden).
* **Trimming**: By default, the application trims trailing empty rows and columns.

## Development
The project is built using Object-Oriented principles, utilizing an Abstract Syntax Tree (AST) for formula parsing and the Chain of Responsibility pattern for CLI argument handling. Comprehensive unit tests ensure the reliability of the evaluation engine and data transformations.
