nohup java -jar lib/echoserver.jar 8080 > logserver.txt 2> errorsserver.txt < /dev/null &
PID=$!
echo $PID > pid.txt
