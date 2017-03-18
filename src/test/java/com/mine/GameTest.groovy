package com.mine

import com.mine.Game.Game
import spock.lang.Specification

class GameTest extends Specification {
    def "test game"() {
        given:
          Game g = new Game();
        expect:
         for(int i =0; i<12; i++) {
             g.add(10);
         }
        assert g.score() == 300
      //  assert g.getCurrentFrame() == 11
    }

    def "test end of array"() {
        given :
        Game g = new Game();

        expect:
        for(int i=0; i<9; i++) {
            g.add(0);
            g.add(0);
        }

        g.add(2);
        g.add(8);
        g.add(10);

        assert 20 == g.score();
    }

    def "test heart break"() {
        given :
        Game g= new Game();

        expect :
        for(int i=0; i<11; i++) {
            g.add(10);
        }

        g.add(9);
        assert 299 == g.score();
    }
}
