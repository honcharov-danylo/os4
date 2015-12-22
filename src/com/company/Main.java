package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {
    static RedBlackTree identifiers;
    static JPanel outPanel = new JPanel();;

    public static void main(String[] args) {
        identifiers = new RedBlackTree();
        createGUI();
    }

    public static void createGUI() {
        JFrame frame = new JFrame();
        outPanel.setLayout(new FlowLayout());
        outPanel.setPreferredSize(new Dimension(300,400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 500));
        JPanel jPanel = new JPanel();
        JPanel btnPanel = new JPanel();
        JTextField identName = new JTextField();
        identName.setPreferredSize(new Dimension(50,20));
        JButton AddButton = new JButton("Add");
        JButton SearchButton=new JButton("Search");
        JButton RemoveButton=new JButton("Remove");
        JButton ClearButton=new JButton("Clear");
        RemoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                identifiers.remove(identName.getText());
                identName.setText(new String());
                PrintTree();
                outPanel.revalidate();
                outPanel.repaint();
            }
        });
        ClearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                identifiers.makeEmpty();
                PrintTree();
                outPanel.revalidate();
            }
        });
        SearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    String res= identifiers.findAndGetTrace(identName.getText());
                    identName.setText(new String());
                    if(res!=null)
                    outPanel.add(new JLabel(res));
                    outPanel.revalidate();
            }
        });
        AddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                identifiers.insert(identName.getText());
                identName.setText(new String());
                PrintTree();
            }
        });
        btnPanel.add(identName);
        btnPanel.add(AddButton);
        btnPanel.add(SearchButton);
        btnPanel.add(RemoveButton);
        btnPanel.add(ClearButton);
        PrintTree();

        try {
            BufferedImage myPicture = ImageIO.read(new File("Zarabotok-na-bannernoy-reklame-_5.jpg"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            outPanel.add(picLabel);
        }catch (Exception ex){}

        jPanel.add(outPanel);
        jPanel.add(btnPanel);
        frame.add(jPanel);
        frame.pack();
        frame.setVisible(true);
    }

    static void PrintTree() {
        while (outPanel.getComponentCount() > 0) outPanel.remove(0);
        PrintableNode printableNode=identifiers.header;
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(printableNode.getText());
        RecursiveAdd(root,identifiers.header);
        JTree tree = new JTree(root);
        expandAllNodes(tree);
        tree.setRootVisible(false);
        outPanel.add(tree);
        outPanel.revalidate();
        outPanel.repaint();
       // identifiers.printTree();
    }

    static void RecursiveAdd(DefaultMutableTreeNode parent,RedBlackTree.RedBlackNode currentNode)
    {
        if(currentNode.left!=RedBlackTree.nullNode && currentNode.right!=RedBlackTree.nullNode) {
          //  System.out.println(currentNode.getLeft().getText()+" "+currentNode.getRight().getText());
            DefaultMutableTreeNode left=new DefaultMutableTreeNode(currentNode.getLeft().getText());
            DefaultMutableTreeNode right=new DefaultMutableTreeNode(currentNode.getRight().getText());
            parent.add(left);
            parent.add(right);
            RecursiveAdd(left,currentNode.left);
            RecursiveAdd(right,currentNode.right);
       }
        else if(currentNode.left!=RedBlackTree.nullNode)
        {
            DefaultMutableTreeNode left=new DefaultMutableTreeNode(currentNode.getLeft().getText());
            parent.add(left);
            RecursiveAdd(left,currentNode.left);
        }
        else if(currentNode.right!=RedBlackTree.nullNode)
        {
            DefaultMutableTreeNode right=new DefaultMutableTreeNode(currentNode.getRight().getText());
            parent.add(right);
            RecursiveAdd(right,currentNode.right);
        }
        else {
            return;
        }
    }
    private static void expandAllNodes(JTree tree) {
        int j = tree.getRowCount();
        int i = 0;
        while(i < j) {
            tree.expandRow(i);
            i += 1;
            j = tree.getRowCount();
        }
    }
}
