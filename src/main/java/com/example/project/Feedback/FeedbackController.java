package com.example.project.Feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/create/{userId}")
    public FeedbackDTO saveFeedback(@RequestBody FeedbackDTO feedbackDTO, @PathVariable Integer userId) {
        return feedbackService.saveFeedback(feedbackDTO,userId);
    }

    @DeleteMapping("/delete/{feedbackId}")
    public FeedbackDTO deleteFeedback(@PathVariable Integer feedbackId) {
        return feedbackService.deleteFeedback(feedbackId);
    }
}
