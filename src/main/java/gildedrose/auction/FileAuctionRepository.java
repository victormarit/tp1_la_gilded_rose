package gildedrose.auction;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.util.Collections;
import java.util.List;

public class FileAuctionRepository implements AuctionGateway {

    private final ObjectMapper mapper = new ObjectMapper();

    private static final String FILE_NAME = "auctions.json";

    private static final FileAuctionRepository INSTANCE = new FileAuctionRepository();

    public static FileAuctionRepository getInstance()
    {
        return INSTANCE;
    }

    @Override
    public List<Auction> getAuctions() {
        try (FileReader reader = new FileReader(FILE_NAME)) {
            return mapper.readValue(reader, new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Auction getAuction(String type, int quality) {
        Auction auctionResult;

        try (FileReader reader = new FileReader(FILE_NAME)) {
            List<Auction> auctions = mapper.readValue(reader, new TypeReference<>() {
            });
            auctionResult = auctions.stream().filter(auction -> auction.getItem().type.equals(type) && auction.getItem().getQuality() == quality).findFirst().orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            auctionResult = null;
        }
        return auctionResult;
    }

    @Override
    public void saveAuctions(List<Auction> auction) {
        try {
            mapper.writeValue(new java.io.File(FILE_NAME), auction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAuction(Auction auction) {
        try (FileReader reader = new FileReader(FILE_NAME)) {
            List<Auction> auctions = mapper.readValue(reader, new TypeReference<>() {
            });
            auctions.remove(auction);
            mapper.writeValue(new java.io.File(FILE_NAME), auctions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addAuction(Auction auction) {
        try (FileReader reader = new FileReader(FILE_NAME)) {
            List<Auction> auctions = mapper.readValue(reader, new TypeReference<>() {
            });
            auctions.add(auction);
            mapper.writeValue(new java.io.File(FILE_NAME), auctions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
