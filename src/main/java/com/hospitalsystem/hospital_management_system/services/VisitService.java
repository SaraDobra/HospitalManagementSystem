package com.hospitalsystem.hospital_management_system.services;

import com.hospitalsystem.hospital_management_system.models.Visit;
import com.hospitalsystem.hospital_management_system.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class VisitService {

    @Autowired
    private VisitRepository visitRepository;

    public Optional<Visit> getVisitById(long id){
        return visitRepository.findById(id);
    }

    public void addVisit(Visit visit) {
        visitRepository.save(visit);
    }

    public void deleteVisit(long visitId) {
        Optional<Visit> optional = visitRepository.findById(visitId);

        optional.ifPresent(visit -> visitRepository.delete(visit));
    }

    public void editVisit(Visit visit) {
        visitRepository.save(visit);
    }
}
