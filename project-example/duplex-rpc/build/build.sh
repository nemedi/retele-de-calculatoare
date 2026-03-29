javac -cp ../lib/gson-2.13.2.jar $(find ../src -name "*.java") -d ../bin
jar cf ../target/duplex-rpc.jar -C ../bin .
cp ../lib/*.jar ../target