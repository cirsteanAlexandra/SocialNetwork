import Controller.*;
import Repository.MemoryRepo;
import Repository.RelationshipMemoryRepo;
import Repository.UserMemoryRepo;
import Ui.MainMenu;

public class Main {

    public static void main(String[] args) {
	// write your code here
        MemoryRepo repoU= new UserMemoryRepo();
        MemoryRepo repoR= new RelationshipMemoryRepo();
        Controller contU=new UserController((UserMemoryRepo) repoU);
        Controller contR= new RelationshipController((RelationshipMemoryRepo)repoR);
        MainMenu ui= new MainMenu((UserController) contU,(RelationshipController) contR);
        ui.mainMenu();

    }
}
