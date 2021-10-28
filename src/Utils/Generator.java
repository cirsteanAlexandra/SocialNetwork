package Utils;

import java.util.List;
import java.util.Random;

public class Generator {
    public static Long generateId(List<Long> listId){
        Random rand=new Random();
        Long id= rand.nextLong();
        while(isIn(listId,id)){
            id= rand.nextLong();
        }
        return id;
    }
    private static boolean isIn(List<Long> listId,Long elem){
        for (Long el:listId)
            if(el==elem) return true;
        return false;
    }

}
