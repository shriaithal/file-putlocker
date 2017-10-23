# CMPE-281 SJSU Course CMPE-281 Projects and POC
# FilePutlocker

### Introduction
Project 1: File Putlocker
University Name: http://www.sjsu.edu/
Course: [Cloud Technologies](http://info.sjsu.edu/web-dbgen/catalog/courses/CMPE281.html)
Professor: [Sanjay Garje](https://www.linkedin.com/in/sanjaygarje/)
ISA: [Divyankitha Urs](https://www.linkedin.com/in/divyankithaurs/)
Student: [Anushri Srinath Aithal](https://www.linkedin.com/in/anushri-aithal/)

### File Putlocker Introduction
File Putlocker, is a web application hosted on AWS cloud which intends to provide Authorized users a portal to securely maintain their files on cloud. 
The project illustrates the use of various AWS cloud components in developing a 3 Tier Web Application. The application manages various components to provide a highly available, scalable, cost effective solution to securely back up data on to Amazon S3. File Putlocker application leverages AWS auto scaling functionality to provide seamless experience during peak load times. The application also monitors the health of the EC2 associated with auto scale group using Cloud Watch, AWS Lambda and SNS.
Users can perform the following activities on the portal
1.	Upload file to S3
2.	Download Files from S3 via Cloud Front
3.	Delete Files on S3
4.	Edit the already uploaded files.
All the above operations are securely handled using AWS VPC and S3 Transfer Acceleration. IAM users with appropriate roles are in place to ensure a secure cloud experience.

- The general workflow to upload, edit, download, delete file is as below
![Fig.1 User Workflow](https://user-images.githubusercontent.com/1582196/31876375-41c890e2-b787-11e7-8af5-d20f73bff7d3.png)

- AWS Architecture of the project is as below
![Fig.2 AWS Architecture Diagram](https://user-images.githubusercontent.com/1582196/31901238-b7f6495a-b7d5-11e7-8abd-98bfb43818c2.png)

### Feature List
1.	Sign up form for new user to create an account. A new user record is created in AWS RDS MySQL. If an already existing user tries to sign up, he is prevented in doing the same.
2.	Login Page to allow only authorized users to login. Performs validation for username and password match.
3.	Home Screen that displays information about User, his/her first name, last name. A widget to upload files. Also, displays all the files previously uploaded with their description, file size, created time, updated time and allows operations to download, delete and edit files. Retrieval of all the metadata is through RDS database.
4.	Allow authorized users to upload file on AWS S3. The file to be uploaded cannot exceed a file size of 10MB.
5.	Allow authorized users to download file from S3 via Cloud Front they have previously uploaded
6.	Allow authorized user to download the files previously uploaded.
7.	Allow authorized users to update their existing file. Update works as download the file, make changes and reupload the file without changing the file name.
8.	User can Logout using the logout button.

### AWS Components Leveraged
1.	EC2: Create an EC2 instance with all project artifacts and use that to obtain an AMI for the AutoScale Group deployment.
2.	AutoScaling Group: To achieve high-available and scalable solution configure auto scale group with a desired instance of 1 and max instance of 2. This is configurable based on requirement and traffic the website attracts.
3.	Classic Load Balancer: Load Balancer point to autoscale group so that it handles optimal load on all the EC2 instances associated with the group.
4.	S3: Used to upload and maintain user files.
5.	S3 Transfer Acceleration: S3 bucket is enabled with Transfer Acceleration to enable faster and secure transfer of files to S3.
6.	Standard Infrequent Access (IA): Lifecycle policies are updated on S3 to move files to IA after 75 days.
7.	Amazon Glacier: Lifecycle policies are enabled on S3 bucket to move files to Amazon Glacier after 365 days.
8.	CloudFront: File download is done using CloudFront. The minimum TTL for CloudFront is setup as 1minute.
9.	RDS: MySQL instance is created to maintain user data and file metadata.
10.	CloudWatch: Cloud watch is used to monitor any additional spin up or termination of EC2 in the autoscale group. It triggers Lambda when such a event occurs. Cloudwatch alarms are configured to send notifications via SNS on S3 and ELB healthchecks.
11.	Lambda: A python program is configured to invoke SNS topic to send notifications.
12.	SNS: Configured to send email to all the subscribers for the topic.
13.	Route53: Domain Name Server that resolves IP address for the application domain www.shriaithal-sjsu.com.

### Deployment Instructions
1.	Prerequisite Softwares: Java8 Runtime, Maven, Apache Tomcat 8.5, Eclipse, MySQL 5.7.
2.	Download code from Github.
3.	Run DBScript.sql on MySQL instance.
4.	Update applications.properties with MySQL URL, Username and Password.
5.	Update application.properties with S3 access key and secret.
6.	Run the instruction mvn clean install on project pom.xml
7.	Change the Tomcat server.xml (in conf/server.xml) <host> to run your application as the root application
8.	Deploy the cloud.war folder in Tomcat webapps folder.
9.	Run the startup.sh/startup.bat in bin folder
10.	You can now access the application at http://localhost:8080/

### Sample Screenshots
- Sign up Page
![Fig.3 Sign Up Form](https://user-images.githubusercontent.com/1582196/31901163-85ce5116-b7d5-11e7-8f6a-3c9086dbb26a.png)
- Login Page
![Fig.4 Login Form](https://user-images.githubusercontent.com/1582196/31901172-8be40122-b7d5-11e7-8fee-39bcb4c2ddd6.png)
- Home File with Upload File Demo
![Fig.5 Home File with Upload File Demo](https://user-images.githubusercontent.com/1582196/31901182-91e18360-b7d5-11e7-9ba2-155e46753119.png)
- Download File
![Fig.6 Download File](https://user-images.githubusercontent.com/1582196/31901204-9f0a70e2-b7d5-11e7-8974-0c97666c4aa6.png)
