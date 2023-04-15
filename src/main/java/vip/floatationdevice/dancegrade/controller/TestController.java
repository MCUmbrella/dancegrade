package vip.floatationdevice.dancegrade.controller;

import org.springframework.web.bind.annotation.*;
import vip.floatationdevice.dancegrade.data.CommonMappedResult;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class TestController
{
    @RequestMapping("/test")
    @ResponseBody
    public CommonMappedResult testAction()
    {
        return new CommonMappedResult(0, "Hello world!", null);
    }

    @GetMapping("/echo")
    @ResponseBody
    public CommonMappedResult echoAction(@RequestParam(value = "message") String message)
    {
        return new CommonMappedResult(message.length(), message, null);
    }

    @PostMapping(value = "/greet", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CommonMappedResult greetAction(@RequestBody Map<String, Object> requestBody)
    {
        return new CommonMappedResult(0, "OK", "Hello " + requestBody.get("name"));
    }
}
