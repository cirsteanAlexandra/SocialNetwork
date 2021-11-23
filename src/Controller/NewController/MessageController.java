package Controller.NewController;

import Domain.Message;
import Repository.Db.MessageDbRepo;

import java.util.List;

public class MessageController extends Controller<Long, Message>{
    private MessageDbRepo repoM;
    public MessageController(MessageDbRepo repo){super.repo=repo;
    repoM=repo;}

    public List<Message> loadConversation(String username1,String username2){
        List<Message> listMess= repoM.getBySR(username1,username2);
        for(var el:repoM.getBySR(username1,username2))
            listMess.add(el);
        return listMess;
    }

    public List<Message> getAll(){
        return repo.getAll();
    }
}
