package aparapi;

import com.amd.aparapi.Kernel;

/**
 * Created by przemek on 07.05.16.
 */
public class TrafficModel extends Kernel {
    private int[] traffic;
    private int streetsCellsSize;
    private long seedPart = 543154097634L;

    public TrafficModel(int[] emptyStreets, int streetsCellsSize) {
        this.traffic = addFirstCars(emptyStreets);
        this.streetsCellsSize = streetsCellsSize;
    }

    @Override
    public void run() {
        int streetId = getGlobalId();

        if (streetIsNotEmpty(streetId)) {
            if (!nextCarsStreetDestinationIsChosen(streetId)) {
                chooseNextCarsStreetDestination(streetId);
            }
        }
    }

    public int[] getTraffic() {
        return traffic;
    }

    public int getStreetsMaxCapacity(int streetId) {
        return getTraffic()[getStreetsMaxCapacityCellIndex(streetId)];
    }

    public int getStreetsCarsNumber(int streetId) {
        return getTraffic()[getStreetsCarsNumberCellIndex(streetId)];
    }

    public boolean streetIsNotEmpty(int streetId) {
        return getStreetsCarsNumber(streetId) > 0;
    }

    public int getStreetsFreeSpace(int streetId) {
        //todo room for optimization
        return getStreetsMaxCapacity(streetId) - getStreetsCarsNumber(streetId);
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

        for (int i = 0; i < outgoingStreetsNumber; i++) {
            outgoingStreetsIds[i] = traffic[firstOutgoingStreetIndex + i];
        }

        return outgoingStreetsIds;
    }

    public boolean nextCarsStreetDestinationIsChosen(int streetId) {
        return getNextCarsStreetDestination(streetId) != -1;
    }

    public void chooseNextCarsStreetDestination(int streetId) {
        int index = getNextCarsDestinationCellIndex(streetId);
        int outgoingStreetsNumber = getStreetsOutgoingStreetsNumber(streetId);
        int chosenStreet = random(seedPart, streetId, outgoingStreetsNumber);
        setTrafficCell(index, chosenStreet);
    }

    public int getNextCarsStreetDestination(int streetId) {
        return getTraffic()[getStreetsLastCellIndex(streetId)];
    }

    private int getStreetsFirstCellIndex(int streetId) {
        return streetId * streetsCellsSize;
    }

    private int getStreetsLastCellIndex(int streetId) {
        return (streetId + 1) * streetsCellsSize - 1;
    }

    private int getStreetsMaxCapacityCellIndex(int streetId) {
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

    private int[] addFirstCars(int[] emptyStreets) {
//        int[] traffic = emptyStreets;
//        //todo
//            for(int i = 0; i < traffic.length; i++) {
//                traffic[i] = i;
//            }
//        //todo
//        return traffic;
        return new int[]{ 3, 1, 1, 1, 0, 0, 0, 0, 0, -1,
                5, 3, 0, 0, 0, 0, 0, 0, 0, -1};
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

    private void setTrafficCell(int index, int value) {
        getTraffic()[index] = value;
    }
}
