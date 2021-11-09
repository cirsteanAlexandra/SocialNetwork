package Ui.NewUi;

import Controller.NewController.MainController;
import Controller.Validator.AbstractValidator;
import Controller.Validator.ContextValidator;
import Controller.Validator.Strategy;
import Controller.Validator.Validator;
import Domain.Persone;
import Domain.Relationship;
import Domain.User;
import Utils.Exceptions.Exception;
import Utils.Exceptions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainMenu {
    String currentMode=new String();
    MainController cont;

    public MainMenu(MainController cont) {
        this.cont=cont;
    }

    /**
     * Generates a map with the option and the corespondent number of that option on the maniu
     * @return a map with Integer as Keys and String as Values
     */
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

    /**
     * prints the menu of the screen
     */
    private void printMenu(){
        Map<Integer,String> options= generateOptions();
        System.out.println("Main Manu");
        for(int i=1; i<=options.size();i++)
            System.out.println(Integer.toString(i)+options.get(i));
        System.out.print("Option: ");
    }

    /**
     * The main manu of the application
     */
    public void mainMenu(){
        boolean done=false;
        try {
            //contUser.loadData("user.csv", "friend.csv");
           // contRel.loadData("relation.csv");
            while (!done) {
                printMenu();
                Scanner scan = new Scanner(System.in);
                int option = scan.nextInt();
                switch (option) {
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
                    case 7:
                        printNumberOfCommunities();
                        break;
                    case 8:
                        printTheMostSociableCommunity();
                        break;
                    case 9:
                        done = true;
                        break;
                    case 404:
                        setCurrentMode();
                    default:
                        System.out.println("Invalid Option");
                }
            }

           // contUser.saveData("user.csv", "friend.csv");
            //contRel.saveData("relation.csv");
        }
         catch (Exception e) {
             System.out.println(e.getDescription());
        }
    }

    /**
     * adds an user
     */
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
            scan.nextLine();
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
            System.out.println(user);
            cont.addUser(user);
            System.out.println("User created succesfully");
        }
        catch(UserException e){
            System.out.println(e.getDescription());
        }
        catch(UserRepoException e){
            System.out.println(e.getDescription());
        }
    }

    /**
     * adds a relationship
     */
    private void addRelationship(){
        long id=0;
        String username1=new String();
        String username2=new String();
        Scanner scan = new Scanner(System.in);
        System.out.println("Provide all the necesary information: ");
        if(currentMode.equals("admin")){
            System.out.print("Id: ");
            id= scan.nextLong();
            scan.nextLine();
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
            cont.addRelationship(rel);
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
        catch(Exception e){
            System.out.println(e.getDescription());
        }
    }

    /**
     * removes an user
     */
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

    /**
     * removes a relationship
     */
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

    /**
     * removes a relationship by id
     */
    private void removeRelationshipById(){
        Scanner scan=new Scanner(System.in);
        System.out.println("Id:");
        long id=scan.nextLong();
        try{
            AbstractValidator vali=ContextValidator.createValidator(Strategy.USER);
            vali.checkId(id);
            cont.removeByRelationshipId(id);
            System.out.println("Relationship removed with succes");
        }
        catch (RelationshipRepoException e){
            System.out.println(e.getDescription());
        }
        catch(Exception e){
            System.out.println(e.getDescription());
        }
    }

    /**
     * removes a relationship by the corespondent usernames
     */
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
            cont.removeRelationshipByUsernames(username1,username2);
            System.out.println("Relationship removed with succes");
        }
        catch (RelationshipRepoException e){
            System.out.println(e.getDescription());
        }
        catch(Exception e){
            System.out.println(e.getDescription());
        }

    }

    /**
     * removes an user by id
     */
    private void removeUserById(){
        Scanner scan=new Scanner(System.in);
        System.out.println("Id:");
        long id=scan.nextLong();
        try{
            AbstractValidator vali=ContextValidator.createValidator(Strategy.USER);
            vali.checkId(id);
            cont.removeByUserId(id);
            System.out.println("User removed with succes");
        }
        catch(UserRepoException e){
            System.out.println(e.getDescription());
        }
        catch (RelationshipRepoException e){
            System.out.println(e.getDescription());
        }
        catch(Exception e){
            System.out.println(e.getDescription());
        }
    }

    /**
     * removes an user by username
     */
    private void removeUserByUsername(){
        Scanner scan=new Scanner(System.in);
        System.out.println("Username:");
        String username=scan.nextLine();
        try{
            AbstractValidator vali=ContextValidator.createValidator(Strategy.USER);
            vali.checkUserName(username);
            cont.removeUserByUsername(username);
            System.out.println("User removed with succes");
        }
        catch(UserRepoException e){
            System.out.println(e.getDescription());
        }
        catch (RelationshipRepoException e){
            System.out.println(e.getDescription());
        }
        catch(Exception e){
            System.out.println(e.getDescription());
        }
    }

    /**
     * set the current state of the class
     */
    private void setCurrentMode(){
        Scanner scan=new Scanner(System.in);
        System.out.println("Set the current mode : ");
        currentMode= scan.nextLine();
        System.out.println("Entering to " + currentMode +" mode");
    }

    /**
     * prints all the users to the console
     */
    private void printAllUsers(){
        try {
            List<User> list = cont.getAllUsers();
            for (User el : list) {
                System.out.println(el);
                System.out.println(el.getFriendsList());
            }
        }catch(Exception e){
            System.out.println(e.getDescription());
        }
    }

    /**
     * prints all the relationship to the console
     */
    private void printAllRelationships(){
        try{
            List<Relationship> list=cont.getAllRelationships();
            for (Relationship el: list){
                System.out.println(el);
            }
        }catch(Exception e){
            System.out.println(e.getDescription());
        }
    }

    /**
     * prints the current number of networks to the console
     */
    private void printNumberOfCommunities(){
        System.out.println("Number of active communities" + cont.getNumberOfCommunities());
    }

    /**
     * prints the most sociable network to the console
     */
    private void printTheMostSociableCommunity(){
        List<String>list= cont.getTheMostSociableCommunity();
        System.out.println("The most sociable communty is formed with : ");
        for(String el:list)
            System.out.print(el);
        System.out.println("It has "+ list.size()+ " members");
    }

}
