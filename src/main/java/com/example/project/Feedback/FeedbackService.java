package com.example.project.Feedback;

import com.example.project.User.User;
import com.example.project.User.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    public FeedbackDTO saveFeedback(FeedbackDTO feedbackDTO, Integer userId) {

        Feedback feedback = new Feedback();
        Optional<User> user = userRepository.findById(userId);
        modelMapper.map(feedbackDTO, feedback);
        feedback.setUser(user.get());
        Feedback savedFeedback = feedbackRepository.save(feedback);
        FeedbackDTO feedbackDTO1 = new FeedbackDTO();
        modelMapper.map(savedFeedback, feedbackDTO1);
        return feedbackDTO1;

    }

    @Transactional(rollbackOn = Exception.class)
    public FeedbackDTO deleteFeedback(Integer feedbackId) {

        Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        modelMapper.map(feedback.get(), feedbackDTO);
        feedbackRepository.delete(feedback.get());
        return feedbackDTO;

    }
}
