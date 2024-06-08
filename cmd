
// fix bug with sonar-qube 
+ into docker hub -> sonarqube -> choose version "community" -> copy docker pull -> cmd -> paste pull 
+ to run docker with sonar-qube : "docker run --name sonar-qube -p 9000:9000 -d sonarqube:lts-community"
+ into port localhost 9000 -> login ->  username : admin , password: admin -> change password -> set up "manual" -> set expiry -> copy terminal of mvn -> delete character : "/" ->  result in : in line 
+ start cmd -> cd project -> to using sornar-qube to find bug ,... : "mvn clean verify sonar:sonar -Dsonar.projectKey=devTri -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_0ad7629bf69c735576cb98ef87a69c7ff4ce8011"

// or install plugin with "SonarLint" in intellij IDE
+ into plugin + install "SonarLint" 
+ left taskbar -> click icon (~~~) sonarLint -> seen "report" -> fix it