call installMaven.bat
start cmd.exe /c call spinUpMongoDB.bat
java -jar target/myretailapi-0.0.1-SNAPSHOT.jar

