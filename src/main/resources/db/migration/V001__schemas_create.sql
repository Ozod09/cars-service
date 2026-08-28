-- =========================================================
-- TYPE OF SERVICES
-- =========================================================

CREATE TABLE type_of_services (
      id UUID PRIMARY KEY,
      name VARCHAR(100) NOT NULL,
      description TEXT,
      attachment_path VARCHAR(150),
      region_id UUID,
      status VARCHAR(50) NOT NULL,
      created_at TIMESTAMP,
      updated_at TIMESTAMP
);


-- =========================================================
-- CARS CLASSES
-- =========================================================

CREATE TABLE cars_classes (
      id UUID PRIMARY KEY,
      type_of_service_id UUID,
      name VARCHAR(100) NOT NULL,
      description TEXT,

      start_price DOUBLE PRECISION,
      start_km DOUBLE PRECISION,
      start_km_price DOUBLE PRECISION,
      km_price DOUBLE PRECISION,
      evening_price DOUBLE PRECISION,
      paid_waiting_time DOUBLE PRECISION,

      status VARCHAR(50) NOT NULL,

      created_at TIMESTAMP,
      updated_at TIMESTAMP,

      CONSTRAINT fk_cars_classes_type_of_service
          FOREIGN KEY (type_of_service_id)
              REFERENCES type_of_services (id)
);


-- =========================================================
-- CARS
-- =========================================================

CREATE TABLE cars (
      id UUID PRIMARY KEY,

      driver_id UUID NOT NULL,

      first_name VARCHAR(100),
      last_name VARCHAR(100),
      phone VARCHAR(50),
      attachment_path VARCHAR(150),

      model VARCHAR(100) NOT NULL,
      number VARCHAR(50) NOT NULL,

      tex_passport_photo_path VARCHAR(150),

      car_redis_id BIGINT UNIQUE,

      brent_status VARCHAR(50) NOT NULL,
      status VARCHAR(50) NOT NULL,

      created_at TIMESTAMP,
      updated_at TIMESTAMP
);


-- =========================================================
-- CARS SERVICE IDS
-- @ElementCollection List<UUID> carsServiceIds
-- =========================================================

CREATE TABLE cars_service_ids (
      car_id UUID NOT NULL,
      cars_class_id UUID,

      CONSTRAINT fk_cars_service_ids_car
          FOREIGN KEY (car_id)
              REFERENCES cars (id)
              ON DELETE CASCADE
);


-- =========================================================
-- BRENT CARS TAKE PHOTO PATHS
-- @ElementCollection List<String>
-- =========================================================

CREATE TABLE brent_cars_take_photo_paths (
     car_id UUID NOT NULL,
     photo_path VARCHAR(150),

     CONSTRAINT fk_brent_cars_take_photo_paths_car
         FOREIGN KEY (car_id)
             REFERENCES cars (id)
             ON DELETE CASCADE
);


-- =========================================================
-- CAR REDIS ID SEQUENCE
-- =========================================================

CREATE SEQUENCE car_redis_id_seq  START WITH 1 INCREMENT BY 1;

ALTER TABLE cars ALTER COLUMN car_redis_id SET DEFAULT nextval('car_redis_id_seq');

ALTER SEQUENCE car_redis_id_seq  OWNED BY cars.car_redis_id;