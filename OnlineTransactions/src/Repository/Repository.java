package Repository;

import Model.Card;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author joj on 4/17/2019
 **/
public class Repository {
    private List<Card> cards;

    public Repository(String data_1) {
        loadData1(data_1);
    }

    public List<Card> getCards() {
        return this.cards;
    }

    private void loadData1(String data_1) {
        try {
            // try with resources
            FileReader fileReader = new FileReader(data_1);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String noCards_string = bufferedReader.readLine();
            int noCards;

            if(noCards_string == null || noCards_string.equals("")) noCards = 0;
            else noCards = Integer.parseInt(noCards_string);

            this.cards = new ArrayList<>();
            for(int i = 0; i < noCards; i++) {
                this.cards.add(new Card(bufferedReader.readLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
