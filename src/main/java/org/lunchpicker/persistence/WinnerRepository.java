package org.lunchpicker.persistence;

import org.lunchpicker.domain.Winner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WinnerRepository extends JpaRepository<Winner, String>, JpaSpecificationExecutor<Winner> {
}
