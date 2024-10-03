/*Authors: Danann Gallagher, Juan Carlos Muratalla-Sanchez 
 * Date: 12/2/22
 * Class: CSCI 241
 * Purpose: Provides unit tests for methods implemented in ShortestPaths.java
 */
package graph;

import static org.junit.Assert.*;
import org.junit.FixMethodOrder;

import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.net.URL;
import java.io.FileNotFoundException;

import java.util.LinkedList;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShortestPathsTest {


    /* Returns the Graph loaded from the file with filename fn. */
    private Graph loadBasicGraph(String fn) {
        Graph result = null;
        try {
          result = ShortestPaths.parseGraph("basic", fn);
        } catch (FileNotFoundException e) {
          fail("Could not find graph " + fn);
        }
        return result;
    }

    /* Returns the Graph loaded from the file with filename fn. */
    private Graph loadDBGraph(String fn) {
      Graph result = null;
      try {
        result = ShortestPaths.parseGraph("db", fn);
      } catch (FileNotFoundException e) {
        fail("Could not find graph " + fn);
      }
      return result;
  }

    /** Dummy test case demonstrating syntax to create a graph from scratch.
     * Write your own tests below. */
    @Test
    public void test00Nothing() {
        Graph g = new Graph();
        Node a = g.getNode("A");
        Node b = g.getNode("B");
        g.addEdge(a, b, 1);

        // sample assertion statements:
        assertTrue(true);
        assertEquals(2+2, 4);
    }

    /** Minimal test case to check the path from A to B in Simple0.txt */
    @Test
    public void test01Simple0() {
        Graph g = loadBasicGraph("Simple0.txt");
        g.report();
        ShortestPaths sp = new ShortestPaths();
        Node a = g.getNode("A");
        sp.compute(a);
        Node b = g.getNode("B");
        LinkedList<Node> abPath = sp.shortestPath(b);
        assertEquals(abPath.size(), 2);
        assertEquals(abPath.getFirst(), a);
        assertEquals(abPath.getLast(),  b);
        assertEquals(sp.shortestPathLength(b), 1.0, 1e-6);
    }

    /* Pro tip: unless you include @Test on the line above your method header,
     * gradle test will not run it! This gets me every time. */
    
    //Edge case where there is no path from A to S in NoPath.txt
    @Test
    public void test02NoPath() {
        Graph g = loadBasicGraph("NoPath.txt");
        g.report();
        ShortestPaths sp = new ShortestPaths();
        Node s = g.getNode("S");
        Node a = g.getNode("A");
        sp.compute(a);
        LinkedList<Node> asPath = sp.shortestPath(s);
        assertEquals(asPath, null);
    }

    //More complicated test for path length for S to B for Simple1.txt
    @Test
    public void test03Simple1() {
        Graph g = loadBasicGraph("Simple1.txt");
        g.report();
        ShortestPaths sp = new ShortestPaths();
        Node s = g.getNode("S");
        sp.compute(s);
        Node b = g.getNode("B");
        Node c = g.getNode("C");
        Node a = g.getNode("A");
        LinkedList<Node> abPath = sp.shortestPath(b);
        assertEquals(abPath.size(), 4);
        assertEquals(abPath.get(0), s);
        assertEquals(abPath.get(1), c);
        assertEquals(abPath.get(2), a);
        assertEquals(abPath.get(3), b);
        assertEquals(sp.shortestPathLength(b), 9.0, 1e-6);
    }

    //Longer path on more complex file, D to G for Simple2.txt
    @Test
    public void test04Simple2() {
        Graph g = loadBasicGraph("Simple2.txt");
        g.report();
        ShortestPaths sp = new ShortestPaths();
        Node d = g.getNode("D");
        sp.compute(d);
        Node a = g.getNode("A");
        Node e = g.getNode("E");
        Node f = g.getNode("F");
        Node i = g.getNode("I");
        Node j = g.getNode("J");
        Node gg = g.getNode("G");
        LinkedList<Node> abPath = sp.shortestPath(gg);
        assertEquals(abPath.size(), 7);
        assertEquals(abPath.get(0), d);
        assertEquals(abPath.get(1), a);
        assertEquals(abPath.get(2), e);
        assertEquals(abPath.get(3), f);
        assertEquals(abPath.get(4), i);
        assertEquals(abPath.get(5), j);
        assertEquals(abPath.get(6), gg);
        assertEquals(sp.shortestPathLength(gg), 12.0, 1e-6);
    }

    //Longer path on more complex file, D to G for Simple2.txt
    @Test
    public void test05Simple2() {
      Graph g = loadBasicGraph("Simple2.txt");
      g.report();
      ShortestPaths sp = new ShortestPaths();
      Node e = g.getNode("E");
      sp.compute(e);
      Node f = g.getNode("F");
      Node b = g.getNode("B");
      LinkedList<Node> abPath = sp.shortestPath(b);
      assertEquals(abPath.size(), 3);
      assertEquals(abPath.get(0), e);
      assertEquals(abPath.get(1), f);
      assertEquals(abPath.get(2), b);
      assertEquals(sp.shortestPathLength(b), 4.0, 1e-6);
    }

    /**Moderate test case on large csv file, already provided, tests functionality 
     * of Shortest Paths with real-world sample/accessiblity score. Follows path 
     * from -122.2962144624761&4762281865920708 to -122.2963178&47.6231077 and
     * checks for accuracy*/
     @Test
    public void test06DBCrop() {
        Graph g = loadDBGraph("DBCrop.csv");
        g.report();
        ShortestPaths sp = new ShortestPaths();
        Node origin = g.getNode("-122.2962144624761&4762281865920708");
        sp.compute(origin);
        Node middle = g.getNode("-122.2961957&47.6228314");
        Node dest = g.getNode("-122.2963178&47.6231077");
        LinkedList<Node> fToLPath = sp.shortestPath(dest);
        assertEquals(fToLPath.size(), 3);
        assertEquals(fToLPath.getFirst(), origin);
        assertEquals(fToLPath.get(1), middle);
        assertEquals(fToLPath.getLast(), dest);
        assertEquals(sp.shortestPathLength(dest), 1.976926528, 1e-6); 
    }

    /**Moderate test case on large csv file, provided by us, tests functionality of
     * Shortest Paths with real-world sample/accessiblity score. Follows path 
     * from -122.3234027&47.6152423 to multiple other nodes, -122.3234419&47.6168731 and
     * -122.3233799&47.6163916, checks for accuracy*/
    @Test
    public void test07SBCrop() {
        Graph g = loadDBGraph("SBCrop.csv");
        g.report();
        ShortestPaths sp = new ShortestPaths();
        Node origin = g.getNode("-122.3234027&47.6152423");
        sp.compute(origin);
        Node middle = g.getNode("-122.3234327&47.6163758");
        Node dest1 = g.getNode("-122.3233799&47.6163916");
        Node dest2 = g.getNode("-122.3234419&47.6168731");
        LinkedList<Node> dest1Path = sp.shortestPath(dest1);
        LinkedList<Node> dest2Path = sp.shortestPath(dest2);

        //first desination node, sharing middle node
        assertEquals(dest1Path.size(), 3);
        assertEquals(dest1Path.getFirst(), origin);
        assertEquals(dest1Path.get(1), middle);
        assertEquals(dest1Path.getLast(),  dest1);
        assertEquals(sp.shortestPathLength(dest1), 0.964174294912608, 1e-6); 

        //second destination node, sharing middle node
        assertEquals(dest2Path.size(), 3);
        assertEquals(dest2Path.getFirst(), origin);
        assertEquals(dest2Path.get(1), middle);
        assertEquals(dest2Path.getLast(),  dest2);
        assertEquals(sp.shortestPathLength(dest2), 1.081876034677989, 1e-6);
    }

        /**Moderate test case on large csv file, provided by us, tests functionality of
     * Shortest Paths with real-world sample/accessiblity score. Follows path 
     * from -122.3514811&47.6245678 to multiple other nodes, -122.3488837&47.6246733 and
     * -122.3484034&47.6245512, checks for accuracy*/
    @Test
    public void test08SPCrop() {
        Graph g = loadDBGraph("SPCrop.csv");
        g.report();
        ShortestPaths sp = new ShortestPaths();
        Node origin = g.getNode("-122.3514811&47.6245678");
        sp.compute(origin);
        Node middle = g.getNode("-122.3488678&47.6245538");
        Node dest1 = g.getNode("-122.3488837&47.6246733");
        Node dest2 = g.getNode("-122.3484034&47.6245512");
        LinkedList<Node> dest1Path = sp.shortestPath(dest1);
        LinkedList<Node> dest2Path = sp.shortestPath(dest2);

        //first desination node, sharing middle node
        assertEquals(dest1Path.size(), 3);
        assertEquals(dest1Path.getFirst(), origin);
        assertEquals(dest1Path.get(1), middle);
        assertEquals(dest1Path.getLast(),  dest1);
        assertEquals(sp.shortestPathLength(dest1), 1.929596757, 1e-6); 

        //second destination node, sharing middle node
        assertEquals(dest2Path.size(), 3);
        assertEquals(dest2Path.getFirst(), origin);
        assertEquals(dest2Path.get(1), middle);
        assertEquals(dest2Path.getLast(),  dest2);
        assertEquals(sp.shortestPathLength(dest2), 1.929596757, 1e-6);
    }
    
}