## ZeroBugs Kata

Problem description: [here](http://kata-log.rocks/bugs-zero-kata)

Based on the UglyTrivia Kata

Started from the GoldenMaster step in [here](https://github.com/martinsson/BugsZero-Kata/tree/587fb9504603c07d7aa043fc5ef1ba6adfc02acc/java)

Working with [Tiago](https://github.com/tiagomartinho).

### In scope

It has been solved:

  * A Game could have less than two players - make sure it always has at least two.
    * Using a runtime exception at first, then moving it to a compile-time check
  * A Game could have 7 players, make it have at most 6.
    * Using a runtime exception at first, then moving it to a compile-time check
  * The deck could run out of questions
    * Adding the questions to the deck again. After using the last Rock question, you use the first Rock question again
  * Introducing new categories of questions seems like tricky business.
    * Now any new category of question is contained in the Questions object


### Out of scope 

  * A player that get’s into prison always stays there
    * Other than just fixing the bug, try to understand what’s wrong with the design and fix the root cause
    * We've tried this but it was not working. Also, it affects the behaviour and we don't have a golden master to support it.
  * Similarly changing the board size greatly affects the questions distribution
    * Haven't worked on it.

