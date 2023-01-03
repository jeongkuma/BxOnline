## intellij boot run option
vm options => -Dspring.profiles.active=local
              -Dlogging.level.root=info
              -DloggerPathFile=/Users/dhj/Downloads/log/scp/scp.log
              -DloggerPath=/Users/dhj/Downloads/log/scp

## Gradle init
gradle wrapper
./gradlew wrapper --gradle-version=5.5