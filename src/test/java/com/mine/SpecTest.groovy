package com.mine

import spock.lang.Specification

/**
 * Created by yaosen.pys on 2016/6/21.
 */
class SpecTest extends Specification {
    def "my first test" () {
        expect:
        Math.max(a, b) == c

        where:
        a | b || c
        1 | 2 || 2
        2 | 3 || 3
    }

    def "test crash triptable"() {
        given:
        QueryService service = new QueryService();

        expect:
        print(service.queryServiceStation())
    }
}
