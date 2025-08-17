package com.global.hr.doctor.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.global.hr.doctor.entity.Doctor;

import jakarta.persistence.criteria.Predicate;

public class DoctorSpecification {

    public static Specification<Doctor> filterBy(String name, String specialization, String gender, String address) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.trim().isEmpty()) {
                predicates.add(cb.like(root.get("name"), "%" + name + "%"));
            }
            if (specialization != null && !specialization.trim().isEmpty()) {
                predicates.add(cb.equal(root.get("specialization"), specialization));
            }
            if (gender != null && !gender.trim().isEmpty()) {
                predicates.add(cb.equal(root.get("gender"), gender));
            }
            if (address != null && !address.trim().isEmpty()) {
                predicates.add(cb.equal(root.get("address"), address));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
