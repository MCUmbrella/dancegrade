package vip.floatationdevice.dancegrade.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vip.floatationdevice.dancegrade.data.CommonMappedResult;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorJsonController implements ErrorController
{
    @RequestMapping("/error")
    @ResponseBody
    public CommonMappedResult errorAction(HttpServletRequest request)
    {
        return new CommonMappedResult(
                (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE),
                (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE),
                request.getAttribute(RequestDispatcher.ERROR_EXCEPTION)
        );
    }
}
