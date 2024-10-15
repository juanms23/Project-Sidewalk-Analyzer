# Project Sidewalk Analyzer
## Summary
This Java program is a command-line tool that implements Dijkstra's Algorithm to analyze Greater Seattle street accessibility data. The program parses both text files and CSV of sidewalk
accessibility scores from Project Sidewalk, found here:
https://sidewalk-sea.cs.washington.edu/
This repo comes with some test files, and instruction for setup can be followed in the README.

## Setup
1. Clone the repo in your desired directory.
2. To Compile, ensure you are in the same directory and run this following command in the command line:
```
    javac -cp .:heap.jar ShortestPaths.java
```
3. To run text files:
```
java ShortestPaths basic filename.txt sourceNode destinationNode
```
For Example:
```
java ShortestPaths basic Simple1.txt 1 5
```
4. To run CSV files:
```
java ShortestPaths db filename.csv sourceNode destinationNode
```
For Example:
```
java ShortestPaths db sidewalkData.csv 200 500
```
Hope you enjoy!
