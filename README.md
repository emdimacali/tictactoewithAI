<h4>How to build and run Using Eclipse</h4>

1. Copy "Tic Tac Toe with AI" folder in workspace.
2. In eclipse, File > Import.
3. Select General > Existing Projects into Workspace
4. Select workspace > Tic Tac Toe with AI.
5. Click Package Explorer > Tic Tac Toe with AI > src > exer10 > Exer10.java.
6. Click Ctrl + F11. Close whatever window comes up.
7. Click File > Export
8. Select Java > Runnable JAR file
9. Select Exer10 - Tic Tac Toe with AI in Launch Configuration
10. Specify your export destination.
11. Click Finish. Your jar file is in your specified export destination.


<h4>How to build and run using StandAlone java (Use the files in no eclipse folder) </h4>

1. cd "no eclipse"
2. javac *.java
3. jar -cvfm Exer10.jar manifest.txt *
4. java -jar Exer10.jar
