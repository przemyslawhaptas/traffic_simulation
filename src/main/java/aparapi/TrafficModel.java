package aparapi;

import com.amd.aparapi.Kernel;

/**
 * Created by przemek on 07.05.16.
 */
public class TrafficModel extends Kernel {
    private int[] streets;
    private int streetsCellSize = 10;
    private long seedPart = 543154097634L;

    public TrafficModel(int[] emptyStreets) {
        this.streets = addFirstCars(emptyStreets);
    }

    @Override
    public void run() {
        int i = getGlobalId();
        streets[i] = streets[i] * streets[i];
    }

    public int[] getStreets() {
        return streets;
    }

    public int getStreetsMaxCapacity(int streetId) {
        return getStreets()[getStreetsFirstCellIndex(streetId)];
    }

    public int getStreetsCarsNumber(int streetId) {
        return getStreets()[getStreetsFirstCellIndex(streetId) + 1];
    }

    public boolean streetIsNotEmpty(int streetId) {
        return getStreetsCarsNumber(streetId) > 0;
    }

    public int getStreetsFreeSpace(int streetId) {
        //todo room for optimization
        return getStreetsMaxCapacity(streetId) - getStreetsCarsNumber(streetId);
    }

    /*
        This method is not thread-safe and may result in three cars from different streets entering a street
        which has only space for one of them.
        I'm not going to resolve this issue as it's not that big of a deal thinking of a real-world traffic situations.
    */
    public boolean thereIsSpaceForNextCar(int streetId) {
        return getStreetsFreeSpace(streetId) > 0;
    }

    public int getStreetsOutgoingStreetsNumber(int streetId) {
        return getStreets()[getStreetsFirstCellIndex(streetId) + 2];
    }

    public int[] getStreetsOutgoingStreetsIds(int streetId) {
        int firstOutgoingStreetIndex = getStreetsFirstCellIndex(streetId) + 3;
        int outgoingStreetsNumber = getStreetsOutgoingStreetsNumber(streetId);
        int[] outgoingStreetsIds = new int[outgoingStreetsNumber];

        for (int i = 0; i < outgoingStreetsNumber; i++) {
            outgoingStreetsIds[i] = streets[firstOutgoingStreetIndex + i];
        }

        return outgoingStreetsIds;
    }

    public boolean nextCarsStreetDestinationIsChosen(int streetId) {
        return getNextCarsStreetDestination(streetId) != -1;
    }

    public int getNextCarsStreetDestination(int streetId) {
        return getStreets()[getStreetsLastCellIndex(streetId)];
    }

    private int getStreetsFirstCellIndex(int streetId) {
        return streetId * streetsCellSize;
    }

    private int getStreetsLastCellIndex(int streetId) {
        return (streetId + 1) * streetsCellSize - 1;
    }

    private int[] addFirstCars(int[] emptyStreets) {
        int[] streets = emptyStreets;
        //todo
            for(int i = 0; i < streets.length; i++) {
                streets[i] = i;
            }
        //todo
        return streets;
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
}
