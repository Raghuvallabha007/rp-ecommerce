package com.ecommerce.sb_ecommerce.exception;

public class ResourceNotFoundException extends RuntimeException {
    String fieldName;
    String resourceName;
    String field;
    Long fieldId;

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String fieldName, String resourceName, String field) {
        super(String.format("%s not found with %s: %s", resourceName, field, fieldName));
        this.fieldName = fieldName;
        this.resourceName = resourceName;
        this.field = field;
    }

    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("%s not found with %s: %s", resourceName, field, fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }
}
