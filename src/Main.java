import Controller.OldController.Controller;
import Controller.OldController.RelationshipController;
import Controller.OldController.UserController;
import Repository.File.RelationshipFileRepo;
import Repository.File.UserFileRepo;
import Repository.Memory.MemoryRepo;
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
