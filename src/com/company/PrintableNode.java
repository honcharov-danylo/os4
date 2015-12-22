package com.company;

/**
 * Created by leon on 18.12.15.
 */
public interface PrintableNode {
    PrintableNode getLeft();


    /** Get right child */
    PrintableNode getRight();


    /** Get text to be printed */
    String getText();
}
