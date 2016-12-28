#!/bin/sh
### BEGIN INIT INFO
# Provides:          
# Required-Start:    $remote_fs $syslog
# Required-Stop:     $remote_fs $syslog
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Start daemon at boot time
# Description:       Enable service provided by daemon.
### END INIT INFO


user="pi"

BASE_DIR="/home/pi/opt"
JAR=$BASE_DIR/raspi-1.0-all.jar
CONFIG=$BASE_DIR/conf/default.yml

CMD="java -jar $JAR server $CONFIG"


name=`basename $0`

stdout_log="/var/log/$name.log"
stderr_log="/var/log/$name.err"

pid_file="/var/run/$name.pid"

isRunning() {
	[ -f "$pid_file" ] && ps `cat $pid_file` > /dev/null 2>&1
}



case $1 in
	start)
		if isRunning; then
			echo "Already started"
		else
			echo "Starting $name"
                        sudo -u "$user" cd $BASE_DIR
			sudo -u "$user" $CMD > "$stdout_log" 2> "$stderr_log" & echo $! > "$pid_file"
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
esac

exit 0
