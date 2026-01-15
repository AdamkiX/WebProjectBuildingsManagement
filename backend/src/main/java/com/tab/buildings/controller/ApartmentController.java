package com.tab.buildings.controller;

import com.tab.buildings.entity.Apartment;
import com.tab.buildings.entity.Room;
import com.tab.buildings.entity.RoomId;
import com.tab.buildings.rep.ApartmentRepository;
import com.tab.buildings.security.ReqRes;
import com.tab.buildings.service.ApartmentService;
import com.tab.buildings.service.RoomService;
import com.tab.buildings.wrapper.ApartmentRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ApartmentController {
    // Annotation
    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private RoomService roomService;

    /**
     * Save operation for an apartment without adding rooms to it.
     *
     * @param apartment the apartment to be saved
     * @return ResponseEntity with status
     */
    @PostMapping("/managers/apartments")
    public ResponseEntity<Object> saveApartment(@Valid @RequestBody Apartment apartment)
    {
        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = apartmentService.saveApartment(apartment);
        int statusCode = reqRes.getStatusCode();

        switch (statusCode) {
            case 200:
                return ResponseEntity.ok(reqRes);
            case 500:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }
    }

    /**
     * Save operation for an apartment with adding rooms to it.
     *
     * @param apartmentRoom the apartment and list of rooms  to be saved
     * @return ResponseEntity with status
     */
    @PostMapping("/managers/apartments_and_rooms")
    public ResponseEntity<Object> saveApartmentWithRooms(@Valid @RequestBody ApartmentRoom apartmentRoom)
    {
        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = apartmentService.saveApartment(apartmentRoom.getApartment());
        int statusCode = reqRes.getStatusCode();
        List<Room> rooms = new ArrayList<>();
        Iterable<Apartment> apartments = apartmentRepository.findAll();
        List<Apartment> apartmentsList = new ArrayList<>();
        for (Apartment a : apartments) {
            apartmentsList.add(a);
        }
        int idApartment = apartmentsList.get(apartmentsList.size() - 1).getId();

        switch (statusCode) {
            case 200:
                break;
            case 500:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }

        for (Room room : apartmentRoom.getRooms()) {
            RoomId roomId = room.getId();
            roomId.setIdApartment(idApartment);
            room.setId(roomId);
            rooms.add(room);
        }

        reqRes = roomService.saveRooms(rooms);
        statusCode = reqRes.getStatusCode();
        return ResponseEntity.status(statusCode).body(reqRes);
    }

    /**
     * Read operation for fetching an apartment by id.
     *
     * @param id_apartment the id of the apartment to be fetched
     * @return ResponseEntity with the fetched apartment
     */
    @GetMapping("/managers/apartments/{id}")
    public ResponseEntity<Object> fetchApartment(@PathVariable("id") Integer id_apartment) {

        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = apartmentService.fetchApartment(id_apartment);
        int statusCode = reqRes.getStatusCode();

        switch (statusCode) {
            case 200:
                return ResponseEntity.ok(reqRes);
            case 403:
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(reqRes);
            case 500:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }


    }
/**
 * Update operation to modify the details of an existing apartment.
 *
 * @param apartment   The Apartment object containing updated details.
 * @param id_apartment The ID of the apartment to be updated.
 * @return ResponseEntity<Object> containing the response status.
 *
 */
    @PutMapping("/managers/apartments/{id}")
    public ResponseEntity<Object> updateApartment(@RequestBody Apartment apartment, @PathVariable("id") Integer id_apartment){

        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = apartmentService.updateApartment(apartment, id_apartment);
        int statusCode = reqRes.getStatusCode();

        switch (statusCode) {
            case 200:
                return ResponseEntity.ok(reqRes);
            case 400:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }
    }

    // Delete operation
    @DeleteMapping("/apartments/{id}")
    public ResponseEntity<Object> deleteApartmentById(@PathVariable("id") Integer id_apartment) {
        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = apartmentService.deleteApartmentById(id_apartment);
        int statusCode = reqRes.getStatusCode();

        switch (statusCode) {
            case 200:
                return ResponseEntity.ok(reqRes);
            case 404:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }
    }

    /**
     * Handles the GET request for finding apartments by building ID.
     *
     * @param id_building the ID of the building to find apartments for
     * @return a {@link ResponseEntity} containing the status and the {@link ReqRes} object
     */
    @GetMapping("/apartments/{building_id}")
    public ResponseEntity<Object> findApartmentsByBuildingId(@PathVariable("building_id") Integer id_building)
    {
        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = apartmentService.findApartmentsByBuildingId(id_building);
        int statusCode = reqRes.getStatusCode();

        switch (statusCode) {
            case 200:
                return ResponseEntity.ok(reqRes);
            case 206:
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(reqRes);
            case 404:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }
    }



    @GetMapping("/rooms/{building_id}")
    public ResponseEntity<Object> findApartmentsWithRoomsByBuildingId(@PathVariable("building_id") Integer id_building)
    {
        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = apartmentService.findApartmentsWithRoomsByBuildingId(id_building);
        int statusCode = reqRes.getStatusCode();

        switch (statusCode) {
            case 200:
                return ResponseEntity.ok(reqRes);
            case 204:
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(reqRes);
            case 206:
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(reqRes);
            case 404:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }
    }




    @GetMapping("/tenants/all_apartments")
    public ResponseEntity<Object> findAllTenantApartments()
    {
        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = apartmentService.findAllTenantApartments();
        int statusCode = reqRes.getStatusCode();

        switch (statusCode) {
            case 200:
                return ResponseEntity.ok(reqRes);
            case 204:
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(reqRes);
            case 206:
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(reqRes);
            case 404:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }
    }
    // tbh sam nie wiem czym to sie rozni od saveapartment, zostawiam w razie gdyby bylo wazne idk
    @PutMapping("/apartments")
    public ResponseEntity<Object> addApartmment(@Valid @RequestBody Apartment apartment){

        SecurityContext context = SecurityContextHolder.getContext();
        ReqRes reqRes = apartmentService.saveApartment(apartment);
        int statusCode = reqRes.getStatusCode();

        switch (statusCode) {
            case 200:
                return ResponseEntity.ok(reqRes);
            case 500:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(reqRes);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(reqRes);
        }
    }
}

