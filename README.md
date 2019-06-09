# IntelliWACC
IntelliJ Plugin for custom language (WACC). WACC is a C-like programming language used as part of a Compilers course at the Department of Computing at Imperial. 

## Running the plugin for the first time

You need to fetch the antlr dependencies from the maven pom file with `mvn dependency:resolve`. To process the 
grammar files, call `mvn process-resources`.

Go to `File -> Project Structure -> Project` and add a new `IntelliJ Platform Plugin SDK` or use a valid existing one.

You also need the jetbrains antlr adapter. It may be added with `git submodule init` and `git submodule update`.

Now create a new `Run Configuration` of the type `Plugin`. 
