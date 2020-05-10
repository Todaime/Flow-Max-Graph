javac -classpath ./lib/gs-algo-1.3.jar:./lib/gs-core-1.3.jar:./lib/gs-ui-1.3.jar -sourcepath ./src -d ./build/ $(find ./src -name *.java)

java -cp lib/*:build ro.Flow
