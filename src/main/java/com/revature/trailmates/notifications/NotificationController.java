package com.revature.trailmates.notifications;

import com.revature.trailmates.auth.TokenService;
import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.notifications.dto.NewNotificationRequest;
import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Inject
    private final NotificationService notificationService;

    @Autowired
    private TokenService tokenService;

    @Inject
    @Autowired
    public NotificationController(NotificationService notificationService, TokenService tokenService) {
        this.notificationService = notificationService;
        this.tokenService = tokenService;
    }

    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody void addNotification(@RequestBody NewNotificationRequest request, @RequestHeader("Authorization") String token) {
        Principal user = tokenService.noTokenThrow(token);
        notificationService.addNotification(request, user.getId());
    }

    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Notification> getAllNotificationsFromUser(@RequestHeader("Authorization") String token) {
        Principal user = tokenService.noTokenThrow(token);
        return notificationService.getAllNotificationsFromUser(user.getId());
    }

    @CrossOrigin
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Optional<Notification> getNotification(@PathVariable("id") String id, @RequestHeader("Authorization") String token) {
        Principal user = tokenService.noTokenThrow(token);
        return notificationService.getNotification(id);
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody void deleteFriend(@PathVariable("id") String id, @RequestHeader("Authorization") String token) {
        Principal user = tokenService.noTokenThrow(token);
        notificationService.deleteNotification(id);
    }

}
