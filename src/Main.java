import Controller.NewController.*;
import Repository.Db.*;
import Repository.RelationshipRepo;
import Repository.UserRepo;
import Ui.NewUi.Login;
import tests.Connections;

public class Main {

    public static void main(String[] args) {
	// write your code here


        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RequestsDbRepo repoRQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        MessageDbRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);


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
