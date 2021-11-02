package Ui;

import Controller.RelationshipController;
import Controller.UserController;
import Controller.Validator.AbstractValidator;
import Controller.Validator.ContextValidator;
import Controller.Validator.Strategy;
import Controller.Validator.Validator;
import Domain.Persone;
import Domain.Relationship;
import Domain.User;
import Utils.Exceptions.*;

import java.util.*;

public class MainMenu {
    String currentMode=new String();
    UserController contUser;
    RelationshipController contRel;

    public MainMenu(UserController contUser, RelationshipController contRel) {
        this.contUser = contUser;
        this.contRel = contRel;
    }

    private Map<Integer,String> generateOptions(){
        Map<Integer,String> options= new HashMap();
        options.put(1,"- to add a user");
        options.put(2,"- to add a a relationship between two users");
        options.put(3,"- to remove a user");
        options.put(4,"- to remove a relationship");
        options.put(5,"- print all users");
        options.put(6,"- print all relationships");
        options.put(7,"- number of cummunities");
        options.put(8,"- the most sociable community");
        options.put(9,"- to exit");
        return options;
    }

    private void printMenu(){
        Map<Integer,String> options= generateOptions();
        System.out.println("Main Manu");
        for(int i=1; i<=options.size();i++)
            System.out.println(Integer.toString(i)+options.get(i));
        System.out.print("Option: ");
    }

    public void mainMenu(){
        boolean done=false;
        while(!done) {
            printMenu();
            Scanner scan = new Scanner(System.in);
            int option=scan.nextInt();
            switch(option){
                case 1:
                    addUser();
                    break;
                case 2:
                    addRelationship();
                    break;
                case 3:
                    removeUser();
                    break;
                case 4:
                    removeRelationship();
                    break;
                case 5:
                    printAllUsers();
                    break;
                case 6:
                    printAllRelationships();
                    break;
                case 9:
                   done=true;
                   break;
                case 404:
                    setCurrentMode();
                default:
                    System.out.println("Invalid Option");
            }
        }

    }

    private void addUser(){
        long id=0;
        String username=new String();
        String firstName=new String();
        String lastName=new String();
        Scanner scan = new Scanner(System.in);
        System.out.println("Provide all the necesary information: ");
        if(currentMode.equals("admin")){
            System.out.print("Id: ");
            id= scan.nextLong();
        }
        System.out.println("First Name :");
        firstName=scan.nextLine();
        System.out.println("Last Name :");
        lastName=scan.nextLine();
        System.out.print("Username: ");
        username=scan.nextLine();

        try{
            User user;
            if(id!=0)user=new User(id,username,new Persone(firstName,lastName));
            else user=new User(username,new Persone(firstName,lastName));
            Validator vali= ContextValidator.createValidator(Strategy.USER);
            vali.validate(user);
            contUser.add(user);
            System.out.println("User created succesfully");
        }
        catch(UserException e){
            System.out.println(e.getDescription());
        }
        catch(UserRepoException e){
            System.out.println(e.getDescription());
        }
    }

    private void addRelationship(){
        long id=0;
        String username1=new String();
        String username2=new String();
        Scanner scan = new Scanner(System.in);
        System.out.println("Provide all the necesary information: ");
        if(currentMode.equals("admin")){
            System.out.print("Id: ");
            id= scan.nextLong();
        }
        System.out.println("First Username :");
        username1=scan.nextLine();
        System.out.println("Second Username :");
        username2=scan.nextLine();
        try{
            Relationship rel;
            if(id!=0)rel=new Relationship(id,username1,username2);
            else rel=new Relationship(username1,username2);
            Validator vali= ContextValidator.createValidator(Strategy.RELATIONSHIP);
            vali.validate(rel);
            if(contUser.getByOther(username1)==null || contUser.getByOther(username2)==null )
                throw new EntityRepoException("A relationship is only applied between tow existing users\n");
            contRel.add(rel);
            contUser.addFriend(contUser.getByOther(username1),username2);
            contUser.addFriend(contUser.getByOther(username2),username1);
            System.out.println("Relationship created succesfully");
        }
        catch(RelationshipException e){
            System.out.println(e.getDescription());
        }
        catch(RelationshipRepoException e){
            System.out.println(e.getDescription());
        }
        catch(EntityRepoException e){
            System.out.println(e.getDescription());
        }
    }

    private void removeUser(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Delete by : 1-Id or 2-Username\nYour option: ");
        int option=scan.nextInt();
        switch(option){
            case 1:
                removeUserById();
                break;
            case 2:
                removeUserByUsername();
                break;
            default:
                System.out.println("Invalid option!");
        }
    }

    private void removeRelationship(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Delete by : 1-Id or 2-By two usernames\nYour option: ");
        int option=scan.nextInt();
        switch(option){
            case 1:
                removeRelationshipById();
                break;
            case 2:
                removeRelationshipByUsernames();
                break;
            default:
                System.out.println("Invalid option!");
        }
    }

    private void removeRelationshipById(){
        Scanner scan=new Scanner(System.in);
        System.out.println("Id:");
        long id=scan.nextLong();
        try{
            AbstractValidator vali=ContextValidator.createValidator(Strategy.USER);
            vali.checkId(id);
            Relationship rel= contRel.getById(id);
            contRel.removeById(id);
            contUser.removeFriend(contUser.getByOther(rel.getFirstUserName()),rel.getSecondUserName());
            contUser.removeFriend(contUser.getByOther(rel.getSecondUserName()),rel.getFirstUserName());
            System.out.println("Relationship removed with succes");
        }
        catch (RelationshipRepoException e){
            System.out.println(e.getDescription());
        }
    }

    private void removeRelationshipByUsernames(){
        Scanner scan=new Scanner(System.in);
        System.out.println("First Username :");
        String username1=scan.nextLine();
        System.out.println("Second Username");
        String username2=scan.nextLine();
        try{
            AbstractValidator vali=ContextValidator.createValidator(Strategy.USER);
            vali.checkUserName(username1);
            vali.checkUserName(username2);
            Relationship rel= contRel.getByOther(username1,username2);
            contRel.removeByOthers(username1,username2);
            contUser.removeFriend(contUser.getByOther(rel.getFirstUserName()),rel.getSecondUserName());
            contUser.removeFriend(contUser.getByOther(rel.getSecondUserName()),rel.getFirstUserName());
            System.out.println("Relationship removed with succes");
        }
        catch (RelationshipRepoException e){
            System.out.println(e.getDescription());
        }
    }

    private void removeUserById(){
        Scanner scan=new Scanner(System.in);
        System.out.println("Id:");
        long id=scan.nextLong();
        try{
            AbstractValidator vali=ContextValidator.createValidator(Strategy.USER);
            vali.checkId(id);
            User user= contUser.getById(id);
            contUser.removeById(id);
            contUser.removeUserFromAllFriends(user.getUsername());
            System.out.println(Integer.toString(contRel.deleteAllRelationsByUsername(user.getUsername())) +" relations deleted");
            System.out.println("User removed with succes");
        }
        catch(UserRepoException e){
            System.out.println(e.getDescription());
        }
        catch (RelationshipRepoException e){
            System.out.println(e.getDescription());
        }
    }

    private void removeUserByUsername(){
        Scanner scan=new Scanner(System.in);
        System.out.println("Username:");
        String username=scan.nextLine();
        try{
            AbstractValidator vali=ContextValidator.createValidator(Strategy.USER);
            vali.checkUserName(username);
            User user= contUser.getByOther(username);
            contUser.removeByOthers(username);
            contUser.removeUserFromAllFriends(user.getUsername());
            System.out.println(Integer.toString(contRel.deleteAllRelationsByUsername(user.getUsername())) +" relations deleted");
            System.out.println("User removed with succes");
        }
        catch(UserRepoException e){
            System.out.println(e.getDescription());
        }
        catch (RelationshipRepoException e){
            System.out.println(e.getDescription());
        }
    }

    private void setCurrentMode(){
        Scanner scan=new Scanner(System.in);
        System.out.println("Set the current mode : ");
        currentMode= scan.nextLine();
        System.out.println("Entering to " + currentMode +" mode");
    }

    private void printAllUsers(){
        List<User> list=contUser.getAll();
        for (User el: list){
            System.out.println(el);
            System.out.println(el.getFriendsList());
        }
    }

    private void printAllRelationships(){
        List<Relationship> list=contRel.getAll();
        for (Relationship el: list){
            System.out.println(el);
        }
    }

}
