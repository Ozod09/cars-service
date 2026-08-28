package uz.taxi.cars_service.rest.service;


public interface CarGeoService {

    void startShift(Long carId);

    void endShift(Long carId);

    void updateLocation(Long carId, double lat, double lon);
}
