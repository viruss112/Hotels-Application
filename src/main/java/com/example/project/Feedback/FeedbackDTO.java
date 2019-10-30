package com.example.project.Feedback;

import lombok.Data;

import java.util.Objects;

@Data
public class FeedbackDTO {

    private Integer feedbackId;
    private String description;
    private Integer rate;
    private Integer userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedbackDTO that = (FeedbackDTO) o;
        return Objects.equals(feedbackId, that.feedbackId) &&
                Objects.equals(description, that.description) &&
                Objects.equals(rate, that.rate) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackId, description, rate, userId);
    }
}
