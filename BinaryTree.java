import java.util.Scanner;

public class BinaryTree {
	
	public static void main(String[] args) {  
		new BinaryTree().Main(); 
 		}

	class Node {
		int value;
  		Node left;
  		Node right;
  		public Node(int val) {		//constructor: it allows variable initialisation
			this.value = val;
		}
	}

	public void Main() {
		int opt;
		Scanner input = new Scanner(System.in);	 //class system.in defines standard i/p keyboard
		System.out.printf("Enter the number to be inserted as root: ");
		Node rootnode = new Node(input.nextInt());
 		System.out.println("Building tree with rootvalue " + rootnode.value); 
		do {
			System.out.printf("\n\n\t\tMAIN MENU\n\t1. Insertion\n\t2. Inorder\n\t");
			System.out.printf("3. Preorder\n\t4. postorder\n\t5. search\n\t");
			System.out.printf("6. delete\n\t7. exit\n");
			System.out.printf("Choose any option: ");
			opt = input.nextInt();
			switch (opt)
			{
				case 1: System.out.printf("\nEnter the number to be inserted : ");
					insert(rootnode, input.nextInt());
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
					search(rootnode, input.nextInt());
					break;
				case 6: System.out.printf("\nEnter the element to be deleted: ");
					if(delete(input.nextInt(), rootnode) == null)
						System.out.println("Element is not found");
					else
						System.out.println("Element is deleted");
					break;
 				case 7: break;
				default:System.out.println("\nInvalid Entry!\n");
			}
		} while(opt>=1 && opt<=6);
 	}  

	public void insert(Node node, int value) {
		if (value < node.value) {
	 		if (node.left != null) {
				insert(node.left, value);
		 		}
	 		else {
		 		System.out.println("  Inserted " + value);  //to left of node; node.value
		 		node.left = new Node(value);
	 		}
 		}
 		else if (value > node.value) { 
	 		if (node.right != null) { 
		 		insert(node.right, value); 
		 	} 
			else {  
				System.out.println("  Inserted " + value); //to right of node; node.value; 
				node.right = new Node(value);
	  		}
  		} //else do nothing since value == Node.value
	}

	public Node findMin( Node node) {	//to find the minimum element in the tree
		if( node == null )
        		return null;
        	else if( node.left == null )
                	return node;
        	return findMin( node.left );
	}

	public Node delete( int x, Node node ) {	//delete the node
        	if( node == null )
                	return node;   // If node address not found; do nothing
        	if( x < node.value )
                	node.left = delete( x, node.left );
        	else if( x > node.value )
                	node.right = delete( x, node.right);
        	else if( node.left != null && node.right != null ) { //if Two children exists
                	node.value = findMin( node.right ).value;
                	node.right = delete( node.value, node.right );
                }
        	else
                	node = ( node.left != null ) ? node.left : node.right;
        	return node;	//if node have no childs only this statement executes
	}

	public void printInorder(Node node) {	//inorder traversal
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

	public void printPostorder(Node node) {	    //postorder traversal
		if (node != null) {
			printPostorder(node.left);
			printPostorder(node.right);
			System.out.printf("\t" + node.value);
		}
	}

	public void search (Node node, int val) { //method to check the existance of an element
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

}
