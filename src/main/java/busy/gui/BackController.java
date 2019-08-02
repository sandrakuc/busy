package busy.gui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BackController {
    @RequestMapping("backToLogin")
    public String goBack(){
        return "redirect:/login";
    }

}
