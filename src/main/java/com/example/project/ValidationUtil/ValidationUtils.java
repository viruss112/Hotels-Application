package com.example.project.ValidationUtil;

import javax.validation.ValidationException;
import java.util.Collection;

public class ValidationUtils {

    public static void validateRequiredObject(Object object, String fieldName) {
        validateRequiredObject(object, fieldName, -1);
    }

    public static void validateRequiredObject(Object object, String fieldName, int maxLength) {
        validateRequiredObject(object, fieldName, -1, maxLength);
    }

    public static void validateRequiredObject(Object object, String fieldName, int minLength, int maxLength) {
        if (!isObjectSet(object)) {
            throw new ValidationException(fieldName + " required");
        }
        if (object instanceof String) {
            validateMinLength((String) object, fieldName, minLength);
            validateMaxLength((String) object, fieldName, maxLength);
        }
    }

    private static boolean isObjectSet(Object object) {
        if (object == null) {
            return false;
        }

        if (object instanceof String) {
            String stringValue = (String) object;

            if (stringValue.isEmpty()) {
                return false;
            }
        }

        if (object instanceof Collection) {
            Collection collection = (Collection) object;

            if (collection.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public static void validateMinLength(String fieldValue, String fieldName, int minLength) {
        if (minLength > 0 && fieldValue.length() < minLength) {
            throw new ValidationException(fieldName + " min length is " + minLength);
        }
    }

    public static void validateMaxLength(String fieldValue, String fieldName, int maxLength) {
        if (fieldValue != null && maxLength > 0 && fieldValue.length() > maxLength) {
            throw new ValidationException(fieldName + " max length is " + maxLength);
        }

    }

    public static void validateRangeLength(String fieldValue, String fieldName, int minLength, int maxLength) {
        validateMinLength(fieldValue, fieldName, minLength);
        validateMaxLength(fieldValue, fieldName, maxLength);
    }
}
