package vallees;

import java.util.ArrayList;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class Borrar {
	
	static Multimap<Integer,Integer> MP= ArrayListMultimap.create();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MP.put(1,2);
		MP.put(1,3);
		MP.put(1,4);
		MP.put(2,2);
		MP.put(2,4);
		MP.put(3,5);
		MP.put(3,2);
		
		System.out.println(MP);
		
		Iterable<Integer> iter=MP.get(1);
		//MP.remove(1,iter);
		
		MP.putAll(9,iter);
		MP.removeAll(1);
		//if(MP.containsValue(2)){
//		ArrayList<Integer> sa= (ArrayList<Integer>) MP.get(1);			
//		
//		for (Integer integer : sa) {
//			MP.remove(1, integer);
//			MP.put(9, integer);
//		}
		
		
//			MP.remove(1,2);
//			MP.put(1, 99);
//		
//		//}
//		
		System.out.println(MP);

	}

}
