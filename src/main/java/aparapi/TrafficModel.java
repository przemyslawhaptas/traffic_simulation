package aparapi;

import com.amd.aparapi.Kernel;

public class TrafficModel extends Kernel {
    /*
        Traffic array is made of joined arrays each representing a street of a structure:

        [0] street max capacity
        [1] current cars number
        [2] outgoing streets number (a possibility for a turn-around count as one)
        [3] 1st outgoing street id
        [4] 2nd outgoing street id
        ...
        [streetsCellsSize - 1] tries counter - how many times a car tried to reach it's destination
        [streetsCellsSize] next destination - id of an outgoing street a car is going to move to
     */
    private int[] traffic;
    private int streetsCellsSize;
    private long[] seed;
    private final int triesLimit = 2;

    public TrafficModel(int[] startTraffic, int streetsCellsSize, long seed) {
        this.traffic = startTraffic;
        this.streetsCellsSize = streetsCellsSize;
        this.seed = new long[1];
        this.seed[0] = seed;
    }

    @Override
    public void run() {
        int streetId = 0;
        streetId = getGlobalId();

        if (streetIsNotEmpty(streetId)) {
            if (!nextCarsStreetDestinationIsChosen(streetId)) {
                chooseNextCarsDestinationStreet(streetId);
            }
            if (getStreetsTriesCounter(streetId) >= triesLimit) {
                chooseNextCarsDestinationStreet(streetId);
                resetStreetsTriesCounter(streetId);
            }
            int destinationStreetId = 0;
            destinationStreetId = getNextCarsDestinationStreetId(streetId);
            if (thereIsSpaceForNextCar(destinationStreetId)) {
                moveToDestinationStreet(streetId, destinationStreetId);
                resetStreetsTriesCounter(streetId);
            }
            else {
                incrementStreetsTriesCounter(streetId);
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
        return seed[0];
    }

    public void setSeed(long seed) {
        this.seed[0] = seed;
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
        return getStreetsCapacity(streetId) - getStreetsCarsNumber(streetId);
    }

    /*
        This method is not thread-safe and may result in three cars from different traffic entering a street
        which has only space for one of them.
       Decided not to resolve this issue as it might resemble real-world traffic situations.
    */
    public boolean thereIsSpaceForNextCar(int streetId) {
        return getStreetsFreeSpace(streetId) > 0;
    }

    public int getStreetsOutgoingStreetsNumber(int streetId) {
        return getTraffic()[getStreetsOutgoingStreetsNumberCellIndex(streetId)];
    }

    public int getStreetsNthOutgoingStreetId(int streetId, int n) {
        int theOutgoingStreetIndex = getStreetsFirstOutgoingStreetCellIndex(streetId) + n;

        return getTraffic()[theOutgoingStreetIndex];
    }

    private int getStreetsTriesCounter(int streetId) {
        return getTraffic()[getStreetsTriesCounterCellIndex(streetId)];
    }

    public boolean nextCarsStreetDestinationIsChosen(int streetId) {
        return getNextCarsDestinationStreetId(streetId) != -1;
    }

    public void chooseNextCarsDestinationStreet(int streetId) {
        int cellIndex = getNextCarsDestinationCellIndex(streetId);
        int outgoingStreetsNumber = getStreetsOutgoingStreetsNumber(streetId);
        int random = random(getSeed(), streetId, outgoingStreetsNumber);
        int chosenStreetId = getStreetsNthOutgoingStreetId(streetId, random);
        setTrafficCell(cellIndex, chosenStreetId);
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

    private int getStreetsTriesCounterCellIndex(int streetId) {
        return getStreetsLastCellIndex(streetId) - 1;
    }

    private int getNextCarsDestinationCellIndex(int streetId) {
        return getStreetsLastCellIndex(streetId);
    }

    private void resetStreetsTriesCounter(int streetId) {
        int cellIndex = getStreetsTriesCounterCellIndex(streetId);
        setTrafficCell(cellIndex, 0);
    }

    private void incrementStreetsTriesCounter(int streetId) {
        int cellIndex = getStreetsTriesCounterCellIndex(streetId);
        int value = getStreetsTriesCounter(streetId) + 1;
        setTrafficCell(cellIndex, value);
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
        setTrafficCell(cellIndex, -1);
    }

    /*
        Based on: http://stackoverflow.com/a/16130111
     */
    private int random(long seedPart, int globalId, int bound) {
        long seed = seedPart + globalId;
        seed = (seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1);
        setSeed(seed);
        long bigResult = seed >> 16;
        int result = (int)(bigResult % bound);

        return result;
    }

    private void setTrafficCell(int cellIndex, int value) {
        getTraffic()[cellIndex] = value;
    }
}
