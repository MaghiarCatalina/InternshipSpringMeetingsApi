package edu.arobs.meetingsapi.meeting;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MeetingService(MeetingRepository meetingRepository, ModelMapper modelMapper) {
        this.meetingRepository = meetingRepository;
        this.modelMapper = modelMapper;
    }

    public MeetingDTO create(MeetingDTO meetingDTO) {
        Meeting newMeeting = new Meeting();
        newMeeting.setId(null);
        modelMapper.map(meetingDTO, newMeeting);
        meetingRepository.save(newMeeting);
        MeetingDTO savedMeetingDTO = new MeetingDTO();
        modelMapper.map(newMeeting, savedMeetingDTO);
        return savedMeetingDTO;
    }

    public MeetingDTO update(Integer id, MeetingDTO meeting) {
        MeetingDTO foundMeetingDTO = getById(id);
        Meeting updateMeeting = new Meeting();
        modelMapper.map(foundMeetingDTO,updateMeeting);
        updateMeeting.setId(id);
        updateMeeting.setTitle(meeting.getTitle());
        updateMeeting.setLocation(meeting.getLocation());
        updateMeeting.setTime(meeting.getTime());
        meetingRepository.save(updateMeeting);
        MeetingDTO updatedMeetingDTO = new MeetingDTO();
        modelMapper.map(updateMeeting,updatedMeetingDTO);
        return updatedMeetingDTO;
//        Meeting existingMeeting = getById(id);
//        existingMeeting.setTitle(meeting.getTitle());
//        existingMeeting.setLocation(meeting.getLocation());
//        existingMeeting.setTime(meeting.getTime());
//        return existingMeeting;
    }

    public MeetingDTO getById(Integer id) {
        Meeting foundMeeting = meetingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Meeting id=%d does not exist", id)));
        MeetingDTO foundMeetingDTO = new MeetingDTO();
        modelMapper.map(foundMeeting, foundMeetingDTO);
        return foundMeetingDTO;
    }

    public MeetingDTO delete(Integer id) {
        MeetingDTO existingMeetingDTO = getById(id);
        meetingRepository.deleteById(id);
        return existingMeetingDTO;
    }
}
