package UI;

import Controller.Controller;
import Controller.Problem;
import Repository.Repository;

/**
 * @author joj on 4/17/2019
 **/
public class Console {
    public static void main(String[] args) {
        Repository repository = new Repository("data/credit_cards.in");
        Problem problem = new Problem("data/parameters.txt");

        Controller controller = new Controller(repository, problem);
        controller.run();
    }
}
