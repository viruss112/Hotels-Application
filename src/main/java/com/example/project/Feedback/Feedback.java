package com.example.project.Feedback;

import com.example.project.User.User;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "feedback")
@Data
public class Feedback {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "feedback_id")
    private Integer feedbackId;

    @Column(name = "description")
    private String description;

    @Column(name = "rate")
    private Integer rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(feedbackId, feedback.feedbackId) &&
                Objects.equals(description, feedback.description) &&
                Objects.equals(rate, feedback.rate) &&
                Objects.equals(user, feedback.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackId, description, rate, user);
    }
}
