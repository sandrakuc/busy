package busy.gui;

import busy.database.User;
import busy.database.repos.UserRepo;
import busy.gui.flashmessagemanager.FlashMessageManager;
import busy.gui.flashmessagemanager.FlashMessageManagerMessageContents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static busy.gui.flashmessagemanager.FlashMessageManager.FlashMessage.Type.ERROR;
import static busy.gui.flashmessagemanager.FlashMessageManager.FlashMessage.Type.INFO;


@Controller
public class LoggingController {

    @Autowired
    FlashMessageManager flashMessageManager;

    @Autowired
    UserRepo userRepo;

    private static final String NAME = "name";
    private static final String USER = "user";
    private static final String SURNAME = "surname";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "pass";
    private static final String PHONE = "phone";
    private static final String EMAIL = "email";
    private static final String REPEAT_PASSWORD = "repeatPassword";

    private User getUserByRequest(HttpServletRequest request){
        String login = request.getParameter(LOGIN);
        User user = userRepo.getByLogin(login);
        return user;
    }

    private boolean isUserLogged(HttpSession session){
        return session.getAttribute(USER) != null;
    }

    private boolean isThereUser(User user){
        return user != null;
    }

    private String check(HttpServletRequest request, String parameter){
        return (request.getParameter(parameter)!= null) ? request.getParameter(parameter):null;
    }

    private boolean checkNotNullParameter(HttpServletRequest request, String parameter){
        return request.getParameter(parameter) != null;
    }

    private boolean checkPasswordEquals(HttpServletRequest request, String password){
        return request.getParameter(PASSWORD).equals(password);
    }

    private boolean checkPasswordIsOk(HttpServletRequest request){
        String password = request.getParameter(REPEAT_PASSWORD);
        return  checkNotNullParameter(request, PASSWORD) && checkNotNullParameter(request, REPEAT_PASSWORD) && checkPasswordEquals(request, password);
    }

    private String getPassword(HttpServletRequest request){
        return checkPasswordIsOk(request)?request.getParameter(PASSWORD):null;
    }

    private boolean isUserReadyToSave(User user, String login, String email){
        return user.isNotBlank() && !isThereUser(userRepo.getByLogin(login)) && !isThereUser(userRepo.getByEmail(email));
    }

    private User buildUser(String name, String surname, String email, String login, String password, String phoneNumber){
        return new User().builder()
                .name(name)
                .surname(surname)
                .email(email)
                .login(login)
                .password(password)
                .phoneNumber(phoneNumber)
                .build();
    }

    private String getSuccessLoggedMessage(String login){
        return FlashMessageManagerMessageContents.LOGGED + login;
    }


    @RequestMapping("login")
    public String login(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        if(isUserLogged(session)) {
            flashMessageManager.addMessage(FlashMessageManagerMessageContents.ALREADY_LOGGED, ERROR);
            return "index";
        }
        if(checkNotNullParameter(request, LOGIN)){
            User user = getUserByRequest(request);
            if(!isThereUser(user)){
                flashMessageManager.addMessage(FlashMessageManagerMessageContents.INVALID_LOGIN_OR_PASSWORD, ERROR);
            }else if(checkPasswordEquals(request, user.getPassword())){
                flashMessageManager.addMessage(getSuccessLoggedMessage(user.getLogin()), INFO);
                session.setAttribute("user", user);
                return "index";
            }else{
                flashMessageManager.addMessage(FlashMessageManagerMessageContents.INVALID_LOGIN_OR_PASSWORD, ERROR);
            }
        }
        model.addAttribute("flashMessageManager", flashMessageManager);
        return "loging/login";
    }

    @RequestMapping("registration")
    public String registration(HttpServletRequest request, Model model){
        String name = check(request, NAME);
        String surname = check(request, SURNAME);
        String email = check(request, EMAIL);
        if(isThereUser(userRepo.getByEmail(email))){
            flashMessageManager.addMessage(FlashMessageManagerMessageContents.EMAIL_TAKEN, ERROR);
        }
        String login = check(request, LOGIN);
        if(isThereUser(userRepo.getByLogin(login))){
            flashMessageManager.addMessage(FlashMessageManagerMessageContents.LOGIN_TAKEN, ERROR);
        }
        String password = getPassword(request);
        if(checkPasswordIsOk(request)){
            flashMessageManager.addMessage(FlashMessageManagerMessageContents.DIFFERENT_PASSWORDS, ERROR);
        }
        String phoneNumber = check(request, PHONE);
        User user = buildUser(name, surname, email, login, password, phoneNumber);
        if(isUserReadyToSave(user, login, email)) {
            userRepo.save(user);
            return "loging/login";
        }
        model.addAttribute("flashMessageManager", flashMessageManager);
        return "loging/registration";
    }
}
