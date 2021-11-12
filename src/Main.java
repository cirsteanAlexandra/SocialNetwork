import Controller.NewController.MainController;
import Controller.NewController.PersoneController;
import Controller.NewController.RelationshipController;
import Controller.NewController.UserController;
import Repository.Db.PersoneDbRepo;
import Repository.Db.RelationshipDbRepo;
import Repository.Db.UserDbRepo;
import Repository.RelationshipRepo;
import Repository.UserRepo;
import Ui.NewUi.MainMenu;

public class Main {

    public static void main(String[] args) {
	// write your code here

        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/ReteaDeSocializare","postgres","852456");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/ReteaDeSocializare","postgres","852456");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/ReteaDeSocializare","postgres","852456");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);
        MainMenu ui= new MainMenu(cont);
        ui.mainMenu();
    }
}
