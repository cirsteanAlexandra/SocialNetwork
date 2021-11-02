package Utils.Algoritms;

import Domain.Relationship;
import Repository.RelationshipMemoryRepo;

import java.util.*;

public class Graph {
    int numberOfVertices;
    int numberOfEdges;
    List<Pair<Integer,Integer>> listEdges;

    public Graph(int numberOfVertices, int numberOfEdges) {
        this.numberOfVertices = numberOfVertices;
        this.numberOfEdges = numberOfEdges;
        listEdges=new ArrayList<>();
    }

    public Graph() {
        listEdges=new ArrayList<>();
    }

    public void setNumberOfVertices(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
    }

    public void setNumberOfEdges(int numberOfEdges) {
        this.numberOfEdges = numberOfEdges;
    }

    public void addEdge(int f, int s){
        listEdges.add(new Pair<>(f,s));
    }

    public void removeEdge(int f,int s){
        listEdges.remove(new Pair<>(f,s));
    }

    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    public int getNumberOfEdges() {
        return numberOfEdges;
    }

    public List<Pair<Integer, Integer>> getListEdges() {
        return listEdges;
    }

    public int numberOfCommunities(RelationshipMemoryRepo repo){
        convertRepo(repo);
        return numberOfConexComponents();
    }

    public List<String> theMostSociableCommunity(RelationshipMemoryRepo repo){
        Map<String,Integer> listOfVer=convertRepo(repo);
        List<Integer> listOfParticipants=theLongestElementeryRoad();
        return convertIntToUsername(listOfParticipants,listOfVer);
    }

    public List<String>convertIntToUsername(List<Integer>listParticipants,Map<String,Integer>listOfVer){
        List<String>list= new ArrayList<>();
        for(Map.Entry<String,Integer> el: listOfVer.entrySet()){
            if(listParticipants.get(el.getValue())==1){
                list.add(el.getKey());
            }
        }
        return list;
    }
    public List<Integer> theLongestElementeryRoad(){
        int maxim=0;
        List<Integer> listParticipants= new ArrayList<>(numberOfVertices);
        Map<Integer,List<Integer>> adiacencyList=getAdiacencyList();
        for (int i=1;i<=numberOfVertices;i++){
            List<Integer> listColours= new ArrayList<>(numberOfVertices);
            List<Integer> listPath= new ArrayList<>(numberOfVertices);
            if(adiacencyList.get((Integer) i).size()==1){
                bfs((Integer)i,adiacencyList,listColours,listPath);
                int max=Collections.max(listPath);
                if(max>maxim){
                    maxim=max;
                    listParticipants=listColours;
                }
            }
        }
        List<Integer> listOfElems= new ArrayList<>();
        for (Integer i=0;i<listParticipants.size();i++){
            if(listParticipants.get(i)==1){
                listOfElems.add(i);
            }
        }
        return listOfElems;
    }


    public void bfs(Integer node,Map<Integer,List<Integer>> adiacencyList,List<Integer> listColours,List<Integer> listPath){
        Queue<Integer> queue= new LinkedList<>();
        queue.add(node);
        listPath.set(node,0);
        while(!queue.isEmpty()){
            Integer currentNode=queue.remove();
            for(Integer neighb: adiacencyList.get(node)){
                if(listColours.get(neighb)==null){
                    queue.add(neighb);
                    listPath.set(neighb,listPath.get(currentNode)+1);
                }
            }
            listColours.set(currentNode,1);
        }
    }

    public int numberOfConexComponents(){
        Map<Integer,List<Integer>> adiacencyList=getAdiacencyList();
        List<Integer> listVisited= new ArrayList<>(numberOfVertices);
        int numberOfComponents=0;
        for(int i=1;i<=numberOfVertices;i++){
            if(listVisited.get(i)==null){
                numberOfComponents++;
                dfs((Integer) i,listVisited,adiacencyList);
            }
        }
        return numberOfComponents;
    }

    public void dfs(Integer node,List<Integer> listVisited,Map<Integer,List<Integer>> adiacencyList){
        if(listVisited.get(node)==null){
            listVisited.set(node,1);
            if(adiacencyList.get(node)!=null)
                for(Integer el:adiacencyList.get(node)){
                    dfs(el,listVisited,adiacencyList);
                }
        }
    }

    public Map<Integer,List<Integer>> getAdiacencyList(){
        Map<Integer,List<Integer>> adLs=new HashMap<>();
        for(Pair el: listEdges){
            if(adLs.get(el.getFirst())==null)
                adLs.put((Integer) el.getFirst(),new ArrayList<>());
            if(adLs.get(el.getSecond())==null)
                adLs.put((Integer) el.getSecond(),new ArrayList<>());
            adLs.get(el.getFirst()).add((Integer) el.getSecond());
            adLs.get(el.getSecond()).add((Integer) el.getFirst());
        }
        return adLs;
    }

    public Map<String,Integer> convertRepo(RelationshipMemoryRepo repo){
        Map<String,Integer> listOfVer=convertUsernameToInt(repo);
        fillEdges(repo,listOfVer);
        return listOfVer;
    }

    public Map<String,Integer> convertUsernameToInt(RelationshipMemoryRepo repo){
        Map<String,Integer> listOfVer=new HashMap();
        for(Relationship rel: repo.getAll()){
            if(listOfVer.get(rel.getFirstUserName())==null){
                listOfVer.put(rel.getFirstUserName(), listOfVer.size()+1);
            }
            if(listOfVer.get(rel.getSecondUserName())==null){
                listOfVer.put(rel.getSecondUserName(), listOfVer.size()+1);
            }
        }
        return listOfVer;
    }

    public void fillEdges(RelationshipMemoryRepo repo, Map<String,Integer> listOfVer){
        for(Relationship rel: repo.getAll()){
            addEdge(listOfVer.get(rel.getFirstUserName()),listOfVer.get(rel.getSecondUserName()));
        }
    }
}
