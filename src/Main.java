import Controller.Controller;
import Controller.RelationshipController;
import Controller.UserController;
import Repository.*;
import Ui.MainMenu;

public class Main {

    public static void main(String[] args) {
	// write your code here
        MemoryRepo repoU= new UserFileRepo();
        MemoryRepo repoR= new RelationshipFileRepo();
        Controller contU=new UserController((UserFileRepo) repoU);
        Controller contR= new RelationshipController((RelationshipFileRepo)repoR);
        //System.out.println(contU.getClass());
        MainMenu ui= new MainMenu((UserController) contU,(RelationshipController) contR);
        ui.mainMenu();

    }
}
