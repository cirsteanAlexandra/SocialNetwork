import Controller.NewController.*;

import Repository.Db.*;

import Repository.Db.MessageDbRepo;
import Repository.Db.PersoneDbRepo;
import Repository.Db.RelationshipDbRepo;
import Repository.Db.UserDbRepo;
import Repository.MessageRepo;
import Repository.RelationshipRepo;
import Repository.UserRepo;
import Ui.NewUi.Login;

public class Main {

    public static void main(String[] args) {
	// write your code here


        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/ReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/ReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/ReteaDeSocializare","postgres","hexagon");
        RequestsDbRepo repoRQ=new RequestsDbRepo("jdbc:postgresql://localhost:5432/ReteaDeSocializare","postgres","hexagon");
        MessageDbRepo repoM=new MessageDbRepo("jdbc:postgresql://localhost:5432/ReteaDeSocializare","postgres","hexagon");


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        RequestsController contRQ=new RequestsController(repoRQ);
        MessageController contM=new MessageController(repoM);

        MainController cont= new MainController(contU,contR,contP,contM,contRQ);

        Login ui= new Login(cont);
        ui.mainMenu();
    }
}
