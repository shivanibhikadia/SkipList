//package skipMe;
import java.util.*;
public class skiplistMain {
  
	Random rand =new Random();
	private class Node{
		int key;
		int value;
		Node next;
		@SuppressWarnings("unused")
		Node prev;
		
		  public Node(int key,int value)
		   {
			   this.value=value;
			   this.key =key;
			   this.next=null;
			   this.prev =null;
		   }
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	List<Node> levels=new ArrayList();
	
	int size;
	
	public Integer insert(int key, int value) {
		
		Node inode = new Node(key,value);
		
		if(levels.size()==0){
			
			levels.add(new Node(key,value));
			return null;
			
		}
		
		Node head = levels.get(0);
		Node curr = head;
		Node previous = null;
		
		while(curr!=null && curr.key<key) {
			
			previous = curr;
			curr=curr.next;
			
		}
		
		if(curr==null) {
			previous.next=inode;
			inode.prev=previous;
		}
		else {
			if(curr.key==key) {
				int temp = curr.value;
				
				curr.value=value;
				return temp;
			}
			if(previous==null) {
				levels.set(0, inode);
				inode.next=head;
				head.prev=inode;
			}
			else {
				curr.prev =inode;
				inode.prev=previous;
				previous.next=inode;
				inode.next=curr;
			}
		}
		
		int i=0;
		
		while(rand.nextBoolean()==true)
		{
			Node innode = new Node(key,value);
		
			i++;
			if(levels.size()-1<i) {
				levels.add(innode);
				continue;
			}
			
			Node headd = levels.get(i);
			Node currr = headd;
			Node previouss = null;
		
			while(currr!=null && currr.key<key) {
				previouss = currr;
				currr=currr.next;
			}
		
			if(currr==null) {
				previouss.next=innode;
				innode.prev=previouss;
				
			}
			
			else {
			
				if(previouss==null) {
					levels.set(i, innode);
					innode.next=headd;
					headd.prev=innode;
				}
				else {
				
					currr.prev =innode;
					innode.prev=previouss;
					previouss.next=innode;
					innode.next=currr;
				}			
			}			
		}
		size++;
		return null;
	}
	
	
	@SuppressWarnings("unused")
	public Integer search(int n) {
		int a =levels.size();

		
		Node head = levels.get(0);
		Node curr=head;
		while(curr!=null && curr.key<n) {
			curr=curr.next;
		}
		
		if(curr==null) {
			return null;
		}
		
		return curr.key==n?0:null;
		

	}
	
	@SuppressWarnings("unused")
	public Integer closest(int n) {
		int a =levels.size();
		
		Node head = levels.get(0);
		Node curr=head;
		while(curr!=null && curr.key<n) {
			curr=curr.next;
		}
		
		if(curr==null) {
			return null;
		}
		
		return curr.key;
	}
	
public Integer delete(int key) {
		
		int noOflevels=levels.size()-1;
		int i;
		for(i=0;i<=noOflevels;i++) {
			if(!deleteElement(key,i)) {
				break;
			}
		}
		if(i!=0) {
			size--;
		}
		return i==0?null:key;
	}
	
	public boolean deleteElement(int key, int level) {
		
		Node head = levels.get(level);
		Node prev = null;
		Node curr=head;
		
		if(head.key==key) {
			Node newhead = head.next;
			levels.set(level, newhead);
			return true;
		}
		
		while(curr!=null) {
			
			
			if(curr.key==key) {
				prev.next=curr.next;
				return true;
			}
			
			prev=curr;
			curr=curr.next;
			
		}
		
		
		
		return false;
	}
	
	
	
	void print() {
		int s = levels.size()-1;
		while(s>=0) {
			if(levels.get(s)!=null) {
			System.out.println("Level :"+s+" ");
			}
			Node h=levels.get(s);
			while(h!=null) {
				if(s==0) {
					System.out.print(h.key+"("+h.value+")");
				}
				else {
					System.out.print(h.key+" ");
				}
				
				h=h.next;
			}
			System.out.println("");
			s--;
			
		}
	}
	
	
	
}