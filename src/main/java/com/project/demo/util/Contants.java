package com.project.demo.util;

import com.project.demo.model.Role;

public class Contants {

    public static class Page {
        public final static String URI_PAGE_PROJECT = "/projects/page?numberPage=";
        public final static String URI_PAGE_TASK_1 = "/tasks/page?numberPage=";
        public final static String URI_PAGE_TASK_3 = "/tasks/page/condition?numberPage=";
        public final static String URI_PAGE_TASK_2 = "&id=";
        public final static String URI_PAGE_SUBTASK = "/subtasks/page?numberPage=";
    }

    public static class Roles {
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
    }
}
