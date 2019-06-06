package edu.arobs.meetingsapi.feedback;

import edu.arobs.meetingsapi.event.EventRepository;
import edu.arobs.meetingsapi.exception.EventNotFoundException;
import edu.arobs.meetingsapi.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FeedbackService {

    private final ModelMapper modelMapper;
    private final FeedbackRepository feedbackRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public FeedbackService(ModelMapper modelMapper, FeedbackRepository feedbackRepository,
                           EventRepository eventRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.feedbackRepository = feedbackRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Feedback create(FeedbackDTO feedbackDTO, Integer eventId) {
        Feedback feedback = new Feedback();
        feedback.setId(null);
        modelMapper.map(feedbackDTO, feedback);
        feedback.setEvent(eventRepository.findById(eventId).orElseThrow(EventNotFoundException::new));
        feedback.setUser(userRepository.findById(feedbackDTO.getUsersId()).get());
        return feedbackRepository.save(feedback);

    }
}
