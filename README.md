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
- Users are able to communicate with each other over a public chat room that stores the last 25 messages that were sent and keeps track of those who are logged in.  
<br />To Do Features
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
- Sixth, you will need to start up the Client Server
  - Open Visual Studio Code
  - Select "Open folder" and navigate to where you have the repository
  - In the repository, select the AngularClient folder then open Project 2 folder in Visual Studio Code
  - Once the project is open, open a terminal in Visual Studio Code and cd into the Project 2 folder if it is not already
  - Run the command "npm install" and wait for it to finish
  - (Optional) if once compilation is complete, if the message states "npm audit -fix" is needed then run that command
  - Run the command "ng serve -o" in the terminal and wait for the Client-Side server to start which should open the Client side website in the default browser.

## Usage
  1. On the login page (at localhost:9001 or the "ip address":"port number" if it was deployed or the port was changed in all endpoints), the User can click the button "Sign Up" which will cause a menu to appear where the User may enter a username, an email, and a password. Once it is submitted (and the username is available and an email was inputted), the User will be moved back to the login menu which will have their username and password already filled in.
  2. Once the user logs in, they will be sent to a home page where they can submit posts(that can have text messages, images, and/or videos)
    <br />1. All posts require that the User includes some kind of text in the Post tab of the new Post form before the "Post" button will successfully submit a new Post
    <br />2. If the User wants to include an image, they will select the "Image" tab and click "Browse" then navigate to the image they want to upload and select it. If they include a text message in the Post Tab, they can press the "Post" button to submit the new Post.
    <br />3. If the User wants to include a video, they will select the "Video" tab and enter the video's id in YouTube into the input video. If they include a text message in the Post Tab, they can press the "Post" button to submit the new Post with a video.
  3. Below the Form for New Posts, all previous posts can be viewed as the User scrolls down/up
  4. If the User wishes to Like a post they see, they can press the button "Like" to give a Post a Like and press it again to remove the Like.
  5. If a User wishes to Comment on a post, they can enter text in the field marked "Write a comment..." and then click on the Smiling Face Button to submit the Comment.
  6. If the User were to use the search by where the "Timeline" Heading is located, they can filter the posts by the content of the post (if a user inputs "Fish" into the search bar, all posts with "Fish" in the text area will appear and only posts with "Fish").
  7. At the top of the page is a navigation bar that can direct the User to different pages
    <br />1. By clicking "Home" the user will be directed to the home page where they can see all the posts.
    <br />2. By clicking "Profile" the user will be directed to their Profile page where they can see the posts they submitted and if they go to the "Profile Setting" tab, they can edit their profile information (Adding first and last name, uploading a profile picture, adding a description/About Me section, and updating their password if they select "User Password" and enter the new password twice and their old password).
    <br />3. By clicking "Chat Room", the User is directed to the Chat Room feature
    <br />4. By clicking "User Search, the User is directed to a search engine where they can search for other user's profiles where they can see their About Me information and their past Posts.
    <br />5. By clicking on the icon(which will have the User's first/last name and their profile picture if one was uploaded) on the far right of the navigation bar, a dropdown menu appears that will give the user the option to either go to their profile or to logout of their account.
  8. Once in the Profile page, the user has two tabs "My Posts" and "Profile Setting"; the "My Posts" Tab will give the User the ability to view the Posts they had previously create while the "Profile Setting" will give the User a form that will allow them to update information about themselves along with allowing them to update their password. They submit changes by clicking on the "Update" button.
  <br /> 1. To update the password, the User will click on the switch labeled "User Password" and input their new password twice in the respective files along with their old password and then click the "Update" button. If the user reset their password view email, they would need to input the temporary password they received in an email.
  10. 
    

## Contributors
 - Michael Loutfi
 - Sam Jenkins
 - Suliman Sam
 - Rafael Malespin
