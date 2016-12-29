#!/bin/sh
### BEGIN INIT INFO
# Provides:          
#  start/stop a dropwizard service
### END INIT INFO


BASE_DIR="/home/pi/opt"
JAR=$BASE_DIR/raspi-1.0-all.jar
CONFIG=$BASE_DIR/conf/default.yml

CMD="java -jar $JAR server $CONFIG"


name=`basename $0`

stdout_log="$BASE_DIR/log/$name.log"
stderr_log="$BASE_DIR/log/$name.err"
mkdir $BASE_DIR/log

pid_file="$BASE_DIR/$name.pid"

isRunning() {
	[ -f "$pid_file" ] && ps `cat $pid_file` > /dev/null 2>&1
}



case $1 in
	start)
		if isRunning; then
			echo "Already started"
		else
			echo "Starting $name"
            cd $BASE_DIR
			$CMD > "$stdout_log" 2> "$stderr_log" & echo $! > "$pid_file"
			if ! isRunning; then
				echo "Unable to start, see $stdout_log and $stderr_log"
				exit 1
			fi
		fi
	;;
	stop)
		if isRunning; then
			echo "Stopping $name"
			kill `cat $pid_file`
			rm "$pid_file"
		else
			echo "Not running"
		fi
	;;
	restart)
		$0 stop
		$0 start
	;;
	*)
      echo "Usage: CMD start|stop|restart"
      exit 1
    ;;
esac

exit 0
