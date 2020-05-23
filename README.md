# Task Manager Web Application

Simple Web Application for task managment.

## Designed by Netcracker students
  - Ilya Kozlov
  - Andrei Riazanov
  - Vladislav Selickiy
  
## Additional information  

See ours [issues](https://github.com/Desire456/task-manager-web-app/issues) for check development process.

### Issue priority

![alt text](https://user-images.githubusercontent.com/33430830/73350679-ef98e300-42a6-11ea-8093-a34c6d227afc.png "Issue priority")

## Releases

Non yet

## Export and import strategies by type and id
                           Export strategies
              Journal                         Task
           1 - only journals               1 - only tasks
           2 - journals with tasks

                           Import strategies
               Journal                         Task
           1 - overwrite                   1 - overwrite
           2 - ignore                      2 - ignore
           3 - conflict                    3 - conflict
## How to run our application 
 - Download TomEE plus Apache Server version 8.0.1
 - Edit your Tomcat configuration (make start page url: "http://localhost:8081/start")
 - Move "script.sql" from resources package to Tomcat directory "bin/jdbc", "queryResult.xsd" to "bin/xml", 
  "config.properties" to "bin/properties"
  - Add to {Tomcat directory}/lib this jars: hibernate-core-5.4.14.Final, hibernate-commons-annotations-5.1.0.Final, 
 jboss-logging-3.3.0.Final, antlr-2.7.7, byte-buddy-1.10.7, classmate-1.5.1, dom4j-1.6.1,
  hibernate-entitymanager-5.4.12.Final, xalan-2.7.2, serializer-2.7.2. To download jars you can use https://mvnrepository.com/
   - Delete xalan-2.7.2.jar, serializer-2.7.2.jar from classpath