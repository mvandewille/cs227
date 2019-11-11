package hw2;

import hw2.TennisGame;

/**
 * Examples of some tests for the TennisGame methods.
 */
public class SimpleGameTests
{

  public static void main(String[] args)
  {
    TennisGame g = new TennisGame();
    
    // ball status should switch directions during rally
    System.out.println(g.getBallStatus());  // NOT_IN_PLAY
    g.serve(false);
    System.out.println(g.getBallStatus());  // TOWARD_RECEIVER
    g.hit(false, false);
    System.out.println(g.getBallStatus());  // TOWARD_SERVER
    g.hit(false, false);
    System.out.println(g.getBallStatus());  // TOWARD_RECEIVER
   
    // hitting with fault takes the ball out of play
    g.hit(true, false);
    System.out.println(g.getBallStatus());  // NOT_IN_PLAY

    // ball was moving toward receiver, so server gets a point
    System.out.println(g.getServerPoints());  // expected 1
    
    // miss should always take ball out of play
    g.serve(false);
    g.hit(false, false);
    g.miss();
    System.out.println(g.getBallStatus());  // NOT_IN_PLAY

    // here, since ball was headed toward server (and not out of bounds),
    // receiver gets a point
    System.out.println(g.getReceiverPoints());  // expected 1

    // calling hit when ball is not in play should have no effect on direction
    g.hit(false, false);
    System.out.println(g.getBallStatus());  // NOT_IN_PLAY
    
    // serving with a service fault does not put the ball in play
    g.serve(true);
    System.out.println(g.getBallStatus());  // NOT_IN_PLAY
    
    // after two service faults, receiver gets a point
    g.serve(true);
    System.out.println(g.getReceiverPoints());  // expected 2
    
    // service faults start counting over again, so this doesn't 
    // give receiver another point
    g.serve(true);
    System.out.println(g.getReceiverPoints());  // expected 2
    
    // might be convenient to look at the both scores as a string
    System.out.println(g.getScore()); // 1-2
    
    // try hitting out of bounds
    g = new TennisGame();
    g.serve(false);
    g.hit(false, true);
    System.out.println(g.getBallStatus());  // TOWARD_SERVER
    
    // this should be a point for server
    g.miss();
    System.out.println(g.getScore()); // 1-0
 
    // ball headed out of bounds, but server hits it back anyway
    g.serve(false);
    g.hit(false, true);   
    g.hit(false, false);
    System.out.println(g.getBallStatus());  // TOWARD_RECEIVER

    // receiver misses, but this is a point for server since
    // ball was not headed out of bounds
    g.miss();   
    System.out.println(g.getScore()); // 2-0
   
    
    // how about testing logic for game over and winning
    g = new TennisGame();
    g.setScore(1, 3);
    System.out.println(g.getScore());    // 1-3
    System.out.println(g.isOver());      // expected false
    System.out.println(g.receiverWon()); // expected false
    System.out.println(g.serverWon());   // expected false

    g = new TennisGame();
    g.setScore(2, 4);
    System.out.println(g.getScore());    // 2-4
    System.out.println(g.isOver());      // expected true
    System.out.println(g.receiverWon()); // expected true
    System.out.println(g.serverWon());   // expected false
    
    g = new TennisGame();
    g.setScore(5, 4);
    System.out.println(g.getScore());    // 5-4
    System.out.println(g.isOver());      // expected false
    System.out.println(g.receiverWon()); // expected false
    System.out.println(g.serverWon());   // expected false
    
    g = new TennisGame();
    g.setScore(4, 0);
    System.out.println(g.getScore());    // 4-0
    System.out.println(g.isOver());      // expected true
    System.out.println(g.receiverWon()); // expected false
    System.out.println(g.serverWon());   // expected true

    
    // try out some call strings
    g = new TennisGame();
    g.setScore(0, 3);
    System.out.println(g.getCallString()); // "love-40"

    g = new TennisGame();
    g.setScore(2, 1);
    System.out.println(g.getCallString()); // "30-15"

    g = new TennisGame();
    g.setScore(3, 4);
    System.out.println(g.getCallString()); // "advantage out"
  }

}
