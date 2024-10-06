This application processes flow logs stored in the `data` directory.

It summarizes the tag counts and port/protocol count for all of the flow log entries.

Use `mvn clean install` to compile and install dependencies

### Unit Tests
To run the unit-tests, use `mvn test` command.

### Application Run
To run the appliciation, use `mvn exec:java` command. 

By default 2 flow_log files are checked in. In order to run tests on flow logs, one could generate `RandomEventFiles` or `FixedSetRandomEventFiles` and measure performance of processing on single-threaded and multi-threaded variants. To do so, uncomment the corresponding generation lines in the Flow.java file.
