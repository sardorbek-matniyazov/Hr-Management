package com.example.hrmanagement.payload;

public record Status(String message, boolean success, Object any) {



    public Status(String message, boolean success) {
        this(message, success, null);
    }

    public static final Status ALREADY_FINISHED = new Status("The work has already finished", false);
    public static final Status COMPANY_EXIST = new Status("Company already created", false);
    public static final Status COMPANY_NOT_FOUND = new Status("Company not found", false);
    public static final Status NON_USED_USER = new Status("this isn't your own", false);
    public static final Status WORK_NOT_FOUND = new Status("Work not found", false);
    public static final Status EMAIL_ALREADY_VERIFIED = new Status("The email already verified, the link is broken", false);
    public static final Status PASSWORD_ERROR = new Status("Password doesn't match", false, null);
    public static final Status INVALID_ROLE = new Status("Invalid Role", false, null);
    public static final Status USERNAME_ALREADY_EXISTS = new Status("Username already exists", false);
    public static final Status EMAIL_ALREADY_EXISTS = new Status("Email already exists", false);
    public static final Status SUCCESS = new Status("Success", true);
    public static final Status SUCCESS_REGISTER = new Status("Successfully registered, verify your email", true);
    public static final Status USER_NOT_FOUND = new Status("User not found", false);
    public static final Status FAILED_VERIFICATION = new Status("Failed to verify", false);
}
