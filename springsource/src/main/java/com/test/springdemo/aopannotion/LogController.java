package com.test.springdemo.aopannotion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/log")
public class LogController {
    @Autowired
    private LogServiceImpl logService;


    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @LogAnnotation(name=EnumInfo.Positive,eventType = EnumEventType.account_operator)
    public void getLog(String name, @TargetType String value, @TargetType Map<String,String> map){
        System.out.println("name = [" + name + "], value = [" + value + "]"+" map ="+map.size());
        logService.getInfo();
    }
}
