package com.adgomin.chat.controller;

import com.adgomin.chat.entity.ChatEntity;
import com.adgomin.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @MessageMapping("/send")
    @ResponseBody
    public void sendMessage(@RequestBody ChatEntity message) {
        chatService.sendMessage(message);
        messagingTemplate.convertAndSend("/user/queue/messages", message);
    }

    @GetMapping("/receive/{receiverOrder}")
    @ResponseBody
    public List<ChatEntity> receiveMessages(@PathVariable int receiverOrder) {
        return chatService.getMessagesByReceiverOrder(receiverOrder);
    }
}
