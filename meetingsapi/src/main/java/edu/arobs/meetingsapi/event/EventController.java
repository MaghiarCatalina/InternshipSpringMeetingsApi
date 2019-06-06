package edu.arobs.meetingsapi.event;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

//    @PostMapping
//    public Event create(Integer userId, @RequestBody EventDetailsDTO eventDetailsDTO){
//        return eventService.create(userId,eventDetailsDTO);
//    }

    @GetMapping
    public List<EventDTO> viewAll() {
        return eventService.viewAll();
    }

    @GetMapping("/pastEvents")
    public List<EventDTO> viewPastEvents() {
        return eventService.viewFutureOrPastEvents(false);
    }

    @GetMapping("/futureEvents")
    public List<EventDTO> viewFutureEvents() {
        return eventService.viewFutureOrPastEvents(true);
    }

    @GetMapping("/{id}")
    public EventDTO getById(@PathVariable(value = "id") Integer id) {
        return eventService.getByIdDTO(id);
    }

    @PatchMapping("/{id}")
    public EventDTO updateAttendanceListOrFeedback(@PathVariable(value = "id") Integer idEvent, @RequestBody Object o,
                                                   @RequestHeader(value = "Referer", required = false) String referer) {
        if (referer.equals("http://localhost:3000/pastEvents")) {
            return eventService.addFeedback(idEvent, o);
        } else if (referer.equals("http://localhost:3000/futureEvents")) {
            return eventService.addAtendees(idEvent, o);
        } else
            return null;
    }
}
