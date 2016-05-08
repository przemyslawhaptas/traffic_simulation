package aparapi;

import com.amd.aparapi.Kernel;

/**
 * Created by przemek on 07.05.16.
 */
public class TrafficModel extends Kernel {
    private int[] traffic;
    private int streetsCellsSize;
    private long seed;

    public TrafficModel(int[] startTraffic, int streetsCellsSize, long seed) {
        this.traffic = startTraffic;
        this.streetsCellsSize = streetsCellsSize;
        this.seed = seed;
    }

    @Override
    public void run() {
        int streetId = 0;
        streetId = getGlobalId();

        if (streetIsNotEmpty(streetId)) {
            if (!nextCarsStreetDestinationIsChosen(streetId)) {
                chooseNextCarsStreetDestination(streetId);
            }
            int destinationStreetId = 0;
            destinationStreetId = getNextCarsDestinationStreetId(streetId);
            if (thereIsSpaceForNextCar(destinationStreetId)) {
                moveToDestinationStreet(streetId, destinationStreetId);
            }
        }
    }

    public void execute2(int streetsNumber) {
        for (int i = 0; i < streetsNumber; i++) {
            run2(i);
        }
    }

    public void run2(int globalId) {
        int streetId = 0;
        streetId = globalId;

        if (streetIsNotEmpty(streetId)) {
            if (!nextCarsStreetDestinationIsChosen(streetId)) {
                chooseNextCarsStreetDestination(streetId);
            }
            int destinationStreetId = 0;
            destinationStreetId = getNextCarsDestinationStreetId(streetId);
            if (thereIsSpaceForNextCar(destinationStreetId)) {
                moveToDestinationStreet(streetId, destinationStreetId);
            }
        }
    }

    public int[] getTraffic() {
        return traffic;
    }

    public int getStreetsCellsSize() {
        return streetsCellsSize;
    }

    public long getSeed() {
        return seed;
    }

    public int getStreetsCapacity(int streetId) {
        return getTraffic()[getStreetsCapacityCellIndex(streetId)];
    }

    public int getStreetsCarsNumber(int streetId) {
        return getTraffic()[getStreetsCarsNumberCellIndex(streetId)];
    }

    public boolean streetIsNotEmpty(int streetId) {
        return getStreetsCarsNumber(streetId) > 0;
    }

    public int getStreetsFreeSpace(int streetId) {
        //todo room for optimization
        return getStreetsCapacity(streetId) - getStreetsCarsNumber(streetId);
    }

    /*
        This method is not thread-safe and may result in three cars from different traffic entering a street
        which has only space for one of them.
        I'm not going to resolve this issue as it's not that big of a deal thinking of a real-world traffic situations.
    */
    public boolean thereIsSpaceForNextCar(int streetId) {
        return getStreetsFreeSpace(streetId) > 0;
    }

    public int getStreetsOutgoingStreetsNumber(int streetId) {
        return getTraffic()[getStreetsOutgoingStreetsNumberCellIndex(streetId)];
    }

    public int[] getStreetsOutgoingStreetsIds(int streetId) {
        int firstOutgoingStreetIndex = getStreetsFirstOutgoingStreetCellIndex(streetId);
        int outgoingStreetsNumber = getStreetsOutgoingStreetsNumber(streetId);
        int[] outgoingStreetsIds = new int[outgoingStreetsNumber];
        int[] traffic = getTraffic();

        for (int i = 0; i < outgoingStreetsNumber; i++) {
            outgoingStreetsIds[i] = traffic[firstOutgoingStreetIndex + i];
        }

        return outgoingStreetsIds;
    }

    public boolean nextCarsStreetDestinationIsChosen(int streetId) {
        return getNextCarsDestinationStreetId(streetId) != -1;
    }

    public void chooseNextCarsStreetDestination(int streetId) {
        int cellIndex = getNextCarsDestinationCellIndex(streetId);
        int outgoingStreetsNumber = getStreetsOutgoingStreetsNumber(streetId);
        int chosenStreet = random(getSeed(), streetId, outgoingStreetsNumber);
        setTrafficCell(cellIndex, chosenStreet);
    }

    public int getNextCarsDestinationStreetId(int streetId) {
        return getTraffic()[getStreetsLastCellIndex(streetId)];
    }

    public void moveToDestinationStreet(int streetId, int destinationStreetId) {
        incrementStreetsCarsNumber(destinationStreetId);
        decrementStreetsCarsNumber(streetId);
        clearDestinationStreetCell(streetId);
    }

    private int getStreetsFirstCellIndex(int streetId) {
        return streetId * getStreetsCellsSize();
    }

    private int getStreetsLastCellIndex(int streetId) {
        return (streetId + 1) * getStreetsCellsSize() - 1;
    }

    private int getStreetsCapacityCellIndex(int streetId) {
        return getStreetsFirstCellIndex(streetId);
    }

    private int getStreetsCarsNumberCellIndex(int streetId) {
        return getStreetsFirstCellIndex(streetId) + 1;
    }

    private int getStreetsOutgoingStreetsNumberCellIndex(int streetId) {
        return getStreetsFirstCellIndex(streetId) + 2;
    }

    private int getStreetsFirstOutgoingStreetCellIndex(int streetId) {
        return getStreetsFirstCellIndex(streetId) + 3;
    }

    private int getNextCarsDestinationCellIndex(int streetId) {
        return getStreetsLastCellIndex(streetId);
    }

    private void incrementStreetsCarsNumber(int streetId) {
        int cellIndex = getStreetsCarsNumberCellIndex(streetId);
        int value = getStreetsCarsNumber(streetId) + 1;
        setTrafficCell(cellIndex, value);
    }

    private void decrementStreetsCarsNumber(int streetId) {
        int cellIndex = getStreetsCarsNumberCellIndex(streetId);
        int value = getStreetsCarsNumber(streetId) -1;
        setTrafficCell(cellIndex, value);
    }

    private void clearDestinationStreetCell(int streetId) {
        int cellIndex = getNextCarsDestinationCellIndex(streetId);
        int value = -1;
        setTrafficCell(cellIndex, value);
    }

    /*
        Based on: http://stackoverflow.com/a/16130111
     */
    private int random(long seedPart, int globalId, int bound) {
        long seed = seedPart + globalId;
        seed = (seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1);
        long bigResult = seed >> 16;
        int result = (int)(bigResult % bound);

        return result;
    }

    private void setTrafficCell(int cellIndex, int value) {
        getTraffic()[cellIndex] = value;
    }
}
