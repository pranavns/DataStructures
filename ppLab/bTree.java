import java.io.*;

public class bTree {
	public static void main(String[] args) { 
		try { 
			new bTree().Main();
		} catch (IOException exc) {
			System.out.println("Error: IOException throwed"); 
 		}
	}

	static class Node {
  		Node left;
  		Node right;
  		int value;
  		public Node(int val) {
			this.value = val;
		}
	}

	public void Main() throws IOException {
		int opt;
		InputStreamReader inps= new InputStreamReader(System.in);
		BufferedReader buf= new BufferedReader(inps);
		System.out.printf("Enter the number to be inserted as root: ");
 		Node rootnode = new Node(Integer.parseInt(buf.readLine())); 
 		System.out.println("Building tree with rootvalue " + rootnode.value); 
		do {
			System.out.printf("\n\t\tMAIN MENU\n\t1. Insertion  2. Inorder  ");
			System.out.printf("3. Preorder  4. postorder  5. search  ");
			System.out.printf("6. delete  7. find parent  8.find minimum  9. exit\n");
			System.out.printf("\tChoose any option: ");
			opt = Integer.parseInt(buf.readLine());
			switch (opt)
			{
				case 1: System.out.printf("\nEnter the number to be inserted : ");
					insert(rootnode, Integer.parseInt(buf.readLine()));
					break;
				case 2: System.out.printf("\nThe Inorder traversal\n");
					printInorder(rootnode);
					break;
				case 3: System.out.printf("\nThe Preorder traversal\n");
					printPreorder(rootnode);
					break;
				case 4: System.out.printf("\nThe Postorder traversal\n");
					printPostorder(rootnode);
					break;
				case 5: System.out.printf("\nEnter the element to be searched: ");
					search(rootnode, Integer.parseInt(buf.readLine()));
					break;
				case 6: System.out.printf("\nEnter the element to be deleted: ");
					if(delete(Integer.parseInt(buf.readLine()), rootnode) == null)
						System.out.println("Element is not found");
					else
						System.out.println("Element is deleted");
					break;
				case 7: System.out.printf("\nEnter the element to find its parent: ");
					parent(rootnode, Integer.parseInt(buf.readLine()));
					break;
				case 8: Node temp = findMin(rootnode);
					if (temp != null)
						System.out.println("Minmum valued element is " + temp.value);
					else
						System.out.println("This node doesnot exist ");
					break;
 				case 9: System.exit(0);
				default:System.out.println("\nInvalid Entry!\n");
			}
		} while(true);
 	}  

	public void insert(Node node, int value) {
		if (value < node.value) { 
	 		if (node.left != null) {  
				insert(node.left, value); 
		 		}
	 		else { 
		 		System.out.println(value + "  Inserted ");
		 		node.left = new Node(value); 
	 		} 
 		}
 		else if (value > node.value) { 
	 		if (node.right != null) { 
		 		insert(node.right, value); 
		 	} 
			else {  
				System.out.println(value + "  Inserted ");
				node.right = new Node(value);
	  		}
  		} //else continue do nothing if value == Node.value
	}

	public Node findMin( Node node ) {	//to find the minimum element in the tree
		if( node == null )
        		return null;
        	else if( node.left == null )
                	return node;
        	return findMin( node.left );
	}

	public Node delete( int x, Node node ) {	//recursive deletion of the node
        	if( node == null )
                	return node;   // Node pointer not available => do nothing
        	if( x < node.value )
                	node.left = delete( x, node.left );
        	else if( x > node.value )
                	node.right = delete( x, node.right );
        	else if( node.left != null && node.right != null ) { // Two children exist then replacing it with minimum element of right child
                	node.value = findMin( node.right ).value;
                	node.right = delete( node.value, node.right );
                }
        	else
                	node = ( node.left != null ) ? node.left : node.right;
        	return node;
	}

	public void printInorder(Node node) {		//inorder traversal
	  	if (node != null) {
		  	printInorder(node.left);
		  	System.out.printf("\t" + node.value); 
		  	printInorder(node.right);
	 	}
	}

	public void printPreorder(Node node) {		//preorder traversal
	 	if (node != null) {
			System.out.printf("\t" + node.value);
			printPreorder(node.left);
			printPreorder(node.right);
		}
	}

	public void printPostorder(Node node) {		//postorder traversal
		if (node != null) {
			printPostorder(node.left);
			printPostorder(node.right);
			System.out.printf("\t" + node.value);
		}
	}

	public void search (Node node, int val) {	//method to check the existance of an element
		if (node == null)
			System.out.printf("\nThis node doesn't exist\n");
		else {
			if (node.value == val)
				System.out.printf("\nNode exist\n");
			else if (node.value < val)
				search(node.right, val);
			else if (node.value > val)
				search (node.left, val);
		}
	}

	public void parent (Node node, int val) {		//parent of the node
		if (node == null) {
			System.out.printf("\nThis node does not exist\n");
			return;
		}
		if (val == node.value)
			System.out.printf("\nThis is a rootnode hence it has no parent\n");		
		else if (val < node.value) {
			if(val == node.left.value) {
				System.out.println("Parent is " + node.value);	 
				return;
			}
			parent(node.left, val);
		}
		else if (val > node.value) {
			if(val == node.right.value) {
				System.out.println("Parent is " + node.value);	 
				return;
			}
			parent(node.right, val);
		}
	}
}
