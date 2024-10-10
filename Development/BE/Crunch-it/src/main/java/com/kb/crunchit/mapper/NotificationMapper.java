package com.kb.crunchit.mapper;

import com.kb.crunchit.dto.request.NotificationRequestDTO;
import com.kb.crunchit.entity.Notification;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface NotificationMapper {

    //특정 사용자 알림 목록 가져오는
    List<Notification> getNotificationById(int userId);

    void insertNotification(NotificationRequestDTO notificationRequestDTO);
}
