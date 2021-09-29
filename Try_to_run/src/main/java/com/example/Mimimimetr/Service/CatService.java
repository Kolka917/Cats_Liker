package com.example.Mimimimetr.Service;

import com.example.Mimimimetr.domain.Cat;
import com.example.Mimimimetr.repository.CatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CatService {
    @Autowired
    private CatRepo catRepo;

    private List<String[]> uncheckedCombinationsOfName = new ArrayList<>();
    public List<String[]> getUncheckedCombinationsOfName() {return uncheckedCombinationsOfName;}

    public void setUncheckedCombinationsOfName(List<String[]> uncheckedCombinationsOfName) {this.uncheckedCombinationsOfName = uncheckedCombinationsOfName;}
    public void fillUncheckedCombinationsofName(String str1, String str2){
        String[] strArray= new String[] {str1.intern(),str2.intern()};
        this.uncheckedCombinationsOfName.add(strArray);
    }

    public void cleanUncheckedCombinationsOfName(){
        this.uncheckedCombinationsOfName = new ArrayList<>();
    }

    public void updateCat(Cat cat){
        Cat catFromDb =catRepo.findByName(cat.getName());
        catFromDb.increaseLikeAmount(1);
        catRepo.save(catFromDb);
    }

    public void initialCreatingCats(){
        Cat cat1 =new Cat("Barsik", "Barsik.jpg" );
        Cat cat2 =new Cat("Cesar", "Cesar.jpg" );
        Cat cat3 =new Cat("Persik", "Persik.jpg" );
        Cat cat4 =new Cat("Markiz", "Markiz.jpg" );
        Cat cat5 =new Cat("Shnuk", "Shnuk.jpg" );

        List<Cat> catList = new ArrayList<>();
        catList.add(cat1);
        catList.add(cat2);
        catList.add(cat3);
        catList.add(cat4);
        catList.add(cat5);

        for (Cat cat: catList){
            if(!cat.equals(catRepo.findByName(cat.getName()))){
                catRepo.save(cat);
            };
        }
    }

    public void updateUncheckedCombinationsOfName(String str1,String str2) {
        String[] strArray= new String[] {str1.intern(),str2.intern()};
        List<String[]> copyOfCombinations = new ArrayList<>(uncheckedCombinationsOfName);
        for (String[] str:copyOfCombinations){
            if((str[0].intern()==str1.intern() && str[1].intern()==str2.intern())||(str[0].intern()==str2.intern() && str[1].intern()==str1.intern())) {
                uncheckedCombinationsOfName.remove(str);
            }
        }
    }

    public List<String[]> createListOfCombinations(){
        Iterable<Cat> catSequence = catRepo.findAll();
        List<String> catName = new ArrayList<>();
        for (Cat cat : catSequence){
            catName.add(cat.getName());
        }
        Collections.shuffle(catName);
        List<String[]> combinations = new ArrayList<>();
        for (int i=0; i<catName.size();i++){
            for (int j=i+1; j<catName.size();j++){
                String[] pairOfNames = {catName.get(i),catName.get(j)};
                combinations.add(pairOfNames);
            }
        }
        return combinations;
    }
}
