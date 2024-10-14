package com.example.demo.jobs;

import com.example.demo.dao.DelegationRepository;
import com.example.demo.entities.Delegation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service pour gérer les opérations de classement général.
 */
@Service
public class AllUsersService {

    @Autowired
    private DelegationRepository delegationRepository;

    /**
     * Récupère le classement général des délégations.
     *
     * @return une liste de toutes les délégations classées
     */
    public List<Delegation> classementGeneral() {
        Iterable<Delegation> delegations = delegationRepository.findAllOrderByMedals();
        return StreamSupport.stream(delegations.spliterator(), false)
                .collect(Collectors.toList());
    }
}
