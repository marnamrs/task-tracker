package todo.project.todotracker.utils;

public enum RoleType {
    /**
     * Types of user Roles:
     *
     * Admin: full permissions and access
     *  * View: Everything
     *  * Create: Everything
     *  * Edit: Everything
     *
     * Basic: limited permissions
     *  * View: Everything
     *  * Create: Can only assign tasks to themselves
     *  * Edit: Can only edit tasks assigned to themselves
     * * */
    ADMIN, BASIC
}
