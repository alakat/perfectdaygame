/**
 *
 * @author jjsanchez
 */
import mapTools.*;

public class MapTools {
    
    public static void main(String[] args){
        
        System.out.println("Go");
        MapGenerator mapGen = new MapGenerator();
        MapEntity me = mapGen.generateNewMap(20, 20);
        int[][] logicMap = me.getLogicMap();
                System.out.println("List");
        for (int i = 0; i < logicMap.length; i++)
        {
            String line = "";
            for (int j= 0; j < logicMap[0].length; j++)
            {
                line = line + Integer.toString(logicMap[i][j]);
            }
            System.out.println(line);
        }
    }
}
