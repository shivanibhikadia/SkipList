//package skipMe;

import java.util.Scanner;

public class mainSkip {
@SuppressWarnings("resource")
public static void main(String[] args) {
	skiplistMain obj = new skiplistMain();
	
	int k=0;
	int v=0;

	boolean trues = true;
	while(trues)
	{		
		System.out.println("Enter one of the following options 1-Insert 2-Find 3-Delete 4-ClosestKeyAfter 5-Display 6-Exit");
		Scanner input = new Scanner(System.in);
		int x= input.nextInt();
	switch(x) {
	case 1:
		System.out.println("Enter the key and value for the insert with space :");
		 k = Integer.parseInt(input.next());
		 v=Integer.parseInt(input.next());
	     Integer res = obj.insert(k,v);
	     if(res==null) {
	    	 System.out.println("old value: None");
	     }
	     else {
	    	 System.out.println("old value: "+res);
	     }
	     obj.print();
		break;
	case 2:
		System.out.println("Enter the number for the Find");
		k = input.nextInt();
		Integer a =obj.search(k);
		if(a!=null) {
			System.out.println("key is found and");}
		else 
			System.out.println("the key is not found");
		
		break;
	case 3:
		System.out.println("enter number to delete");
		k = input.nextInt();
		Integer del=obj.delete(k);
		if(del==null)
		{
			System.out.println("key is not found to delete");
		}
		obj.print();
	    break;
	    
	case 4:
		System.out.println("Enter the number for closestKeyAfter");
		 k = input.nextInt();
		Integer b =obj.closest(k);
		if(b==null) {
			System.out.println("closest Key +ve infinity");	
		}
		else
		System.out.println("Closest key next is "+ b);
		
	    break;
	case 5:
		System.out.println("Data printed");
	    obj.print();
	    break;
	case 6:
		trues=false;
		
	}
	
	}
	
}
}