package com.example.project.Hotel;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Data
@Component
public class CoordinatesDTO {

    private double latitude;
    private double longitude;
    private double radius;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordinatesDTO that = (CoordinatesDTO) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                Double.compare(that.radius, radius) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, radius);
    }
}
