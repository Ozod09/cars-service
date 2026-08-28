package uz.taxi.cars_service.rest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import uz.taxi.cars_service.rest.service.CarGeoService;


@Service
@RequiredArgsConstructor
public class CarGeoServiceImpl implements CarGeoService {

    private final StringRedisTemplate stringRedisTemplate;

    private static final String KEY_AVAILABLE = "drivers:geo:0"; // Bo'sh haydovchilar
    private static final String KEY_BUSY = "drivers:geo:1";      // Band haydovchilar
    private static final String CAR_PREFIX = "car:status:";


    // Location Update: GPS yangilanganda Redis-dagi koordinatani yangilash.
    public void updateLocation(Long carId, double lat, double lon) {

        // Redis-dan mashinaning hozirgi statusini o'qiymiz (standart bo'sh/AVAILABLE)
        String status = stringRedisTemplate.opsForValue().get(CAR_PREFIX + carId);

        if (status == null || status.isEmpty()) return;

        boolean isAvailable = "AVAILABLE".equals(status);

        String targetKey = isAvailable ? KEY_AVAILABLE : KEY_BUSY;
//        String oppositeKey = isAvailable ? KEY_BUSY : KEY_AVAILABLE;

        // Qarama-qarshi set-dan o'chirib, to'g'ri set-ga koordinat yozamiz
//        stringRedisTemplate.opsForZSet().remove(oppositeKey, carId.toString());

        stringRedisTemplate.opsForGeo().add(
                targetKey,
                new Point(lon, lat),
                carId.toString()
        );
    }

    // Shift Start: Ishni boshlash (Online bo'lish)
    @Override
    public void startShift(Long carId) {
        stringRedisTemplate.opsForValue().set(CAR_PREFIX + carId, "AVAILABLE");
        updateLocation(carId, 0.0, 0.0);
    }

     // Shift End: Ishni yakunlash (Offline bo'lish)
    @Override
    public void endShift(Long carId) {
        stringRedisTemplate.opsForZSet().remove(KEY_AVAILABLE, carId.toString());
        stringRedisTemplate.opsForZSet().remove(KEY_BUSY, carId.toString());
        stringRedisTemplate.delete(CAR_PREFIX + carId);
    }

    // Zakas qabul qilinganda yoki tugaganda statusni o'zgartirish
    public void changeCarStatus(Long carRedisId, boolean isAvailable) {

        String statusValue = isAvailable ? "AVAILABLE" : "BUSY";
        stringRedisTemplate.opsForValue().set(CAR_PREFIX + carRedisId, statusValue);

        // Agarda mashina holati o'zgarsa, uni darhol tegishli GEO set-ga ko'chiramiz
        String fromKey = isAvailable ? KEY_BUSY : KEY_AVAILABLE;
        String toKey = isAvailable ? KEY_AVAILABLE : KEY_BUSY;

        // Eski set-dagi koordinatasini o'qib, yangisiga o'tkazish
        var pos = stringRedisTemplate.opsForGeo().position(fromKey, carRedisId.toString());
        if (pos != null && !pos.isEmpty() && pos.getFirst() != null) {
            stringRedisTemplate.opsForZSet().remove(fromKey, carRedisId.toString());
            stringRedisTemplate.opsForGeo().add(toKey, pos.getFirst(), carRedisId.toString());
        }
    }
}
