package Yarn;

import static org.junit.Assert.*;
import org.junit.Test;

public class YarnTests {

    @Test
    public void testYarn() {
        Yarn ball = new Yarn();
        Yarn basketball = new Yarn(ball);
    }

    @Test
    public void testIsEmpty() {
        Yarn ball = new Yarn();
        assertTrue(ball.isEmpty());
        ball.insert("not_empty");
        assertFalse(ball.isEmpty());
        ball.removeAll("not_empty");
        assertTrue(ball.isEmpty());
    }

    @Test
    public void testGetSize() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        assertEquals(2, ball.getSize());
        ball.insert("unique");
        assertEquals(3, ball.getSize());
        ball.remove("dup");
        assertEquals(2, ball.getSize());
    }

    @Test
    public void testGetUniqueSize() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        assertEquals(1, ball.getUniqueSize());
        ball.insert("unique");
        assertEquals(2, ball.getUniqueSize());
        ball.removeAll("unique");
        assertEquals(1, ball.getUniqueSize());
    }

    @Test
    public void testInsert() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertTrue(ball.contains("dup"));
        assertTrue(ball.contains("unique"));
        assertEquals(3, ball.getSize());
    }

    @Test
    public void testRemove() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        assertEquals(2, ball.getSize());
        assertEquals(1, ball.getUniqueSize());
        ball.remove("dup");
        assertEquals(1, ball.getSize());
        assertEquals(1, ball.getUniqueSize());
        ball.remove("dup");
        assertEquals(true, ball.isEmpty());
    }

    @Test
    public void testRemoveAll() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertEquals(3, ball.getSize());
        assertEquals(2, ball.getUniqueSize());
        ball.removeAll("dup");
        assertEquals(1, ball.getSize());
        assertEquals(1, ball.getUniqueSize());
        ball.removeAll("unique");
        assertEquals(true, ball.isEmpty());
    }

    @Test
    public void testCount() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertEquals(2, ball.count("dup"));
        assertEquals(1, ball.count("unique"));
        assertEquals(0, ball.count("forneymon"));
    }

    @Test
    public void testContains() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertTrue(ball.contains("dup"));
        assertTrue(ball.contains("unique"));
        assertFalse(ball.contains("forneymon"));
        ball.remove("unique");
        assertFalse(ball.contains("unique"));
        
    }
    
    @Test
    public void testCopy() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        Yarn dolly = new Yarn(ball);
        assertEquals(2, dolly.count("dup"));
        assertEquals(1, dolly.count("unique"));
        dolly.insert("cool");
        assertFalse(ball.contains("cool"));
        ball = new Yarn(dolly);
        assertTrue(ball.contains("cool"));
        
    }

    @Test
    public void testGetNth() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        ball.insert("cool");
        assertNotEquals("cool", ball.getNth(4));
        Yarn comparison = new Yarn(ball);
        for (int i = 0; i < ball.getSize(); i++) {
            String gotten = ball.getNth(i);
            assertTrue(comparison.contains(gotten));
            comparison.remove(gotten);
        }
        
    }

    @Test
    public void testGetMostCommon() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        ball.insert("cool");
        assertEquals("dup", ball.getMostCommon());
        ball.insert("cool");
        String mc = ball.getMostCommon();
        assertTrue(mc.equals("dup") || mc.equals("cool"));
    }

    @Test
    public void testSwap() {
        Yarn y1 = new Yarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        Yarn y2 = new Yarn();
        y2.insert("yo");
        y2.insert("sup");
        y1.swap(y2);
        assertTrue(y1.contains("yo"));
        assertTrue(y1.contains("sup"));
        assertTrue(y2.contains("dup"));
        assertTrue(y2.contains("unique"));
        assertFalse(y1.contains("dup"));
        y2.swap(y1);
        assertTrue(y2.contains("yo"));
        assertTrue(y2.contains("sup"));
        assertTrue(y1.contains("dup"));
        assertTrue(y1.contains("unique"));
        
    }
    
    @Test
    public void testToString() {
        Yarn y1 = new Yarn();
        assertTrue(y1.toString().equals("{  }"));
        y1.insert("unique");
        assertTrue(y1.toString().equals("{ \"unique\": 1 }"));
        y1.insert("dup");
        y1.insert("dup");
        assertTrue(y1.toString().equals("{ \"unique\": 1, \"dup\": 2 }") ||
                   y1.toString().equals("{ \"dup\": 2, \"unique\": 1 }"));

    }

    @Test
    public void testKnit() {
        Yarn y1 = new Yarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        Yarn y2 = new Yarn();
        y2.insert("dup");
        y2.insert("cool");
        Yarn y3 = Yarn.knit(y1, y2);
        assertEquals(3, y3.count("dup"));
        assertEquals(1, y3.count("unique"));
        assertEquals(1, y3.count("cool"));
        y3.insert("test");
        assertFalse(y1.contains("test"));
        assertFalse(y2.contains("test"));
        
    } 

    @Test
    public void testTear() {
        Yarn y1 = new Yarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        Yarn y2 = new Yarn();
        y2.insert("dup");
        y2.insert("cool");
        Yarn y3 = Yarn.tear(y1, y2);
        assertEquals(1, y3.count("dup"));
        assertEquals(1, y3.count("unique"));
        assertFalse(y3.contains("cool"));
        y3.insert("test");
        assertFalse(y1.contains("test"));
        assertFalse(y2.contains("test"));
        
    }

    @Test
    public void testSameYarn() {
        Yarn y1 = new Yarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        Yarn y2 = new Yarn();
        y2.insert("unique");
        y2.insert("dup");
        y2.insert("dup");
        assertTrue(Yarn.sameYarn(y1, y2));
        assertTrue(Yarn.sameYarn(y2, y1));
        y2.insert("test");
        assertFalse(Yarn.sameYarn(y1, y2));
        y2.remove("test");
        assertTrue(Yarn.sameYarn(y2, y1));
    }

}
