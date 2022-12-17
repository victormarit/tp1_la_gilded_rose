package gildedrose.shop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileReader;

public class FileBalanceRepository implements BalanceGateway {

    private static final String FILE_NAME = "balance.json";

    ObjectMapper mapper = new ObjectMapper();

    private static final FileBalanceRepository INSTANCE = new FileBalanceRepository();

    /** Point d'accès pour l'instance unique du singleton */
    public static FileBalanceRepository getInstance()
    {
        return INSTANCE;
    }


    @Override
    public float getBalance() {
        try (FileReader reader = new FileReader(FILE_NAME)) {
            return mapper.readValue(reader, new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void saveBalance(float balance) {
        try {
            mapper.writeValue(new File(FILE_NAME), balance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
