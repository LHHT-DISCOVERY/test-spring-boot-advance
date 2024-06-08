// Pluggin jacoco using test coverage code 
+ Plugin in file pom.xml
+ run terminal , seen in pom.xml
+ open "taregt" -> "site" -> index.html

// Plugin Spotless  using forrmat code
+ plugin in file pom.xml
+ run termial in intelij IDE , seem in pom.xml

// isolation (this mean run anywhere nothing cause by error from thridparty) in unit test using H2DB
+ in file pom.xml , lin 130 -> 141
+ create "resoureces" in "test" floter 
+ using annotaion @TestPropertySource("/test.properties") in class need testing

// integration test using testcontainer
+ in file pom.xml , line 149->175 
+ must install docker
+ using annotation @Testcontainers , @Container and @DynamicPropertySource , ex: IntegrationUserControllerTest class


// fix bug with sonar-qube 
+ into docker hub -> sonarqube -> choose version "community" -> copy docker pull -> cmd -> paste pull 
+ to run docker with sonar-qube : "docker run --name sonar-qube -p 9000:9000 -d sonarqube:lts-community"
+ into port localhost 9000 -> login ->  username : admin , password: admin -> change password -> set up "manual" -> set expiry -> copy terminal of mvn -> delete character : "/" ->  result in : in line 
+ start cmd -> cd project -> to using sornar-qube to find bug ,... type in terminal: "mvn clean verify sonar:sonar -Dsonar.projectKey=devTri -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_0ad7629bf69c735576cb98ef87a69c7ff4ce8011"

// or install plugin with "SonarLint" in intellij IDE to fix bug
+ into plugin + install "SonarLint" 
+ left taskbar -> click icon (~~~) sonarLint -> seen "report" -> fix it