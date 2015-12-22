// RedBlackTree class
//
// CONSTRUCTION: with a negative infinity sentinel
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// Comparable find( x )   --> Return item that matches x
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print all items
// ******************ERRORS********************************
// Exceptions are thrown by insert if warranted and remove.

package com.company;

public class RedBlackTree {
    /**
     * Construct the tree.
     */
    public RedBlackTree( ) {
        header      = new RedBlackNode( null );
        header.left = header.right = nullNode;
    }

    /**
     * Compare item and t.element, using compareTo, with
     * caveat that if t is header, then item is always larger.
     * This routine is called if is possible that t is header.
     * If it is not possible for t to be header, use compareTo directly.
     */
    private final int compare( Comparable item, RedBlackNode t ) {
        if( t == header )
            return 1;
        else
        {
           // System.out.println("lol "+item.compareTo( t.element ));
            return item.compareTo( t.element );}
    }


    public void insert( Comparable item ) {
        current = parent = grand = header;
        nullNode.element = item;

        while( compare( item, current ) != 0 ) {
            great = grand; grand = parent; parent = current;
            current = compare( item, current ) < 0 ?
                    current.left : current.right;

            // Check if two red children; fix if so
            if( current.left.color == RED && current.right.color == RED )
                handleReorient( item );
        }

        // Insertion fails if already present
        if( current != nullNode )
            throw new DuplicateItemException( item.toString( ) );
        current = new RedBlackNode( item, nullNode, nullNode );

        // Attach to parent
        if( compare( item, parent ) < 0 )
            parent.left = current;
        else
            parent.right = current;
        handleReorient( item );
    }

    /**
     * Remove from the tree.
     * @param x the item to remove.
     * @throws UnsupportedOperationException if called.
     */
    public void remove( Comparable x ) {
        //throw new UnsupportedOperationException( );
    RedBlackTree rd=addForRemove(x,header,new RedBlackTree());
    header=rd.header;
    }
    private RedBlackTree addForRemove(Comparable x,RedBlackNode currentNode,RedBlackTree newTree)
    {
       try{ if(x.compareTo(currentNode.element)!=0)newTree.insert(currentNode.element);} catch (Exception exc){System.out.println(x+" "+currentNode.element);}

        if(currentNode.left!=nullNode) addForRemove(x,currentNode.left,newTree);
        if(currentNode.right!=nullNode) addForRemove(x,currentNode.right,newTree);
        return newTree;
    }

    public Comparable findMin( ) {
        if( isEmpty( ) )
            return null;

        RedBlackNode itr = header.right;

        while( itr.left != nullNode )
            itr = itr.left;

        return itr.element;
    }


    public Comparable findMax( ) {
        if( isEmpty( ) )
            return null;

        RedBlackNode itr = header.right;

        while( itr.right != nullNode )
            itr = itr.right;

        return itr.element;
    }


    public Comparable find( Comparable x ) {
        nullNode.element = x;
        current = header.right;

        for( ; ; ) {
            if( x.compareTo( current.element ) < 0 )
                current = current.left;
            else if( x.compareTo( current.element ) > 0 )
                current = current.right;
            else if( current != nullNode )
                return current.element;
            else
                return null;
        }
    }


    public String findAndGetTrace( Comparable x ) {
        nullNode.element = x;
        current = header.right;
        String Path=current.getText()+"->";
        if(x.compareTo(current.element)<0) Path+=
                Path+=current.left.getText()+"->";
        else Path+=current.right.getText()+"->";

        for( ; ; ) {
            if( x.compareTo( current.element ) < 0 )
            {
                current = current.left;
                Path+=current.left.getText()+"->";
            }
            else if( x.compareTo( current.element ) > 0 )
            {
                current = current.right;
                Path+=current.right.getText()+"->";
            }
            else if( current != nullNode )
            {
               // return current.element;
                Path= Path.substring(0,Path.length()-2);
                int kostul=Path.length()-Path.lastIndexOf("->");
                return Path.substring(0,Path.length()-kostul);
            }
            else
                return null;
        }
    }



    public void makeEmpty( ) {
        header.right = nullNode;
    }


 void printTree( ) {
        printTree( header.right );
    //print(header.right);
    }


    private void printTree( RedBlackNode t) {
        if( t != nullNode ) {
            printTree( t.left);
            System.out.println( t.element );
            printTree( t.right);
     // print(t);
        }
    }

    public boolean isEmpty( ) {
        return header.right == nullNode;
    }

    /**
     * Internal routine that is called during an insertion
     * if a node has two red children. Performs flip and rotations.
     * @param item the item being inserted.
     */
    private void handleReorient( Comparable item ) {
        // Do the color flip
        current.color = RED;
        current.left.color = BLACK;
        current.right.color = BLACK;

        if( parent.color == RED )   // Have to rotate
        {
            grand.color = RED;
            if( ( compare( item, grand ) < 0 ) !=
                    ( compare( item, parent ) < 0 ) )
                parent = rotate( item, grand );  // Start dbl rotate
            current = rotate( item, great );
            current.color = BLACK;
        }
        header.right.color = BLACK; // Make root black
    }

    /**
     * Internal routine that performs a single or double rotation.
     * Because the result is attached to the parent, there are four cases.
     * Called by handleReorient.
     * @param item the item in handleReorient.
     * @param parent the parent of the root of the rotated subtree.
     * @return the root of the rotated subtree.
     */
    private RedBlackNode rotate( Comparable item, RedBlackNode parent ) {
        if( compare( item, parent ) < 0 )
            return parent.left = compare( item, parent.left ) < 0 ?
                    rotateWithLeftChild( parent.left )  :  // LL
                    rotateWithRightChild( parent.left ) ;  // LR
        else

            return parent.right = compare( item, parent.right ) < 0 ?
                    rotateWithLeftChild( parent.right ) :  // RL
                    rotateWithRightChild( parent.right );  // RR
    }

    /**
     * Rotate binary tree node with left child.
     */
    private static RedBlackNode rotateWithLeftChild( RedBlackNode k2 ) {
        RedBlackNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        return k1;
    }

    /**
     * Rotate binary tree node with right child.
     */
    private static RedBlackNode rotateWithRightChild( RedBlackNode k1 ) {
        RedBlackNode k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        return k2;
    }

    public static class RedBlackNode implements PrintableNode{
        public String getText()
        {
            return (String)element;
        }
        public PrintableNode getLeft()
        {
            return left;
        }

        public PrintableNode getRight()
        {
            return right;
        }
        // Constructors
        RedBlackNode( Comparable theElement ) {
            this( theElement, null, null );
        }

        RedBlackNode( Comparable theElement, RedBlackNode lt, RedBlackNode rt ) {
            element  = theElement;
            left     = lt;
            right    = rt;
            color    = RedBlackTree.BLACK;
        }

        public boolean isLeaf() {
            if(left == nullNode && right == nullNode) {
                return false;
            }
            return true;
        }

        Comparable   element;    // The data in the node
        RedBlackNode left;       // Left child
        RedBlackNode right;      // Right child
        int          color;      // Color
    }

    public RedBlackNode header;
    public static RedBlackNode nullNode;
    static         // Static initializer for nullNode
    {
        nullNode = new RedBlackNode( null );
        nullNode.left = nullNode.right = nullNode;
    }

    private static final int BLACK = 1;    // Black must be 1
    private static final int RED   = 0;

    // Used in insert routine and its helpers
    private static RedBlackNode current;
    private static RedBlackNode parent;
    private static RedBlackNode grand;
    private static RedBlackNode great;
}

