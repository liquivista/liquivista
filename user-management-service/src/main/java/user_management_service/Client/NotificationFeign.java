package user_management_service.Client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import user_management_service.dto.NotificationDto;

@FeignClient(value = "notificationFeign" , url = "http://localhost:9804/notification-management")
public interface NotificationFeign {

    @GetMapping("/send-email")
    public String sendEmail(@RequestBody NotificationDto notificationDto);
}
