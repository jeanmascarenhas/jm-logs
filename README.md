# jm-logs
Application JM Logs Back-end

#Step by Step to run:

##Install postgreeSQL
##Create database "p_jmlogs"
##On project folder open prompt and enter:
###	mvn clean;
###	mvn package;
###	java -jar target\jm-logs-0.0.1-SNAPSHOT.jar

###The project start on http://localhost:8080/api/v1/logs

#Features:
##	Get Logs        :http://localhost:8080/api/v1/logs
##	Get by Id       :http://localhost:8080/api/v1/logs/65
##	Get by IP		:http://localhost:8080/api/v1/logs/ip?ip=192.168.234.82
##	Get by Request	:http://localhost:8080/api/v1/logs/request?request=GET
##	Post Log :http://localhost:8080/api/v1/logs
		body:
			{
				"date":"2020-01-01 21:59:33",
				"ip":"192.168.234.82",
				"request": "teste",
				"status": 200,
				"userAgent": "Mozilla/5.0 (Linux; Android 5.1.1; Nexus 6 Build/LYZ28E) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Mobile Safari/537.36"
			}
##	Put logs:  http://localhost:8080/api/v1/logs/65
		body:
			{
				"id": 65,
				"date": "2020-01-01 00:00:00.000",
				"ip": "192.168.234.82",
				"request": "GET / HTTP/1.1",
				"status": 200,
				"userAgent": "Chrome/5.0 (Linux; Android 5.1.1; Nexus 6 Build/LYZ28E) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Mobile Safari/537.36"
			}
##	Delete log: http://localhost:8080/api/v1/logs/65
##	Post file: http://localhost:8080/api/v1/logs/upload
		(Post file using: Content-Type: multipart/form-data;"	
