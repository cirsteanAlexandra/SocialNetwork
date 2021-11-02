package Repository;

import Domain.Relationship;
import Utils.Exceptions.EntityRepoException;
import Utils.Generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class RelationshipFileRepo extends FileRepo<Long,Relationship>{
    private String delimiter=new String(",");

    public RelationshipFileRepo() {
        initiateRepo();
    }

    @Override
    public void loadData(String... other){
        loadRelationship(other[0]);
    }

    public void loadRelationship(String filename){
        try{
            File file=new File(filename);
            Scanner scan=new Scanner(file);
            while(scan.hasNextLine()){
                String data=scan.nextLine();
                List<String> values= List.of(data.split(delimiter));
                Relationship rel= new Relationship(Long.parseLong(values.get(0)),values.get(1), values.get(2));
                save(rel);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            throw(new EntityRepoException("The data cannot be loaded"));
        }
    }


    @Override
    public void saveData(String... other){
        saveRelationship(other[0]);
    }

    public void saveRelationship(String filename){
        try {
            FileWriter file=new FileWriter(filename);
            for(Relationship el: (List<Relationship>)getAll()){
                file.write(el.getId().toString()+delimiter+el.getFirstUserName()+
                        delimiter+el.getSecondUserName()+"\n");
            }
            file.close();
        } catch (IOException e) {
            throw(new EntityRepoException("The data cannot be saved"));
        }
    }

    @Override
    public boolean save(Relationship entity) {
        if(getByUserNames(entity.getFirstUserName(), entity.getSecondUserName())!=null)return false;
        return saveToRepo(entity);
    }


    @Override
    protected Long generateId() {
        return Generator.generateId(getAllIds());
    }

    @Override
    public Relationship getByOther(String... other) {
        return getByUserNames(other[0],other[1]);
    }

    public Relationship getByUserNames(String username1, String username2){
        Relationship relation=null;
        for(Relationship rel:(List<Relationship>)getAll()){
            if(rel.getFirstUserName().equals(username1) && rel.getSecondUserName().equals(username2))
                relation=rel;
        }
        return relation;
    }
}
