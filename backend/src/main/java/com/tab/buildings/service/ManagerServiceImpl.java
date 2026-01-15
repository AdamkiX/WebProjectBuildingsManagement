package com.tab.buildings.service;

import com.tab.buildings.entity.*;
import com.tab.buildings.rep.ApartmentRepository;
import com.tab.buildings.rep.ManagerRepository;
import com.tab.buildings.rep.UserRepository;
import com.tab.buildings.security.ReqRes;
import com.tab.buildings.wrapper.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static java.lang.System.out;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;

    // save operation
    @Override
    public ReqRes saveUserManager(UserManager request) {
        ReqRes reqRes = new ReqRes();
        try {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            User user = request.getUser();
            Manager manager = request.getManager();


            managerRepository.save(manager);
            user.setUsertype("manager");
            user.setManagerUserId(manager.getId());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            reqRes.setStatusCode(200);
            reqRes.setMessage("You've successfully signup as " + user.getUsername());

            return reqRes;
        }catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setMessage(e.getMessage());
            managerRepository.delete(request.getManager());
            return reqRes;
        }
    }

    // read operation
    @Override
    public List<Manager> fetchManagerList() {
        return (List<Manager>) managerRepository.findAll();
    }

    // update operation
    @Override
    public Manager updateManager(Manager manager, Integer id_manager) throws Exception {
        Manager depDB = managerRepository.findById(id_manager).get();

        if (Objects.nonNull(manager.getName()) && !"".equalsIgnoreCase(manager.getName())) {
            depDB.setName(manager.getName());
        }

        if (Objects.nonNull(manager.getSurname()) && !"".equalsIgnoreCase(manager.getSurname())) {
            depDB.setSurname(manager.getSurname());
        }

        if (Objects.nonNull(manager.getBirthdate()) && !"".equalsIgnoreCase(manager.getBirthdate())) {
            depDB.setBirthdate(manager.getBirthdate());
        }

        if (Objects.nonNull(manager.getPhone())) {
            depDB.setPhone(manager.getPhone());
        }

        if (Objects.nonNull(manager.getResidence()) && !"".equalsIgnoreCase(manager.getResidence())) {
            depDB.setResidence(manager.getResidence());
        }

        return managerRepository.save(depDB);
    }

        public List<Apartment> getBuildingsByManagerId(int id_manager) {

        out.println(id_manager);

        Manager manager = managerRepository.findById(id_manager)
                .orElse(null);

        out.println(manager);
        List<Apartment> apartments = apartmentRepository.findByIdManager(manager);
        return apartments;
                /*
                apartments.stream()
                .map(Apartment::getId_building)
                .distinct()
                .collect(Collectors.toList());
                */
    }





}
