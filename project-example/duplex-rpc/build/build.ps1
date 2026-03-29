javac -cp ..\lib\gson-2.13.2.jar (Get-ChildItem ..\src -Recurse -Filter *.java | ForEach-Object FullName) -d ..\bin
jar cf ..\target\duplex-rpc.jar -C ..\bin .
copy ..\lib\*.jar ..\target