###export CATALINA_HOME="/usr/local/apache-tomcat-7.0.61"
## export JAVA_HOME="/usr/local/jdk7"
export CATALINA_HOME="/opt/tomcat"
ERROR=0
case "$1" in
 start)
            echo $"Starting Tomcat"
            sh $CATALINA_HOME/bin/startup.sh
            ;;
 stop)
           echo $"Stopping Tomcat"
           sh $CATALINA_HOME/bin/shutdown.sh
           ;;
 restart)
           sh $CATALINA_HOME/bin/shutdown.sh
           sh $CATALINA_HOME/bin/startup.sh
            ;;
 *)
         echo $"Usage: $0 {start|stop|restart}"
 exit 1
 ;;
esac

exit $ERROR