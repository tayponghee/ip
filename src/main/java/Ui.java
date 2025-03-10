import java.util.List;
import java.util.Scanner;

/**
 * The Ui class handles user interactions and provides methods for printing messages,
 * displaying task lists, and processing user commands.
 */
public class Ui {
    private static final String LINE_SEPARATOR = "____________________________________________________________";
    public static final int SPLIT_INTO_TWO_PARTS = 2;
    public static final String TODO = "todo";
    public static final String DEADLINE = "deadline";
    public static final String EVENT = "event";
    public static final String BYE = "bye";
    public static final String DELETE = "delete";
    public static final String GOODBYE = "Goodbye... It may be a mere few seconds for you but an eternity for me.";
    public static final String SUP = "Sup! I'm DavinciBot! I was the smartest man alive," +
            " but now I am just a list maker.";
    public static final String ENTER_COMMANDS = "Enter commands, and I will echo them back to you, " +
            "as well as add them to your list.";
    public static final String ITEMS_IN_PREVIOUS_LIST = "If there were things in your list that you previously had, " +
            "I will show what you have told me previously.";
    public static final String TYPE_BYE_TO_END_THE_CONVERSATION = "Type 'bye' to end the conversation.";
    public static final String TYPE_LIST_TO_SEE_YOUR_TO_DO_LIST = "Type 'list' to see your to-do list.";
    public static final String TYPE_MARK_TO_MARK_A_TASK_AS_DONE = "Type 'mark' to mark a task as done.";
    public static final String TYPE_UNMARK_TO_MARK_A_TASK_AS_NOT_DONE = "Type 'unmark' to mark a task as not done.";
    public static final String TYPE_TODO_WORK_TO_ADD_A_TASK_TO_THE_LIST = "Type 'todo <work>' " +
            "to add a task to the list.";
    public static final String BYE_BYE = "See ya bucko!";
    public static final String TYPE_FIND_KEYWORD_TO_LIST_ALL_THE_TASKS_THAT_HAVE_THOSE_KEYWORDS =
            "Type 'find <keyword>' to list all the tasks that have those keywords.";
    public static final String TYPE_DELETE_INDEX_TO_DELETE_A_TASK_FROM_YOUR_LIST = "Type 'delete <index>' to" +
            " delete a task from your list.";
    public static final String TYPE_EVENT_DESCRIPTION_FROM_START_TO_END_TO_ADD_AN_EVENT_TO_THE_LIST =
            "Type 'event <description> /from <start> /to <end>' to add an event to the list.";
    public static final String TYPE_DEADLINE_DESCRIPTION_BY_DEADLINE_TO_ADD_A_TASK_WITH_A_DEADLINE_TO_THE_LIST =
            "Type 'deadline <description> /by <deadline>' to add a task with a deadline to the list.";
    public static final String ERROR = "Error: ";
    public static final String ERROR_INVALID_TASK_INDEX_FORMAT = "Error: Invalid task index format.";
    public static final String WHAT_DO_YOU_WANT_ME_TO_DO = "What do you want me to do? ";
    public static final String NO_TASKS_ENTERED_YET = "No tasks entered yet.";
    public static final String HERE_ARE_THE_TASKS_IN_YOUR_LIST = "Here are the tasks in your list:";
    public static final String THESE_ARE_THE_TASKS_THAT_MATCH_YOUR_SEARCH = "These are the tasks that match your search: ";
    public static final String D = "[D]";
    public static final String T = "[T]";
    public static final String E = "[E]";
    public static final String GOT_IT_I_VE_ADDED_THIS_TASK = "Got it. I've added this task:";
    public static final String NOW_YOU_HAVE = "Now you have ";
    public static final String TASKS_IN_THE_LIST = " tasks in the list.";

    /**
     * Prints the starting message and instructions for using DavinciBot.
     */
    public static void printStartingMessage() {
        System.out.println(LINE_SEPARATOR);
        System.out.println(SUP);
        System.out.println(ENTER_COMMANDS);
        System.out.println(ITEMS_IN_PREVIOUS_LIST);
        System.out.println(TYPE_BYE_TO_END_THE_CONVERSATION);
        System.out.println(TYPE_LIST_TO_SEE_YOUR_TO_DO_LIST);
        System.out.println(TYPE_MARK_TO_MARK_A_TASK_AS_DONE);
        System.out.println(TYPE_UNMARK_TO_MARK_A_TASK_AS_NOT_DONE);
        System.out.println(TYPE_TODO_WORK_TO_ADD_A_TASK_TO_THE_LIST);
        System.out.println(TYPE_DEADLINE_DESCRIPTION_BY_DEADLINE_TO_ADD_A_TASK_WITH_A_DEADLINE_TO_THE_LIST);
        System.out.println(TYPE_EVENT_DESCRIPTION_FROM_START_TO_END_TO_ADD_AN_EVENT_TO_THE_LIST);
        System.out.println(TYPE_DELETE_INDEX_TO_DELETE_A_TASK_FROM_YOUR_LIST);
        System.out.println(TYPE_FIND_KEYWORD_TO_LIST_ALL_THE_TASKS_THAT_HAVE_THOSE_KEYWORDS);
        System.out.println(BYE_BYE);
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Handles user commands and performs corresponding actions based on the input.
     */
    public static void userCommand() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String userInput = Ui.getUserInput();
            if (userInput.equalsIgnoreCase(BYE)) {
                Ui.printMessage(GOODBYE);
                break;
            }
            if (userInput.toLowerCase().startsWith(TODO) ||
                    userInput.toLowerCase().startsWith(DEADLINE) ||
                    userInput.toLowerCase().startsWith(EVENT)) {
                TaskList.handleUserInput(userInput);
            } else if (userInput.toLowerCase().startsWith(DELETE)) {
                try {
                    String[] parts = userInput.split(" ", SPLIT_INTO_TWO_PARTS);
                    TaskList.ableToDelete(parts);
                } catch (DavinciException e) {
                    Ui.printMessage(ERROR + e.getMessage());
                } catch (NumberFormatException e) {
                    Ui.printMessage(ERROR_INVALID_TASK_INDEX_FORMAT);
                }
            } else {
                Parser.parseUserInput(userInput, TaskList.getTaskList());
            }
        }
        scanner.close();
    }

    /**
     * Gets user input from the console.
     *
     * @return User input as a trimmed string.
     */
    public static String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(WHAT_DO_YOU_WANT_ME_TO_DO);
        return scanner.nextLine().trim();
    }

    /**
     * Displays the current task list.
     *
     * @param taskList The list of tasks to be displayed.
     */
    public static void displayTaskList(List<Task> taskList) {
        System.out.println(LINE_SEPARATOR);
        if (taskList.isEmpty()) {
            System.out.println(NO_TASKS_ENTERED_YET);
        } else {
            System.out.println(HERE_ARE_THE_TASKS_IN_YOUR_LIST);
            checkForTask(taskList);
        }
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Displays the tasks that match the user's search keyword.
     *
     * @param taskList The list of tasks to be displayed.
     */
    public static void displayFindTask(List<Task> taskList) {
        System.out.println(LINE_SEPARATOR);
        System.out.println(THESE_ARE_THE_TASKS_THAT_MATCH_YOUR_SEARCH);
        checkForTask(taskList);
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Displays a single task with the correct task icon.
     *
     * @param task The task to be displayed.
     */
    public static void displaySingleTask(Task task) {
        String taskType;
        if (task instanceof Deadline) {
            taskType = D;
        } else if (task instanceof Todo) {
            taskType = T;
        } else {
            taskType = E;
        }
        System.out.println(taskType +
                " [" + task.getStatusIcon() + "] " +
                task.getDescription());
    }

    /**
     * Displays tasks in the given task list with their corresponding task types and status icons.
     *
     * @param taskList The list of tasks to be displayed.
     */
    private static void checkForTask(List<Task> taskList) {
        for (int i = 0; i < taskList.size(); i++) {
            String taskType;
            Task task = taskList.get(i);
            if (task instanceof Deadline) {
                taskType = D;
            } else if (task instanceof Todo) {
                taskType = T;
            } else {
                taskType = E;
            }
            System.out.println((i + 1) + ". " + taskType +
                    " [" + task.getStatusIcon() + "] " +
                    task.getDescription());
        }
    }

    public static void printMessage(String message) {
        System.out.println(LINE_SEPARATOR);
        System.out.println(message);
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Echoes the last added task.
     *
     * @param taskList The list of tasks to get the last added task.
     */
    public static void echoTask(List<Task> taskList) {
        System.out.println(LINE_SEPARATOR);
        Task addedTask = taskList.get(taskList.size() - 1);
        System.out.println(GOT_IT_I_VE_ADDED_THIS_TASK);
        System.out.println(addedTask.toString());
        System.out.println(NOW_YOU_HAVE + taskList.size() + TASKS_IN_THE_LIST);
        System.out.println(LINE_SEPARATOR);
    }
}
