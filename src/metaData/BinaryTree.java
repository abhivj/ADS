package metaData;

public class BinaryTree {

	public String label;
	public BinaryTree left;
	public BinaryTree right;
	
	public BinaryTree() 
	{
		this.label = "";
		this.left = null;
		this.right = null;
	}
	public void addNode(String label)
	{
		
	}
	public void setLabel(String label)
	{
		this.label = label;
	}
	public void linkNode(BinaryTree parent, BinaryTree child)
	{
		if(parent.left==null)
		{
			parent.left = child;
		}
		else
		{
			parent.right = child;
		}
	}
	public void printInorder(BinaryTree root)
	{
		if(root==null)
			return;
		System.out.print(root.label+"->");
		printInorder(root.left);
		printInorder(root.right);
	}
}
