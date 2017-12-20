# Gift-Registry-System

Introduction:
I have implemented a Gift Registry system, where different users can login and create and share their gift registries with each other. The user can also assign himself to any of the gift items in someone else’s registry. Only the admin user is allowed to add items in the inventory which are then visible to the users.

System Architecture:
The system developed in JAVA with JSP/Servlets framework. There are multiple web-services and Micro-webservices that are being from the client applications depending upon the actions performed by the user.
Components in the architecture:
1. Client Application: This is the application deployed on Web-App server 1. This contains in initial JSP and servlets corresponding to each user functionality.
2. Web-service: This application is deployed on Web-App server 2. This contains all the REST web-services that are being called from the servlets in the client application. Further it calls the micro-service application deployed on the same server.
3. Micro- service: This application is deployed on Web-App server 2. This contains the REST micro-services that are called by the Web-services deployed on the same server. This application calls the database to fetch the information requested.
4. Database: For this system, we have used MySQL database, that contains all the information regarding the users, gift items and inventory.
I have used Apache Tomcat server version 8.0 for this system. Two instances of the server are created and the corresponding applications are deployed on them.
All the applications are build using Maven and the corresponding dependencies are included in POM.xml.

Technology Used:
I have implemented the project using Java/Servlets framework. The front-end of the application is developed using:
1. JAVA
2. Servlets
3. Ajax
4. JQuery
5. CSS
The Application containing the Webservices and Microservices are also implemented in a similar fashion with JAVA and Servlets. The calls made from client side to these applications are through REST webservices.
The backend database containing the data for the application is implemented using MySQL database. We have used this architecture as we believe the architecture of servlets and JSP is easier to implement and integrate with webservices and database.

Server Details:
I have created two instances of Apache Tomcat v8.0. We have deployed the client application on the first instance and the projects for Webservices and Microservices are deployed on the second instance.

User Authentication Process:
User authentication is done before implementing any functionality i.e. implementing any webservice. For authentication, following steps are performed:
1. When the client application calls the Webservice application, encrypted used id is send along with the request. At the webservice end, the encrypted user id is decrypted, and if matched with the user id, the request is processed.
2. When the request is send to Microservice from Webservice, encrypted user id is send along with the request. At the microservice level, the user id is decrypted and if the same user id is present in the database, the request is processed and the result is returned back to the microservice.


Features Implemented:
1. TLS/SSL: Whenever we pass confidential information across servers, it is always advised that we use a secure transport connection. For our project security is implemented in transport layer across the service calls using self-signed certificate and configuring it in Tomcat server.xml file.
2. Memcached: Caching techniques are used for fast processing of redundant requests and reducing the load on server. For this project, I have used Memcached for implementing caching. Memcached uses a Hash Table that stores the information using a key value pair. If requested data is found in the Memcached, then it is a cache hit else it is a cache miss.
In this project, I have implemented Memcached in Webservice application for fetching the user registry list. If the user views a particular registry present in his profile, then it is saved in cache memory so that next time when he views the same registry, instead of a database call, the data is fetched from the server.
3. Compression: While transferring information/data over server, it is always advisable to compress the data so that faster processing can take place and for better usage of available bandwidth. Compression is implemented by making changes in the server.xml file in server configuration.
In this project, I have implement compression while sending the data from servlets, present in the client application, till the request reaches the database.


Database Details:
I have implemented MySQL database for this project. The initial setup of database is implemented in the code, the user need not setup the database tables. The user just need to have a database with the name ‘gift’ present in the MySQL in the system. The following tables are present in the database:
Database Tables
Gift_registry
Items
User_info
User_registry
Users
visible

All the information/data used in the implementation of services in the project is stored in these tables.
• Gift_registry contains the registry details for each registry created by the user.
• Items table have all the available items in the inventory. Whenever admin adds any item, it is stored in this table.
• User_info contains the details of the user that is used in register new user process, update process and status of registry for each user.
• User_registry table maps the registries present for each user with the items containing in it.
• Users contain the confidential information for each user i.e. userid and password.
• Visible table describes the registry_id present for each user_id.
