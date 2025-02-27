package ru.sfedu.ictis.sports_sections.exception;

public interface ValidationConstants {

    String USERNAME_SIZE_NOT_VALID = "Username size should be between 3 and 25";

    String NEWS_DESCRIPTION_SIZE_NOT_VALID = "News description size should be between 3 and 130";

    String NEWS_DESCRIPTION_HAS_TO_BE_PRESENT = "News description mustn't be null";

    String ID_MUST_BE_POSITIVE = "ID must be grater than zero";

    String REQUIRED_INT_PARAM_PAGE_IS_NOT_PRESENT = "Parameter page mustn't be null";

    String REQUIRED_INT_PARAM_PER_PAGE_IS_NOT_PRESENT = "Parameter perPage mustn't be null";

    String USERNAME_HAS_TO_BE_PRESENT = "Username mustn't be null";

    String TAGS_NOT_VALID = "Tags invalid";

    String NEWS_IMAGE_HAS_TO_BE_PRESENT = "Image mustn't be null";

    String USER_WITH_THIS_EMAIL_ALREADY_EXIST = "User with this email already exists";

    String USER_PASSWORD_NOT_VALID = "user password should be more than 6 symbols";

    String USER_AVATAR_NOT_NULL = "user avatar mustn't be null";

    String USER_AVATAR_NOT_VALID = "user avatar should be between 3 and 130";

    String MAX_UPLOAD_SIZE_EXCEEDED = "Maximum upload size exceeded";

    String PER_PAGE_MAX_NOT_VALID = "perPage must be less or equal 100";

    String PER_PAGE_MIN_NOT_VALID = "perPage must be greater or equal 1";

    String PAGE_SIZE_NOT_VALID = "news page must be greater or equal 1";

    String USER_EMAIL_NOT_VALID = "user email must be a well-formed email address";

    String PARAM_PER_PAGE_NOT_NULL = "Required Integer parameter 'perPage' is not present";

    String PARAM_PAGE_NOT_NULL = "Required Integer parameter 'page' is not present";

    String NEWS_TITLE_NOT_NULL = "title has to be present";

    String NEWS_TITLE_SIZE = "news title size not valid";

    String USER_ROLE_NOT_VALID = "user role should be between 3 and 25";

    String USER_PASSWORD_NULL = "user password must be null";

    String USER_EMAIL_NOT_NULL = "user email mustn't be null";

    String ROLE_SIZE_NOT_VALID = "role size not valid";

    String EMAIL_SIZE_NOT_VALID = "email size not valid";

    String TOKEN_NOT_PROVIDED = "JWT token not provided";

    String USER_ID_NULL = "User id must be null";

    String USERNAME_NULL = "Username must be null";

    String USER_ROLE_NULL = "User role must be null";

    String USER_ROLE_NOT_NULL = "user role mustn't be null";

    String TOKEN_POSITION_MISMATCH = "Token must be in authorization header";

    String NEWS_IMAGE_LENGTH = "Image link length should be between 3 and 130";

    String NEWS_ID_NULL = "News id must be null";

    String PASSWORD_NOT_VALID = "password not valid";

    String USER_NAME_HAS_TO_BE_PRESENT = "User name has to be present";

    String TODO_TEXT_NOT_NULL = "todo text not null";

    String TODO_TEXT_SIZE_NOT_VALID = "size not valid";

    String TODO_STATUS_NOT_NULL = "todo status not null";

    String TASK_NOT_FOUND = "task not found";

    String TASK_PATCH_UPDATED_NOT_CORRECT_COUNT = "task patch updated not correct count";

    String TASKS_PAGE_GREATER_OR_EQUAL_1 = "task page greater or equal 1";

    String TASKS_PER_PAGE_GREATER_OR_EQUAL_1 = "tasks per page greater or equal 1";

    String TASKS_PER_PAGE_LESS_OR_EQUAL_100 = "tasks per page less or equal 100";

    String HTTP_MESSAGE_NOT_READABLE_EXCEPTION = "Http request not valid";

    String THEME_SIZE_NOT_VALID = "The length of the theme must be from 3 to 130";

    String THEME_SIZE_NOT_NULL = "The length of the theme must not null";

    String CATEGORY_SIZE_NOT_NULL = "The length of the category must not null";

    String CATEGORY_SIZE_NOT_VALID = "The length of the category must be from 3 to 100";

    String CATEGORY_ALREADY_EXISTS = "The category should not exist!";

    String CATEGORY_NOT_FOUND = "This category does not exist";

    String SECTION_NAME_NULL = "The section name must not be null or empty.";

    String SECTION_NAME_SIZE_NOT_VALID = "The section name must be between 1 and 100 characters.";

    String SECTION_DESCRIPTION_NULL = "The section description must not be null or empty.";

    String SECTION_DESCRIPTION_SIZE_NOT_VALID = "The section description must be between 1 and 100 characters.";

    String SECTION_LOCATION_NULL = "The section location must not be null or empty.";

    String SECTION_LOCATION_SIZE_NOT_VALID = "The section location must be between 1 and 100 characters.";

    String SECTION_TRAINER_ID_NULL = "The trainer ID must not be null.";

    String SECTION_TRAINER_ID_NEGATIVE = "The trainer ID must be a positive number.";

    String SECTION_CATEGORIES_EMPTY = "The section must have at least one category.";

    String SECTION_CATEGORIES_NULL = "The categories field must not be null.";

    String SECTION_ID_NULL = "Section ID is required";

    String NO_RIGHTS_FOR_CREATE = "Only a trainer can create!";

    String SECTION_NOT_FOUND = "This section does not exist";

    String STATUS_NULL = "Status cannot be null";

    String STATUS_SIZE_NOT_VALID = "The length of the status must be from 3 to 100";

    String ENROLLMENT_NOT_FOUND = "This enrollment does not exist";

    String ACHIEVEMENT_TITLE_NULL = "The achievement title must not be null or empty.";

    String ACHIEVEMENT_TITLE_SIZE_NOT_VALID = "The achievement title must be between 3 and 100 characters.";

    String ACHIEVEMENT_DESCRIPTION_NULL = "The achievement description must not be null or empty.";

    String ACHIEVEMENT_DESCRIPTION_SIZE_NOT_VALID = "The achievement description must be between 3 and 150 characters.";

    String ACHIEVEMENT_TEMPLATE_NOT_FOUND = "This achievement template does not exist";

    String ACHIEVEMENT_TEMPLATE_TITLE_EXIST = "This title already exists";

    String DATE_NULL = "Date cannot be NULL";

    String DATE_NOT_FUTURE = "The date can only be in the future";

    String SESSION_NOT_FOUND = "This session does not exist";

    String REVIEWS_RATING_NOT_VALID = "The rating in the review can be from 1 to 5";

    String REVIEWS_COMMENT_NOT_VALID = "The comment in the review can be from 1 to 255";

    String NO_RIGHT_FOR_CREATE_REVIEW = "To create a review," +
            " you must be registered for the section and have attended a lesson.";

    String REVIEW_NOT_FOUND = "This review does not exist";

    String REVIEW_EXISTS = "This is review exist";

    private void doNothingMethod() {

    }
}
