// Pluggin jacoco using test coverage code 
+ Plugin in file pom.xml
+ run terminal , seen in pom.xml
+ open "taregt" -> "site" -> index.html
=================================================================================================================================================================================================================================================
// Plugin Spotless  using forrmat code
+ plugin in file pom.xml
+ run termial in intelij IDE , seem in pom.xml
=================================================================================================================================================================================================================================================

// isolation (this mean run anywhere nothing cause by error from thridparty) in unit test using H2DB
+ in file pom.xml , lin 130 -> 141
+ create "resoureces" in "test" floter 
+ using annotaion @TestPropertySource("/test.properties") in class need testing
==================================================================================================================================================================================================================================================

// integration test using testcontainer
+ in file pom.xml , line 149->175 
+ must install docker
+ using annotation @Testcontainers , @Container and @DynamicPropertySource , ex: IntegrationUserControllerTest class

==================================================================================================================================================================================================================================================

// fix bug with sonar-qube 
+ into docker hub -> sonarqube -> choose version "community" -> copy docker pull -> cmd -> paste pull 
+ to run docker with sonar-qube : "docker run --name sonar-qube -p 9000:9000 -d sonarqube:lts-community"
+ into port localhost 9000 -> login ->  username : admin , password: admin -> change password -> set up "manual" -> set expiry -> copy terminal of mvn -> delete character : "/" ->  result in : in line 
+ start cmd -> cd project -> to using sornar-qube to find bug ,... type in terminal: "mvn clean verify sonar:sonar -Dsonar.projectKey=devTri -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_0ad7629bf69c735576cb98ef87a69c7ff4ce8011"

// or install plugin with "SonarLint" in intellij IDE to fix bug
+ into plugin + install "SonarLint" 
+ left taskbar -> click icon (~~~) sonarLint -> seen "report" -> fix it
====================================================================================================================================================================================================================================================

// using Jmeter to test performance and threads request
+ download JMeter (apache-jmeter-5.6.3) and extract file.
+ into bin folder , click run jmeter.bat -> show guide JMeter
+ step 1 : in "test plan" -> click mouse right -> "add" -> "threads(Users)" -> "Thread Group" -> rename "thread group"
	+ in "Thread Group" has :
		1. "Number of thread" = number of request sent to server (ex: "Number of thread" = 10 -> has 10 request same same sent to server)
		2. "Ramp-up period" = time of number of request sent to server (ex: "Ramp-up period" = 0 -> 10 request will sent within 0s, this mean 10 request sent same time)
		3. "Loop count" = loop request (ex: "Loop count" = 5 -> 10 request -> sent 10 this request within 0s -> repeat 5 )
+ step 2 : in "Thread Group" -> click mouse right -> "add" -> "sampler" -> "HTTP Request" -> rename "HTTP Request":
	+ in "HTTP Request" has :
		1. "Protocol[http] -> type "http"
		2. "Server name or IP" -> type "localhost"
		3. "Port number" -> type "8080"
		4. "HTTP Request" -> choose "POST" or "GET" ,...
		5. "path" -> type url of api "/.../.../..."
		6. Paramter , Body Data usually using method "POST" , Files Uploads ,.....
+ step 3 : in "HTTP Request" -> click mouse right -> "add" -> "Config Element" -> "HTTP Header Manager" -> rename "HTTP Header Manager":
	+ in "HTTP Header Manager" has :
		1. below -> choose "add" -> in colunm "Name" -> type "content-type" -> in colunm "Value" type "application/json" 
		-> (to Spring aware of a json and it map to Object)
+  step 4 : in "Thread Group" -> click mouse right -> "add" -> "Listener" -> "View Result Tree" -> rename "View Result Tree":
		-> (to seen result of request)
+ step 5 : when implement a new request -> Click "Clean All" -> sst is 5 (from right to)
====================================================================================================================================================================================================================================================

// 