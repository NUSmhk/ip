package duke;

import java.util.List;

/**
 * This represents a list of tasks.
 */
public class TaskList {

    private List<Task> tasks;
    private Storage storage;

    /**
     * This constructor creates the initial list of tasks for Duke bot by
     * firstly loading all the tasks stored in the given Storage.
     * @param storage where all the tasks are stored on initial load.
     */
    public TaskList(Storage storage) {
        this.storage = storage;
        tasks = storage.readFromStorage();
    }

    /**
     * This method adds the given task into the list of tasks and
     * returns a String which is provided to the user interface to inform
     * user that the task has been successfully added.
     * @param newTask Task object provided to be put into list.
     * @return String to inform user the command is successful.
     */
    public String addToList(Task newTask) {
        tasks.add(newTask);
        storage.writeToStorage(this.getList());

        return newTask.toString();
    }

    /**
     * This method deletes the task from the list of tasks at the given
     * index and returns a String which is provided to the user interface to
     * inform user that the task has been successfully deleted.
     * @param index the int that tells which task on the list to delete
     * @return String to inform user the command is successful.
     */
    public String deleteFromList(int index) {
        Task curr = tasks.get(index - 1);
        tasks.remove(index - 1);
        storage.writeToStorage(this.getList());

        return curr.toString();
    }

    /**
     * This method marks the task on the list of tasks as done at the
     * given index and returns a String which is provided to the user interface
     * to inform user that the task has been successfully marked as done.
     * @param index the int that tells which task on the list to mark as done.
     * @return String to inform user the command is successful.
     */
    public String taskDone(int index) {
        Task curr = tasks.get(index - 1);
        curr.markAsDone();
        storage.writeToStorage(this.getList());

        return curr.toString();
    }

    /**
     * This method tells how many tasks are there in the task list.
     * @return number of task remaining.
     */
    public int taskCount() {
        return tasks.size();
    }

    /**
     * This method transforms the list of task into a String that lists out all
     * the tasks neatly in a numbered order in a user-friendly way.
     * @return String that lists out the tasks.
     */
    public String getList() {
        int counter = 1;
        String result = "";

        for (int i = 0; i < tasks.size(); i++) {
            if (i < tasks.size() - 1) {
                result = result + counter + "." + tasks.get(i) + "\n";
            } else {
                result = result + counter + "." + tasks.get(i);
            }
            counter++;
        }

        return result;
    }
}
