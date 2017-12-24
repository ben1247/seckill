package org.seckill.web;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillResult;
import org.seckill.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by yuezhang on 17/12/3.
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageService messageService;


    @RequestMapping(value = "/send" , method = RequestMethod.POST)
    @ResponseBody
    public String send(@RequestParam("message")String message){
        String result = "send success!";
        try {
            messageService.sendMessage(message);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result = "send failure!!!";
        }
        return result;
    }

}
