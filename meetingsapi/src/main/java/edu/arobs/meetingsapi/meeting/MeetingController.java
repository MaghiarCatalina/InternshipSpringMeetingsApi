package edu.arobs.meetingsapi.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meetings")
public class MeetingController {

    private final MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping
    public MeetingDTO create(@RequestBody MeetingDTO meeting) {
        return meetingService.create(meeting);
    }

    @PutMapping("/{id}")
    public MeetingDTO update(@PathVariable Integer id, @RequestBody MeetingDTO meeting) {
        return meetingService.update(id, meeting);
    }

    @GetMapping("/{id}")
    public MeetingDTO get(@PathVariable Integer id) {
        return meetingService.getById(id);
    }

    @DeleteMapping("/{id}")
    public MeetingDTO delete(@PathVariable Integer id) {
        return meetingService.delete(id);
    }
}
