package org.guy.rpg.dwg.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Used to display custom error templates.
 * 
 * Remember that you must add the error path to the list of
 *   unsecured pages on the AuthenticationInterceptor.
 * 
 * @author Guy
 */
@Controller
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @Autowired
    ErrorAttributes errorAttributes;
    
    @RequestMapping(PATH)
    public String error() {
        return "Error handling";
    }
    
    @RequestMapping("/404")
    public String get404(HttpServletRequest request, Model model) {
        return "errors/404";
    }
    
    @RequestMapping("/500")
    public String get500(HttpServletRequest request, Model model) {
    	RequestAttributes requestAttributes = new ServletRequestAttributes(request);
    	Map<String, Object> errorMap = errorAttributes.getErrorAttributes(requestAttributes, true);
    	
    	model.addAttribute("error", errorAttributes.getError(requestAttributes));
    	model.addAttribute("trace", errorMap.get("trace"));
        return "errors/500";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
