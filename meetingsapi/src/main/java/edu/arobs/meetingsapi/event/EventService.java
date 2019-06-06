package edu.arobs.meetingsapi.event;

import edu.arobs.meetingsapi.eventdetails.EventDetails;
import edu.arobs.meetingsapi.eventdetails.EventDetailsRepository;
import edu.arobs.meetingsapi.exception.EventDetailsNotFoundException;
import edu.arobs.meetingsapi.exception.EventNotFoundException;
import edu.arobs.meetingsapi.exception.UserNotFoundException;
import edu.arobs.meetingsapi.feedback.Feedback;
import edu.arobs.meetingsapi.feedback.FeedbackDTO;
import edu.arobs.meetingsapi.feedback.FeedbackRepository;
import edu.arobs.meetingsapi.feedback.FeedbackService;
import edu.arobs.meetingsapi.user.User;
import edu.arobs.meetingsapi.user.UserDTO;
import edu.arobs.meetingsapi.user.UserRepository;
import edu.arobs.meetingsapi.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventDetailsRepository eventDetailsRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final FeedbackRepository feedbackRepository;
    private final ModelMapper modelMapper;
    private final FeedbackService feedbackService;

    @Autowired
    public EventService(EventRepository eventRepository, EventDetailsRepository eventDetailsRepository,
                        UserRepository userRepository, FeedbackRepository feedbackRepository, ModelMapper modelMapper,
                        UserService userService, FeedbackService feedbackService) {
        this.eventRepository = eventRepository;
        this.eventDetailsRepository = eventDetailsRepository;
        this.userRepository = userRepository;
        this.feedbackRepository = feedbackRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.feedbackService = feedbackService;
    }

//    @Transactional
//    public Event create(Integer userId, EventDetailsDTO eventDetailsDTO) {
//        Event newEvent = new Event();
//        newEvent.setId(null);
//        newEvent.setUser(userRepository.findById(userId)
//                .orElseThrow(()->new IllegalArgumentException(String.format("User id=? does not exist",userId))));
//        Event savedEvent = eventRepository.save(newEvent);
//
//        eventDetailsService.create(eventDetailsDTO,savedEvent);
//        return savedEvent;
//    }

    @Transactional
    public Event getById(Integer id) {
        return eventRepository.findById(id).orElseThrow(EventNotFoundException::new);
    }

    public EventDTO getByIdDTO(Integer id) {
        Event event = getById(id);
        EventDetails eventDetails = eventDetailsRepository.findById(event.getId())
                .orElseThrow(EventDetailsNotFoundException::new);
        EventDTO eventDTO = new EventDTO();
        modelMapper.map(eventDetails, eventDTO);
        eventDTO.setId(id);
        eventDTO.setUsersId(event.getUser().getId());
        eventDTO.setUsers(userService.getById(event.getUser().getId()));
        //explicitly set the next 2 because they have different names(in DTO & entity) and are not mapped automatically
        eventDTO.setLang(eventDetails.getLanguage());
        eventDTO.setMaxPeople(eventDetails.getMaxPersons());
        LocalDateTime localDateTime = LocalDateTime
                .of(eventDetails.getDate().toLocalDate(), eventDetails.getTime().toLocalTime());
        ZoneId zoneId = ZoneId.systemDefault();
        long epoch = localDateTime.atZone(zoneId).toEpochSecond();
        eventDTO.setTimestamp(epoch);

        //attendance id list
        Set<User> users = event.getUsers();
        if (!users.isEmpty()) {
            for (User user : users) {
                eventDTO.getAttendanceIds().add(user.getId());
            }
        }

        //feedback
        Set<Feedback> feedbacks = feedbackRepository.findByEvent(id);
        for (Feedback feedback : feedbacks) {
            FeedbackDTO feedbackDTO = new FeedbackDTO();
            modelMapper.map(feedback, feedbackDTO);
            eventDTO.getFeedback().add(feedbackDTO);
        }

        return eventDTO;
    }

    @Transactional
    public Event delete(Integer id) {
        Event existingEvent = getById(id);
        eventRepository.deleteById(id);
        return existingEvent;
    }

    @Transactional
    public List<EventDTO> viewAll() {
        List<EventDTO> eventDTOS = new ArrayList<>();
        List<Event> events = (List<Event>) eventRepository.findAll();
        for (Event e : events) {
            EventDetails eventDetails = eventDetailsRepository.findById(e.getId())
                    .orElseThrow(EventDetailsNotFoundException::new);
            EventDTO eventDTO = new EventDTO();
            modelMapper.map(eventDetails, eventDTO);

            //attendance ids
            Set<User> users = e.getUsers();
            if (!users.isEmpty()) {
                for (User user : users) {
                    eventDTO.getAttendanceIds().add(user.getId());
                }
            }

            //feedback list
            Set<Feedback> feedbacks = feedbackRepository.findByEvent(e.getId());
            if (!feedbacks.isEmpty()) {
                for (Feedback f : feedbacks) {
                    FeedbackDTO dto = new FeedbackDTO();
                    modelMapper.map(f, dto);
                    dto.setUsersId(f.getUser().getId());
                    eventDTO.getFeedback().add(dto);
                }
            }

            UserDTO userDTO = new UserDTO();
            modelMapper.map(e.getUser(), userDTO);
            eventDTO.setId(e.getId());
            eventDTO.setUsers(userDTO);
            eventDTO.setUsersId(e.getUser().getId());

            //for timestamp
            LocalDateTime localDateTime = LocalDateTime
                    .of(eventDetails.getDate().toLocalDate(), eventDetails.getTime().toLocalTime());
            ZoneId zoneId = ZoneId.systemDefault();
            long epoch = localDateTime.atZone(zoneId).toEpochSecond();
            eventDTO.setTimestamp(epoch);

            //explicitly set the next 2 because they have different names(in DTO & entity) and are not mapped automatically
            eventDTO.setMaxPeople(eventDetails.getMaxPersons());
            eventDTO.setLang(eventDetails.getLanguage());

            eventDTO.setName(eventDetails.getName());
            eventDTOS.add(eventDTO);
        }
        return eventDTOS;
    }

    @Transactional
    public List<EventDTO> viewFutureOrPastEvents(Boolean future) {
        List<EventDTO> eventDTOS = viewAll();
        List<EventDTO> pastEvents = new ArrayList<>();
        List<EventDTO> futureEvents = new ArrayList<>();
        for (EventDTO eventDTO : eventDTOS) {
            if (eventDTO.getDate().before(Date.valueOf(LocalDateTime.now().toLocalDate()))
                    && eventDTO.getTime().before(Date.valueOf(LocalDateTime.now().toLocalDate()))) {
                pastEvents.add(eventDTO);
            } else {
                futureEvents.add(eventDTO);
            }
        }

        if (future) {
            return futureEvents;
        } else {
            return pastEvents;
        }

    }

    @Transactional
    public EventDTO addAtendees(Integer eventId, Object o) {
        LinkedHashMap<String, List<Integer>> attendList;
        attendList = (LinkedHashMap<String, List<Integer>>) o;
        List<Integer> attendees = attendList.get("attendanceIds");
        EventDTO eventDTO = getByIdDTO(eventId);
        Event event = getById(eventId);

        for (Integer idUser : attendees) {
            User user = userRepository.findById(idUser).orElseThrow(UserNotFoundException::new);
            if (!event.getUsers().contains(user)) {
                //for subscribe
                event.getUsers().add(user);
            }
        }
        for (User user : event.getUsers()) {
            if (!attendees.contains(user.getId())) {
                //for unsubscribe
                event.getUsers().remove(user);
            }
        }
        eventDTO.setAttendanceIds(attendees);

        return eventDTO;
    }

    @Transactional
    public EventDTO addFeedback(Integer eventId, Object o) {
        EventDTO eventDTO = getByIdDTO(eventId);
        //Event event = getById(eventId);
        FeedbackDTO feedbackDTO = new FeedbackDTO();

        LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>> feedBacks =
                (LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>>) o;
        ArrayList<LinkedHashMap<String, Object>> list = feedBacks.get("feedback");
        LinkedHashMap<String, Object> newFeedback = list.get(list.size() - 1);

        feedbackDTO.setClarity(newFeedback.get("clarity").toString());
        feedbackDTO.setOriginality(newFeedback.get("originality").toString());
        feedbackDTO.setComplexity(newFeedback.get("complexity").toString());
        feedbackDTO.setCursive(newFeedback.get("cursive").toString());
        feedbackDTO.setEngagement(newFeedback.get("engagement").toString());
        feedbackDTO.setUsersId(Integer.valueOf(newFeedback.get("usersId").toString()));

        feedbackService.create(feedbackDTO, eventId);

        eventDTO.getFeedback().add(feedbackDTO);

        return eventDTO;
    }

}
