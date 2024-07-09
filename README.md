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

// Đa luồng (mutiple threading): cho phép nhiều nhiệm vụ (task) trong chương trình thực thi cùng một lúc -> tăng hiệu năng và tăng tính tương tác của chương trình 
+ Thread là một luồng thực thi từ lúc bắc đầu đến kết thúc của nhiệm vụ (task), chúng ta có thể chạy nhiều Thread này cùng một lúc để thực thi
	mutiple threading , nghĩa là tận dụng phần cứng, ko để task nào nghĩ ngơi , cx ko để task nào làm nhiều quá
	vd: Thread 1 chạy từ 6h30 đến 7h -> nghĩ ngơi 30 phút đến 7h30 chạy tiếp -> lúc mà nghĩ ngơi từ 7h đến 7h30 chúng ta cho Thread 2 chen vào khoảng thời gian này
		sau khi xong Thread 2 -> đến 7h30 Thread 1 bắt đầu chạy tiếp (Thread 1 -> Thread 2 -> Thread 1) , nghĩa là nhiều Thread chia sẻ trên cùng 1 cpu
	=> sẽ có một số vđề khi truy cập chung tài nguyên sẽ dấn đến tranh chấp dữ liệu, 
		vd: 2 người có 2 thẻ (2 thread) cùng 1 tài khoản (1 tài nguyên) cùng đi rút tiền (tranh chấp trên 1 tài nguyên) => đây cx có thể coi là đa luồng ( mutiple threading)....xem cuối bài viết
 
trong JAVA sẽ có 2 cơ chế để lập trình đa luồng và một cơ chế quản lý Thread là Thread Pool:
    1/ cơ chế Runable (xem tại class MyTaskImplementRunnable_coche1):
	+ các task là các đối tượng được tạo ra từ lớp mà implement Runable
	+ Runable có một phương thức trừu tượng là run() chỉ định cách thức Thread làm việc
    2/ cơ chế Thread (xem tại class MyTaskExtendThread_coche2) : đối với cơ chế này thì chỉ extend được mỗi Thread và ko đc extend thêm thèn nào nữa còn Runnable thì implement nên có thể implement thêm nhiều thèn cha nữa => tùy trường hợp mà lựa chọn
	+ Thread.yield() : tạm dừng Thread hiện tại, cho thèn Thread khác nhào vào làm
	+ Thread.sleep() : cho hén ngủ, nghĩa là cx tạm dừng nhưng dừng trong khoảng thời gian chỉ định
	+ join() : Bắt Thread đang chạy đến 1 lúc cụ thể nào đó phải dừng lại, cho thèn Thread khác nhảy vào chiếm dụng CPU để thực hiện
    3/ Thread pool:
	+ vd như fb có hàng triệu tác vụ mà cứ mỗi tác vụ lại phải tạo Thread thì lãng phí tài nguyên => sinh ra cơ chế Thread Pool để giải quyết số lượng task quá lớn
	=> Thread Pool là cơ chế cho chúng ta lưu cache lại, nghĩa là ko phải lúc nào chúng ta có 1 task -> cx tạo một Thread nữa 
		vd: ta ước lượng phần cứng của mình chỉ chạy tốt với 3 thread thôi mà hệ thống 50 task thì cx chạy trên 3 thread thôi vì có tăng Thread thì hệ thống cx chậm , 
		    nên chúng ta chỉ tạo đệm 3 thread đó thôi khi nào có 1 Thread nhàng rỗi thì Thread khác nhảy vào để sử dụng và thực hiện task vụ đó => đây chính là Thread Pool
	=> Tùy vào cách Pool như thế nào :
		+ cơ chế 1 (Line 62, Main class) : vd chỉ tạo 3 thread trong suốt chương trình thì cx chỉ có 3 , khi thread nào trong pool rảnh thì nhào vào thread rảnh đó mà chạy, còn nếu bận hết thì chờ thèn mô xong thì nhảy vào chạy
			=> hiểu đơn giản như đi nộp hồ sơ: vd có 3 cửa để nộp hồ sơ mà 3 cửa đều bận , thì phải xếp hàng chờ coi 1 trong 3 cửa, cửa nào rảnh thì tới nộp hồ sơ
		+ cơ chế 2 (Line 87, Main class): nếu cx có 3 bàn mà tự nhiên ứng viên đông quá , chờ lâu quá thì tạo thêm một cái cửa số 4, số 5 để đáp ứng => khi quá tải thì phải thêm 1 Thread mới 
	=> bản chất của thread pool cx là chạy mutiple thread như nó ko tạo Thread một cách tùy tiện, có nghĩa là ko phải task nào vào cx tạo, mà nếu thèn Thread 1 chạy ok rồi thì nhảy vào Thread 2 để chạy, chứ ko tạo Thread mới => loãng phí
	=> Tạo sẵn một bể chứa các Thread , cứ Thread nào trong bể (pool) rảnh thì nhảy vào và chạy trên cái Thread rảnh đó
	+ JAVA cung cấp cho Thread Pool 2 giao diện là:
		1/: Excutor :
		2/: ExcutoerService: 

=> Vấn đề tranh chấp dữ liệu tren cùng một tài nguyên (Thread Synchronization):
	+
