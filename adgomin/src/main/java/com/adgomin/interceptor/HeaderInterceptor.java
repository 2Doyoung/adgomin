package com.adgomin.interceptor;

import com.adgomin.chat.mapper.ChatMapper;
import com.adgomin.chat.vo.ChatMessageVO;
import com.adgomin.join.vo.JoinVO;
import com.adgomin.media.entity.MediaRegisterEntity;
import com.adgomin.media.mapper.MediaMapper;
import com.adgomin.media.vo.MediaRegisterVO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class HeaderInterceptor implements HandlerInterceptor {
    private final ChatMapper chatMapper;
    private final MediaMapper mediaMapper;

    public HeaderInterceptor(ChatMapper chatMapper, MediaMapper mediaMapper) {
        this.chatMapper = chatMapper;
        this.mediaMapper = mediaMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        int userOrder = 0;
        HttpSession session = request.getSession();
        if(session.getAttribute("LOGIN_USER") != null) {
            userOrder = ((JoinVO)session.getAttribute("LOGIN_USER")).getUserOrder();
        }

        ChatMessageVO chatMessageVO = this.chatMapper.getUnReadCount(userOrder);
        MediaRegisterVO mediaRegisterVO = this.mediaMapper.getRefuseCount(userOrder);
        MediaRegisterEntity mediaRegisterEntity = this.mediaMapper.getRefuseMediaOrder(userOrder);

        request.setAttribute("unReadCount", chatMessageVO.getCount());
        request.setAttribute("refuseCount", mediaRegisterVO.getCnt());
        if(mediaRegisterVO.getCnt() > 0) {
            request.setAttribute("refuseMediaOrder", mediaRegisterEntity.getMediaOrder());
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        int userOrder = 0;
        HttpSession session = request.getSession();
        if(session.getAttribute("LOGIN_USER") != null) {
            userOrder = ((JoinVO)session.getAttribute("LOGIN_USER")).getUserOrder();
        }

        ChatMessageVO chatMessageVO = this.chatMapper.getUnReadCount(userOrder);

        request.setAttribute("unReadCount", chatMessageVO.getCount());
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

}
