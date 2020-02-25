package practice.sr.todoapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import practice.sr.todoapp.web.support.ConnectedClientCountBroadcaster;

@Controller
public class OnlineUsersCounterController {

    private ConnectedClientCountBroadcaster broadcaster = new ConnectedClientCountBroadcaster();

    @GetMapping(value = "/stream/online-users-counter", produces = "text/event-stream")
    public SseEmitter counter() {
        return broadcaster.subscribe();
    }

}
