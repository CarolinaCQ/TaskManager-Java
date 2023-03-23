package com.project.demo.util;

import com.project.demo.model.Role;

public class Contants {

    public static abstract class Page {
        public final static String URI_PAGE_PROJECT = "/projects/page?numberPage=";
        public final static String URI_PAGE_TASK_1 = "/tasks/page?numberPage=";
        public final static String URI_PAGE_TASK_3 = "/tasks/page/condition?numberPage=";
        public final static String URI_PAGE_TASK_2 = "&id=";
        public final static String URI_PAGE_SUBTASK = "/subtasks/page?numberPage=";
    }

    public static abstract class Roles {
        public final static String ROLE_ADMIN = "ROLE_ADMIN";
        public final static String ROLE_USER = "ROLE_USER";
        public final static String[] ALL_ROLES = {ROLE_ADMIN, ROLE_USER};

    }

    public static class Request {
        public static final String AUTH = "/auth/**";
        public final static String PROJECTS = "/projects";
        public final static String PAGE_PROJECTS = "/projects/page";
        public final static String PROJECTS_ID = "/projects/{id}";
        public final static String TASKS = "/tasks";
        public final static String PAGE_TASKS = "/tasks/page";
        public final static String TASKS_ID = "/subtasks/{id}";
        public final static String SUBTASKS = "/subtasks";
        public final static String PAGE_SUBTASKS = "/subtasks/page";
        public final static String SUBTASKS_ID = "/subtasks/{id}";
        public final static String USERS = "/users";
        public final static String USERS_ID ="/users/{id}";
        public final static String[] API_DOCUMENTATION = {"/swagger-ui/**", "/v3/**", "/swagger-ui.html"};
    }

    public static abstract class HttpCodes{
        public static final String STATUS_OK = "200";
        public static final String STATUS_CREATED = "201";
        public static final String STATUS_BAD_REQUEST = "400";
        public static final String STATUS_FORBIDDEN = "403";
        public static final String STATUS_NOT_FOUND = "404";
        public static final String STATUS_INTERNAL_SERVER_ERROR = "500";
    }

    public static abstract class ProjectApi{

        public static final String TAG_NAME = "Project";
        public static final String TAG_DESCRIPTION = "The project API";

        public static final String SUCCESS = "This operation was succesful";
        public static final String INVALID_DATA = "The data entered is invalid";
        public static final String NOT_FOUND_PROJECT = "The project was not found with the indicated ID";
        public static final String NO_AUTHORIZATION = "Access denied, needed authorization to access";
        public static final String ERROR_SERVER = "Internal Server Eror";

        public static final String SUMARY_ADD = "Add a project";
        public static final String SUMARY_ADD_COLLABORATOR = "Add collaborator to project";
        public static final String SUMARY_UPDATE = "Update a project";
        public static final String SUMARY_DELETE = "Delete a project";
        public static final String SUMARY_PAGINATION = "Get project page";
        public static final String SUMARY_GET_ID = "Get project by Id";

        public static final String DESCRIPTION_ADD = "This endpoint is used to add a project";
        public static final String DESCRIPTION_ADD_COLLABORATOR = "This endpoint is used to add collaborator to project";
        public static final String DESCRIPTION_UPDATE = "This endpoint is used to update a project";
        public static final String DESCRIPTION_DELETE = "This endpoint is used to delete a project by ID";
        public static final String DESCRIPTION_PAGINATION = "This endpoint is used to get project page";
        public static final String DESCRIPTION_GET_ID = "This endpoint is used to get a project by Id";

        public static final String PARAMETER_ID = "Id of the project";
        public static final String PARAMETER_PROJECT_ADD = "Project to add";
        public static final String PARAMETER_PROJECT_UPDATE = "Project to update";
        public static final String PARAMETER_PROJECT_PAGE = "Size project page";
        public static final String PARAMETER_USERNAME = "Username user";

    }

    public static abstract class TaskApi{

        public static final String TAG_NAME = "Task";
        public static final String TAG_DESCRIPTION = "The task API";

        public static final String SUCCESS = "This operation was succesful";
        public static final String INVALID_DATA = "The data entered is invalid";
        public static final String NOT_FOUND_TASK = "The task was not found with the indicated ID";
        public static final String NO_AUTHORIZATION = "Access denied, needed authorization to access";
        public static final String ERROR_SERVER = "Internal Server Eror";

        public static final String SUMARY_ADD = "Add a task";
        public static final String SUMARY_UPDATE = "Update a task";
        public static final String SUMARY_DELETE = "Delete a task";
        public static final String SUMARY_PAGINATION = "Get task page";
        public static final String SUMARY_GET_ID = "Get task by Id";

        public static final String DESCRIPTION_ADD = "This endpoint is used to add a task";
        public static final String DESCRIPTION_UPDATE = "This endpoint is used to update a task";
        public static final String DESCRIPTION_DELETE = "This endpoint is used to delete a task by ID";
        public static final String DESCRIPTION_PAGINATION = "This endpoint is used to get task page";
        public static final String DESCRIPTION_GET_ID = "This endpoint is used to get a task by Id";

        public static final String PARAMETER_ID = "Id of the task";
        public static final String PARAMETER_TASK_ADD = "Task to add";
        public static final String PARAMETER_TASK_UPDATE = "Task to update";
        public static final String PARAMETER_TASK_PAGE = "Size project page";

    }

    public static abstract class SubtaskApi{

        public static final String TAG_NAME = "Subtask";
        public static final String TAG_DESCRIPTION = "The subtask API";

        public static final String SUCCESS = "This operation was succesful";
        public static final String INVALID_DATA = "The data entered is invalid";
        public static final String NOT_FOUND_SUBTASK = "The subtask was not found with the indicated ID";
        public static final String NO_AUTHORIZATION = "Access denied, needed authorization to access";
        public static final String ERROR_SERVER = "Internal Server Eror";

        public static final String SUMARY_ADD = "Add a subtask";
        public static final String SUMARY_UPDATE = "Update a subtask";
        public static final String SUMARY_DELETE = "Delete a subtask";
        public static final String SUMARY_PAGINATION = "Get subtask page";
        public static final String SUMARY_GET_ID = "Get subtask by Id";

        public static final String DESCRIPTION_ADD = "This endpoint is used to add a subtask";
        public static final String DESCRIPTION_UPDATE = "This endpoint is used to update a subtask";
        public static final String DESCRIPTION_DELETE = "This endpoint is used to delete a subtask by ID";
        public static final String DESCRIPTION_PAGINATION = "This endpoint is used to get subtask page";
        public static final String DESCRIPTION_GET_ID = "This endpoint is used to get a subtask by Id";

        public static final String PARAMETER_ID = "Id of the subtask";
        public static final String PARAMETER_SUBTASK_ADD = "Subtask to add";
        public static final String PARAMETER_SUBTASK_UPDATE = "Subtask to update";
        public static final String PARAMETER_SUBTASK_PAGE = "Size project page";

    }

    public static abstract class ConditionApi{

        public static final String TAG_NAME = "Condition";
        public static final String TAG_DESCRIPTION = "The condition API";

        public static final String SUCCESS = "This operation was succesful";
        public static final String INVALID_DATA = "The data entered is invalid";
        public static final String NOT_FOUND_CONDITION = "The condition was not found with the indicated ID";
        public static final String NO_AUTHORIZATION = "Access denied, needed authorization to access";
        public static final String ERROR_SERVER = "Internal Server Eror";

        public static final String SUMARY_ADD = "Add a condition";
        public static final String SUMARY_UPDATE = "Update a condition";
        public static final String SUMARY_DELETE = "Delete a condition";
        public static final String SUMARY_GET_ALL = "Get all conditions";
        public static final String SUMARY_GET_ID = "Get condition by Id";

        public static final String DESCRIPTION_ADD = "This endpoint is used to add a condition";
        public static final String DESCRIPTION_UPDATE = "This endpoint is used to update a condition";
        public static final String DESCRIPTION_DELETE = "This endpoint is used to delete a condition by ID";
        public static final String DESCRIPTION_GET_ALL = "This endpoint is used to get all conditions";
        public static final String DESCRIPTION_GET_ID = "This endpoint is used to get a condition by Id";

        public static final String PARAMETER_ID = "Id of the condition";
        public static final String PARAMETER_CONDITION_ADD = "Condition to add";
        public static final String PARAMETER_CONDITION_UPDATE = "Condition to update";

    }

    public static abstract class UserApi{

        public static final String TAG_NAME = "User";
        public static final String TAG_DESCRIPTION = "The user API";

        public static final String SUCCESS = "This operation was succesful";
        public static final String INVALID_DATA = "The data entered is invalid";
        public static final String NOT_FOUND_USER = "The user was not found with the indicated ID";
        public static final String NO_AUTHORIZATION = "Access denied, needed authorization to access";
        public static final String ERROR_SERVER = "Internal Server Eror";

        public static final String SUMARY_ADD = "Register a user";
        public static final String SUMARY_LOGIN = "User login";
        public static final String SUMARY_UPDATE = "Update a user";
        public static final String SUMARY_DELETE = "Delete a user";
        public static final String SUMARY_GET_ALL = "Get all users";
        public static final String SUMARY_GET_ID = "Get user by Id";

        public static final String DESCRIPTION_ADD = "This endpoint is used to register a user";
        public static final String DESCRIPTION_LOGIN = "This endpoint is used to login a user";
        public static final String DESCRIPTION_UPDATE = "This endpoint is used to update a user";
        public static final String DESCRIPTION_DELETE = "This endpoint is used to delete a user by ID";
        public static final String DESCRIPTION_GET_ALL = "This endpoint is used to get all users";
        public static final String DESCRIPTION_GET_ID = "This endpoint is used to get a user by Id";

        public static final String PARAMETER_ID = "Id of the user";
        public static final String PARAMETER_CONDITION_ADD = "User to add";
        public static final String PARAMETER_CONDITION_UPDATE = "User to update";

    }
}
