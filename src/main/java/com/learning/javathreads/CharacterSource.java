package com.learning.javathreads;

/**
 * Created by yuwt on 2015/12/14.
 */
public interface CharacterSource {
    void nextCharacter();

    void addCharacterListener(CharacterListener listener);

    void removeCharacterListener(CharacterListener listener);
}
