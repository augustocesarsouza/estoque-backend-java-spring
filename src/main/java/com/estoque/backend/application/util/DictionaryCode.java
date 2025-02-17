package com.estoque.backend.application.util;

import com.estoque.backend.application.util.interfaces.IDictionaryCode;
import org.springframework.stereotype.Component;

import java.util.Dictionary;
import java.util.Hashtable;

@Component
public class DictionaryCode implements IDictionaryCode {
    private static Dictionary<String, Integer> dictionaryCode;

    public DictionaryCode() {
        dictionaryCode = new Hashtable<>();
    }

    public Integer getKeyDictionary(String key){
        return dictionaryCode.get(key);
    }

    public Integer removeKeyDictionary(String key){
        return dictionaryCode.remove(key);
    }

    public Integer putKeyValueDictionary(String key, Integer value){
        return dictionaryCode.put(key, value);
    }
}
