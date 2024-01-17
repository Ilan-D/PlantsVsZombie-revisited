#step 1
javac src/model/*.java src/model/enemy/*.java src/model/defender/*.java src/view/*.java src/controller/*.java;

#step 2
java -cp src view.Start;

#step 3
rm src/model/*.class src/model/enemy/*.class src/model/defender/*.class src/view/*.class src/controller/*.class

#java src view.Start