package org.alfresco.felix;

public class DictionaryServiceImpl implements DictionaryService
{
    // The set of words contained in the dictionary.
    String[] m_dictionary = { "welcome", "to", "the", "osgi", "tutorial" };
    
    public boolean checkWord(String word)
    {
        word = word.toLowerCase();

        // This is very inefficient
        for (int i = 0; i < m_dictionary.length; i++)
        {
            if (m_dictionary[i].equals(word))
            {
                return true;
            }
        }
        return false;
    }
}
