# MovieTheater
## How to run
1. Clone repository
2. Navigate to folder with MovieTheater.java
3. enter `javac MovieTheater.java` to compile the program
4. Enter `java MovieTheater [args]` to run the program
### Command line arguments
1. Use `--i [path/to/input]` to use an input file
2. Use `--o [path/to/output]` to name an output file
### Assumptions
- If not all seats in a request can be filled, the entire reservation is cancelled.
- If the seating dimensions are changed the number of rows will be less than or equal to 26.
- If input and output files are not given, they are assumed to be in the same folder as the java executable.

