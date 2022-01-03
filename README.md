# Admission_ManageSystem_SpringBoot
The RearEnd Code of Admission_ManageSystem

## FrontEnd_Project(Vue_address)
```
https://github.com/ZerviKuoc/Admission_ManageSystem
```

### JDK
```
jdk-15.0.2
```

### Customize configuration
see src/application.properities
(CORS Has been processed at the front end code------see vue.config.js)
so the follow can be deleted
CORS_ALLOWED_ORIGINS=http://localhost:8888,http://localhost:8080,http://localhost:55667,http://172.17.3.46:8082,http://172.17.3.46:8081,http://172.17.3.46:8080,http://10.33.93.199:8080,http://120.76.194.171,http://120.76.194.171:55667


### Run port
server.port=8080
if you wanted to change this port，remember to change it in the vue code！


### database  ---- addmissiondb 's  sql please see the   db.sql
spring.datasource.url=jdbc:mysql://localhost:3306/addmissiondb?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false
spring.datasource.username=root
spring.datasource.password=123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
