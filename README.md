# Revature Social Network v2
## Project Description
A social media platform where clients may sign up for an account and be able to view and create posts that other clients may view. Clients may "Like" and "Comment" on any Post they choose.  Clients will be able to search other profiles and view the specific user's profile and their previous posts.
## Technologies Used
- Java
- Spring
- SQL
- Hibernate
- HTML
- CSS
- JavaScript
- Log4J
- JUnit
- Angular
- EC2
- Jenkins
- S3
## Features
Current Features
- Users can register a social media account.
- Users can create posts with either text, images(with a text) or a youtube video (with a text).
- Users can view previously created posts.
- Users can filter posts by creator and by content.
- Users can search for other users' profiles where they view their public information (name and description) and their previous posts.
- Users can give a link to a post and then remove it.
- Users can add comments to posts
- Users can enter their profile to view their old posts, view their public information, and edit their public information (including uploading a profile picture). They can also updated their password after password validation is completed.
- Users can reset their password via email as long as the email they used to register their account is valid.
- Users are able to communicate with each other over a public chat room that stores the last 25 messages that were sent and keeps track of those who are logged in
To-Do Features
- Implement Integration Testing
- Sucessfully deploy project on EC2
- Implement Notifications of Likes and Comments
- Implement Follower features
- Fix bug in chat room: if a user logs out or closes the window while in chat room, they will appear as being online
- Fix bug where dropdown with links to edit profile and logout occasionally displays "null null" instead of the user's name

## Getting Started
- First, you need to clone the project
  - git clone https://github.com/rafael-malespin/RevatureProject2.git
- Second, you will need to setup the database
  1. Open DBeaver (install if not installed) and connect to a RDS database
  2. In the Database Manager, right click the connection and select "Create" then "Database"
  3. Set "Tablespace" to Default and give the database a name
- Third, set up the environment variables
  1. In environment variables, create a variable "TRAINING_DB_NAME" and set its value to be the name of the database that was created in the previous point
  2. create a variable "TRAINING_DB_ENDPOINT" and set its value to be the endpoint of the RDS database that was created in AWS
  3. create a variable "TRAINING_DB_USERNAME" and set its value to be the username of the RDS database that was created in AWS
  4. create a variable "TRAINING_DB_PASSWORD" and set its value to be the password of the RDS database that was created in AWS
  5. create a variable "EMAILING_ADDRESS" and set its value to be the address of an email that is set up to be used for sending emails to others
  6. create a variable "EMAILING_PASSWORD" and set its value to be the password for the previous email
  7. create a variable "AWS_BUCKET" and set its value to be name of the bucket in S3 that is set up to contain images
  8. create a variable "AWS_ID" whose value will be the Access Key for the S3 Bucket
  9. create a variable "AWS_KEY" whose value will be the Secret Access Key for the S3 Bucket
  10. create a variable "AWS_REGION" and set it to be the region where the S3 bucket will be hosted
- Fourth, you will need to open the project in an IDE (preferably IntelliJ 2020.3 or above)
  - Open IntelliJ
  - Click File -> Open
  - Go to where the repository was cloned
  - Select the JavaServer file then the Project 2 file
- Fifth, you will need to setup the tomcat server
  - Download Tomcat 9.0.45 and the Smart Tomcat plugin in IntelliJ 2020.3 or above
  - In add configuration, create a Smart Tomcat configuration
  - In Context Path, push "/toph" or another path as long and you edit the endpoints to swap "/toph" with the change path.  If you are deploying the project on EC2, you may skip this step
  - In Server Port, change the port to be "9001" or another port as long as you change the endpoints in the Angular side of the project
  - Click "Apply" and then "Okay"
  - Run the tomcat server and the Server is be ready to receive requests from the Client
- Sixth

## Usage

## Contributors
 
