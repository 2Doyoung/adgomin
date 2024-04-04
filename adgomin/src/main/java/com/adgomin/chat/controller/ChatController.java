package com.adgomin.chat.controller;

import com.adgomin.chat.entity.ChatMessageEntity;
import com.adgomin.chat.service.ChatService;
import com.adgomin.join.vo.JoinVO;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app")
public class ChatController {
    private final ChatService chatService;

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatController(ChatService chatService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping(value = "/chat")
    public ModelAndView chat(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, @RequestParam(value = "senderOrder", required = false) String senderOrder, @RequestParam(value = "receiverOrder", required = false) String receiverOrder) {
        ModelAndView modelAndView = null;
        if(joinVO != null) {
            modelAndView = new ModelAndView("chat/chat");
        } else {
            modelAndView = new ModelAndView("error/error");
        }

        return  modelAndView;
    }

    @MessageMapping("/send")
    @ResponseBody
    public void sendMessage(@RequestBody ChatMessageEntity message) {
        chatService.sendMessage(message);
        messagingTemplate.convertAndSend("/user/queue/messages", message);
    }

    @PatchMapping("/conversation")
    @ResponseBody
    public String appConversation(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, ChatMessageEntity chatMessageEntity) {
        JSONObject responseObject = new JSONObject();
        Enum<?> result = this.chatService.appConversation(joinVO, chatMessageEntity);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }
}
