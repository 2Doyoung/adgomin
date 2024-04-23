package com.adgomin.chat.controller;

import com.adgomin.chat.entity.ChatMessageEntity;
import com.adgomin.chat.entity.ChatRoomEntity;
import com.adgomin.chat.service.ChatService;
import com.adgomin.chat.vo.ChatRoomVO;
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
    public ModelAndView chat(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, @RequestParam(value = "chatRoomOrder", required = false) Integer chatRoomOrder) {
        ModelAndView modelAndView = null;
        if(joinVO != null) {
            modelAndView = new ModelAndView("chat/chat");

            ChatRoomEntity[] chatRoomList = this.chatService.getChatRoom(joinVO.getUserOrder());

            for(int i = 0; i < chatRoomList.length; i++) {
                if(chatRoomList[i].getReceiverOrder() == joinVO.getUserOrder()) {
                    chatRoomList[i].setPartnerNickname(chatRoomList[i].getSenderNickname());
                    chatRoomList[i].setPartnerOrder(chatRoomList[i].getSenderOrder());
                } else if(chatRoomList[i].getSenderOrder() == joinVO.getUserOrder()) {
                    chatRoomList[i].setPartnerNickname(chatRoomList[i].getReceiverNickname());
                    chatRoomList[i].setPartnerOrder(chatRoomList[i].getReceiverOrder());
                }

                ChatMessageEntity chatMessageEntity = this.chatService.getLastMessage(chatRoomList[i].getChatRoomOrder());
                ChatMessageEntity chatMessageEntityIsRead = this.chatService.getIsRead(joinVO.getUserOrder(), chatRoomList[i].getChatRoomOrder());

                chatRoomList[i].setLastMessage(chatMessageEntity.getMessage());
                if(chatMessageEntityIsRead != null) {
                    chatRoomList[i].setIsRead(chatMessageEntityIsRead.getIsRead());
                } else if(chatMessageEntityIsRead == null) {
                    chatRoomList[i].setIsRead(1);
                }
            }

            modelAndView.addObject("chatRoomList", chatRoomList);

            if(chatRoomOrder != null) {
                int partnerOrder = 0;
                ChatRoomVO chatRoomVO = this.chatService.getChatRoomCount(joinVO.getUserOrder(), chatRoomOrder);
                if(chatRoomVO.getCount() > 0) {
                    ChatRoomEntity chatRoomPartner = this.chatService.getChatRoomPartner(chatRoomOrder);
                    ChatMessageEntity[] chatMessageEntityList = this.chatService.getChatMessage(chatRoomOrder);


                    if(chatRoomPartner.getSenderOrder() == joinVO.getUserOrder()) {
                        partnerOrder = chatRoomPartner.getReceiverOrder();
                    } else if(chatRoomPartner.getSenderOrder() != joinVO.getUserOrder()) {
                        partnerOrder = chatRoomPartner.getSenderOrder();
                    }

                    modelAndView.addObject("userOrder", joinVO.getUserOrder());
                    modelAndView.addObject("chatRoomOrder", chatRoomOrder);
                    modelAndView.addObject("partnerOrder", partnerOrder);
                    modelAndView.addObject("chatMessageEntityList", chatMessageEntityList);
                } else if(chatRoomVO.getCount() == 0) {
                    modelAndView = new ModelAndView("error/error");
                }
            }
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
        ChatRoomEntity chatRoomEntity = this.chatService.getConversationGetChatRoom(joinVO, chatMessageEntity);
        responseObject.put("result", result.name().toLowerCase());
        responseObject.put("chatRoomEntity", chatRoomEntity.getChatRoomOrder());

        return responseObject.toString();
    }

    @PatchMapping("/isRead")
    @ResponseBody
    public String isRead(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, ChatMessageEntity chatMessageEntity) {
        JSONObject responseObject = new JSONObject();
        Enum<?> result = this.chatService.isRead(joinVO, chatMessageEntity);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }
}
