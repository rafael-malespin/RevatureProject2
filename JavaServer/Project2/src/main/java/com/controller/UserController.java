package com.controller;

import com.model.CustomResponseMessage;
import com.model.User;
import com.service.UserService;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    final static Logger loggy = Logger.getLogger("primaryLogger");
    static {
        loggy.setLevel(Level.ALL);
        //loggy.setLevel(Level.ERROR);
    }


    /**
     * Api endpoint that returns an Array list of User objects from the service layer.
     * @return Array list of all registered User objects in the HTTP response body.
     */
    @GetMapping(value="getAllUsers")
    public List<User> getAllUsers(){
        loggy.info("All Users was retrieved from serviceLayer");
        return userService.selectAllUsers();
    }

    /**
     * Api endpoint that returns a User object from the service layer.
     * @param userId User identifier (Int).
     * @return User object in the HTTP response.
     */
    @GetMapping(value = "/getUserById/{userId}")
    public User getUserById(@PathVariable("userId") int userId){
        loggy.info("A user with id: "+userId+" was retrieved.");
        return userService.selectById(userId);
    }

    /**
     * Api endpoint that receives a User object from HTTP request body and returns
     * User object based on the username and password provided.
     * @param user User object
     * @return User object in the HTTP response.
     */
    @PostMapping(value = "/getByUserAndPass")
    public User getUserByUserAndPass(@RequestBody User user){
        loggy.info("An attempt to retrieve a user with username: "+user.getUsername()+" with a password.");
        return userService.selectByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    /**
     * Api endpoint that receives User object to use username to retreive User object from service layer.
     * @param user User object from HTTP request.
     * @return User object based on the username.
     */
    @PostMapping(value = "/getByUsername")
    public User getUserByUsername(@RequestBody User user) {
        loggy.info("An attempt to retrieve a user with username: "+user.getUsername()+".");
        return userService.selectByUsername(user.getUsername());
    }

    /**
     * Api endpoint that checks User object's previous password and returns a Custom Response Message in the
     * HTTP response body.
     * @param user User object
     * @return Custom response message (string)
     */
    @PostMapping(value = "/checkOldPassword")
    public CustomResponseMessage checkOldPassword(@RequestBody User user) {

        if(userService.checkOldPassword(user.getUsername(),user.getPassword())) {
            loggy.info("The valid old password of a user with username: "+user.getUsername()+" was correct.");
            return new CustomResponseMessage("yes");
        }else{
            loggy.info("The old password of a user with username: "+user.getUsername()+" is not correct.");
            return null;
        }
    }

    /**
     * Api endpoint that inserts User object into the application depending on whether they exists or not.
     * @param user User object.
     * @return Custom response message (string).
     */
    @PostMapping(value = "/insertNewUser")
    public CustomResponseMessage insertNewUser(@RequestBody User user){
        User alreadyExists = getUserByUsername(user);
        if(alreadyExists == null) {
            userService.insert(user);
            loggy.info("The successful creation of a user with username: "+user.getUsername()+".");
            return new CustomResponseMessage("User was created");
        }
        else {
            loggy.info("The failed creation of a user with username: "+user.getUsername()+".");
            return new CustomResponseMessage("Username already taken");
        }
    }

    /**
     * Api endpoint that updates the User in the application. Receives updated User object
     * and passes the information to the service layer. Returns message when the user is updated
     * in the application via response body.
     * @param session HTTP Session of current logged in user.
     * @param user User object.
     * @return Custom response message (string)
     */
    @PutMapping(value = "/updateUser")
    public CustomResponseMessage updateUser(HttpSession session,@RequestBody User user){
        if((User)session.getAttribute("loggedInUser")!=null){
            User current=((User)session.getAttribute("loggedInUser"));
            if(!current.getPassword().equals(user.getPassword())){
                userService.updateWithPassword(user);
                loggy.info("The successful update(with password) of a user with username: "+user.getUsername()+".");
            }
            else {
                loggy.info("The successful update of a user with username: "+user.getUsername()+".");
                userService.update(user);
            }
            int id = current.getUserId();
            User updatedVersion=getUserById(id);
            session.setAttribute("loggedInUser",updatedVersion);
            return new CustomResponseMessage("User was updated");
        }
        loggy.info("The failed update of a user with username: "+user.getUsername()+".");
        return new CustomResponseMessage("User was not updated");

    }

    /**
     * Api endpoint that will remove a User from the application. Returns message when the user
     * is deleted through the HTTP response body.
     * @param user User object.
     * @return Custom response message (string).
     */
    @PostMapping(value = "/deleteUser")
    public CustomResponseMessage deleteUser(@RequestBody User user){
        userService.delete(user);
        loggy.info("The deletion of a user with username: "+user.getUsername()+".");
        return new CustomResponseMessage("User was deleted");
    }

    /**
     * Api endpoint that retrieves the logged in user from the HTTP session.
     * @param session HTTP session
     * @return User object
     */
    @GetMapping(value = "/getLoggedInUser")
    public User getLoggedInUser(HttpSession session){
        //try to get the most updated version of the user
        if((User)session.getAttribute("loggedInUser")!=null){
            int id = ((User)session.getAttribute("loggedInUser")).getUserId();
            User updatedVersion=userService.selectById(id);
            session.setAttribute("loggedInUser",updatedVersion);
            loggy.info("The successful retrieval of the loggedInUser");
            return (User) session.getAttribute("loggedInUser");
        }
        else{
            loggy.info("The failed retrieval of the loggedInUser");
            return null;
        }
    }

    /**
     * Api endpoint for the main landing page of application that allows user to login.
     * @param session HTTP session
     * @param currentUser User object of the current logged in user.
     * @return User object
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public User login(HttpSession session, @RequestBody User currentUser){
        User retrievedUser = userService.selectByUsernameAndPassword(currentUser.getUsername(),currentUser.getPassword());
        session.setAttribute("loggedInUser",retrievedUser);
        loggy.info("The successful login of the user:"+currentUser.getUsername());
        return retrievedUser;
    }

    /**
     * Api endpoint that logs out the user from the application. Redirects to landing page.
     * @param myReq HTTP servlet request
     * @return Custom Response message (string)
     */
    @GetMapping(value = "logout")
    public CustomResponseMessage logout(HttpServletRequest myReq){
        HttpSession userSession = myReq.getSession();
        loggy.info("The successful logout of the session:"+userSession);
        userSession.invalidate();
        return new CustomResponseMessage("Logged out");
    }

    /**
     * Api endpoint that sends an email to User's email with a randomly generated password.
     * @param userName Username of User current user (string).
     * @return Custom response message (string).
     */
    @PostMapping(value = "/resetPassword")
    public CustomResponseMessage sendEmail(@RequestBody String userName){
        User tempUser = new User();
        tempUser.setUsername(userName);
        User user = getUserByUsername(tempUser);//retrieving the user in the database with the username
        if(user ==null){
            loggy.warn("The failed sending of an email due to the user not existing");
            return new CustomResponseMessage("Sending Email Failed");
        }
        String newPassword = generateTempPassword(20);//creating new random password
        user.setPassword(newPassword);
        userService.updateWithPassword(user);//updating the user in the database with temp password
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(
                    new DefaultAuthenticator(System.getenv("EMAILING_ADDRESS")//
                            , System.getenv("EMAILING_PASSWORD")));//
            email.setSSLOnConnect(true);
            email.setFrom(System.getenv("EMAILING_ADDRESS"));
            email.setSubject("Reset Password for Toph Link");
            email.setMsg("Your new password: \"" + newPassword+"\"");
            email.addTo(user.getEmailAddress());
            email.send(); // will throw email-exception if something is wrong
        } catch (EmailException e) {
            e.printStackTrace();
            loggy.warn("The failed sending of an email");
            return new CustomResponseMessage("Sending Email Failed");
        }
        loggy.info("Email sent to:"+user.getEmailAddress());
        return new CustomResponseMessage("Sending Email Success");
    }

    /**
     * Password generating method that generates a random sequence of characters for the reset
     * password.
     * @param length length of password (int).
     * @return random string used as reset password.
     */
    public String generateTempPassword(int length){
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        // each iteration of the loop randomly chooses a character from the given
        // ASCII range and appends it to the `StringBuilder` instance

        for (int i = 0; i < length; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }


    public UserController() {
    }


    public UserController(UserService userService) {
        this.userService = userService;
    }


    public UserService getUserService() {
        return userService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
