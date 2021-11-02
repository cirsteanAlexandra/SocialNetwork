package Repository;

import Domain.Persone;
import Domain.User;
import Utils.Exceptions.EntityRepoException;
import Utils.Generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class UserFileRepo extends FileRepo<Long,User>{
    private String delimiter=new String(",");
    public UserFileRepo() {
        initiateRepo();
    }
    @Override
    public void loadData(String... other){
        loadUsers(other[0]);
        loadFriends(other[1]);
    }
    public void loadUsers(String filename){
        try{
            File file=new File(filename);
            Scanner scan=new Scanner(file);
            while(scan.hasNextLine()){
                String data=scan.nextLine();
                List<String> values= List.of(data.split(delimiter));
                User user= new User(Long.parseLong(values.get(0)),values.get(1), new Persone(values.get(2),values.get(3)));
                save(user);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            throw(new EntityRepoException("The data cannot be loaded"));
        }
    }
    public void loadFriends(String filename)  {
        File file=new File(filename);
        Scanner scan= null;
        try {
            scan = new Scanner(file);
            while(scan.hasNextLine()){
                String data=scan.nextLine();
                List<String> values= List.of(data.split(delimiter));
                Long id=Long.parseLong(values.get(0));
                User user=get(id);
                int times=Integer.parseInt(values.get(1));
                for (int i=0;i<times;i++){
                    user.addFriend(values.get(i+2));
                }
            }
            scan.close();
        } catch (FileNotFoundException e) {
            throw(new EntityRepoException("The data cannot be loaded"));
        }
    }

    @Override
    public void saveData(String... other){
        saveUsers(other[0]);
        saveFriends(other[1]);
    }

    public void saveUsers(String filename){
        try {
            FileWriter file=new FileWriter(filename);
            for(User el: getAll()){
                file.write(el.getId().toString()+delimiter+el.getUsername()+
                            delimiter+el.getPers().getFirstName()+delimiter+el.getPers().getLastName()+"\n");
            }
            file.close();
        } catch (IOException e) {
            throw(new EntityRepoException("The data cannot be saved"));
        }
    }

    public void saveFriends(String filename){
        try {
            FileWriter file=new FileWriter(filename);
            for(User el: getAll()){
                file.write(el.getId().toString()+delimiter+el.getFriendsList().size());
                for(String elem:el.getFriendsList()){
                    file.write(delimiter+elem);
                }
                file.write("\n");
            }
            file.close();
        } catch (IOException e) {
            throw(new EntityRepoException("The data cannot be saved"));
        }
    }

    public User getByUserName(String username){
        User user=null;
        for(User use:getAll()){
            if(use.getUsername().equals(username))
                user=use;
        }
        return user;
    }

    @Override
    public boolean save(User entity) {
        if(getByUserName(entity.getUsername())!=null)return false;
        return saveToRepo(entity);
    }

    @Override
    protected Long generateId() {
        return Generator.generateId(getAllIds());
    }

    @Override
    public User getByOther(String... other) {
        return getByUserName(other[0]);
    }
}
