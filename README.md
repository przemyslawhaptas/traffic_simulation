Traffic simulation
==================

##Aparapi setup in IntelliJ IDEA
1. Add aparapi.jar to classpath:
    * Right-click on libs/aparapi/<your-distribution>/aparapi.jar and select "Add as a library"
2. Add OpenCl libs to path:
    * Run the main, stop the main
    * Edit configuration:
        * Put the path to folder containing OpenCl libs in VM Options e.g. -Djava.library.path="/Users/admin/university/semesterVI/SP/traffic_simulation/libs/aparapi/dist_mac_x86_64"
    * Ensure the java compiler has a -g flag
        * Go to IntelliJ IDEA > Preferences > Build, Execution, Deployment > Compiler > Java Compiler
        * "Generate debugging info" checkbox is equivalent to providing additional command line parameter "-g"

##Parsing OSM data
OSM parser used in this version of application doesn't suppor relation tags. If your OSM file contains these tags, remove them from the file before parsing.